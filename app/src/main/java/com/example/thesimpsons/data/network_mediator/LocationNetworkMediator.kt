package com.example.thesimpsons.data.network_mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.thesimpsons.data.local.dao.LocationDao
import com.example.thesimpsons.data.local.db.AppDataBase
import com.example.thesimpsons.data.local.entity.LocationEntity
import com.example.thesimpsons.data.network.ApiService
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class LocationNetworkMediator @Inject constructor(
    private val apiService: ApiService,
    private val db: AppDataBase,
    private val dao: LocationDao,

    ) : RemoteMediator<Int, LocationEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocationEntity>,
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(true)
                    lastItem.page + 1
                }
            }

            val response = apiService.getAllLocations(page)

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dao.clearAll()
                }

                val entities = response.results.map {
                    it.toEntity().copy(page = page)
                }

                dao.insertAll(entities)

            }

            MediatorResult.Success(response.results.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Success(true)
        }
    }

}