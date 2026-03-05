package com.example.thesimpsons.data.network_mediator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil.network.HttpException
import com.example.thesimpsons.data.local.dao.CharacterDao
import com.example.thesimpsons.data.local.db.AppDataBase
import com.example.thesimpsons.data.local.entity.CharacterEntity
import com.example.thesimpsons.data.network.ApiService
import okio.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterNetworkMediator @Inject constructor(
    private val db: AppDataBase,
    private val apiService:ApiService,
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
                    val lastItem = state.lastItemOrNull()

                    if(lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
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
            Log.d("MEDIATOR", "loadType=$loadType, itemsInState=${state.pages.sumOf { it.data.size }}")
            MediatorResult.Success(response.results.isEmpty())

        }catch (e: IOException) {
            MediatorResult.Error(e)
        }catch (e: HttpException) {
            MediatorResult.Error(e)
        }

    }
}