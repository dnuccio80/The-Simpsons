package com.example.thesimpsons.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.thesimpsons.data.local.entity.EpisodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeDao {

    @Query("SELECT * FROM EpisodeEntity WHERE id = :id")
    fun getSingleEpisode(id:Int): Flow<EpisodeEntity?>

    @Query("SELECT * FROM EpisodeEntity ORDER BY page,id")
    fun getPagingSource():PagingSource<Int,EpisodeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSingleEpisode(episodeEntity: EpisodeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(episodes:List<EpisodeEntity>)

    @Query("DELETE FROM EpisodeEntity")
    suspend fun clearAll()

    @Query("SELECT EXISTS ( SELECT 1 FROM episodeentity WHERE id = :id)")
    suspend fun exists(id:Int):Boolean

}