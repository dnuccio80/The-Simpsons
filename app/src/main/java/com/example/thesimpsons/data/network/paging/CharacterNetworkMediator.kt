package com.example.thesimpsons.data.network.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.thesimpsons.data.local.AppDataBase
import com.example.thesimpsons.data.local.dao.CharacterDao
import com.example.thesimpsons.data.local.entity.CharacterEntity
import com.example.thesimpsons.data.network.ApiService
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterNetworkMediator @Inject constructor(
    private val apiService:ApiService,
    private val db:AppDataBase,
    private val dao: CharacterDao
):RemoteMediator<Int,CharacterEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>,
    ): MediatorResult {
        return try {

            val page = when(loadType) {
                LoadType.REFRESH -> { 1 }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(true)
                    lastItem.page + 1
                }
            }

            val response = apiService.getAllCharacters(page)

            db.withTransaction {

                if(loadType == LoadType.REFRESH) {
                    dao.clearAll()
                }

                val entities = response.results.map {
                    it.toEntity().copy(page = page)
                }

                dao.insertAll(entities)
            }
            MediatorResult.Success(response.results.isEmpty())

        }catch (e:Exception) {
            MediatorResult.Success(true)
        }

    }
}