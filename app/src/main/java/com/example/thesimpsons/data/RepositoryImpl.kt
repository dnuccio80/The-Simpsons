package com.example.thesimpsons.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.thesimpsons.data.local.CharacterDao
import com.example.thesimpsons.data.local.LocalDataSource
import com.example.thesimpsons.data.local.entity.CharacterEntity
import com.example.thesimpsons.data.network.ApiService
import com.example.thesimpsons.data.network.data_source.NetworkDataSource
import com.example.thesimpsons.data.network.paging.NetworkMediator
import com.example.thesimpsons.domain.CharacterDomain
import com.example.thesimpsons.domain.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RepositoryImpl @Inject constructor(
    private val network: NetworkDataSource,
    private val local: LocalDataSource,
    private val mediator: NetworkMediator,
    private val dao: CharacterDao,
) : Repository {

    companion object {
        const val CHARACTERS_PER_PAGE = 20
        const val PREFETCH_ITEMS = 3
    }

    override suspend fun getSingleCharacter(id: Int): Flow<CharacterDomain> {
        return local.getSingleCharacter(id)
            .onStart {
                val exists = local.characterExists(id)
                if (!exists) local.addCharacter(network.getSingleCharacter(id).toEntity())
            }
            .filterNotNull()
            .map { it.toDomain() }
    }

    override fun getAllCharacters(): Flow<PagingData<CharacterDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = CHARACTERS_PER_PAGE,
                prefetchDistance = PREFETCH_ITEMS
            ),
            remoteMediator = mediator,
            pagingSourceFactory = { dao.pagingSource() }
        ).flow.map {pagingData ->
            pagingData.map { it.toDomain() }
        }
    }


}