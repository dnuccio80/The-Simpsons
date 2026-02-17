package com.example.thesimpsons.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.thesimpsons.data.local.dao.CharacterDao
import com.example.thesimpsons.data.local.LocalDataSource
import com.example.thesimpsons.data.local.dao.EpisodeDao
import com.example.thesimpsons.data.network.data_source.NetworkDataSource
import com.example.thesimpsons.data.network.paging.CharacterNetworkMediator
import com.example.thesimpsons.data.network.paging.EpisodeNetworkMediator
import com.example.thesimpsons.domain.CharacterDomain
import com.example.thesimpsons.domain.EpisodeDomain
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
    private val characterMediator: CharacterNetworkMediator,
    private val characterDao: CharacterDao,
    private val episodeMediator:EpisodeNetworkMediator,
    private val episodeDao:EpisodeDao
) : Repository {

    companion object {
        const val ITEMS_PER_PAGE = 20
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
                pageSize = ITEMS_PER_PAGE,
                prefetchDistance = PREFETCH_ITEMS
            ),
            remoteMediator = characterMediator,
            pagingSourceFactory = { characterDao.pagingSource() }
        ).flow.map {pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun getSingleEpisode(id: Int): Flow<EpisodeDomain> {
        return local.getSingleEpisode(id)
            .onStart {
                val exists = episodeDao.exists(id)
                if(!exists) {
                    local.addSingleEpisode(network.getSingleEpisode(id).toEntity())
                }
            }
            .filterNotNull()
            .map { it.toDomain() }
    }

    override fun getAllEpisodes(): Flow<PagingData<EpisodeDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                prefetchDistance = PREFETCH_ITEMS
            ),
            remoteMediator = episodeMediator,
            pagingSourceFactory = { episodeDao.getPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }


}