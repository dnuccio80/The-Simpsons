package com.example.thesimpsons.data.network.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.thesimpsons.data.local.AppDataBase
import com.example.thesimpsons.data.local.CharacterDao
import com.example.thesimpsons.data.local.entity.CharacterEntity
import com.example.thesimpsons.data.network.ApiService
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NetworkMediator @Inject constructor(
    private val apiService:ApiService,
    private val db:AppDataBase,
    private val dao:CharacterDao
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

            Log.d("Paging", "Page: $page")
            val response = apiService.getAllCharacters(page)
            Log.d("Paging", response.toString())

            db.withTransaction {
                Log.d("Paging", "Entry on db")

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
            Log.e("Paging", "NetworkMediator error", e)
            MediatorResult.Error(e)
        }

    }
}