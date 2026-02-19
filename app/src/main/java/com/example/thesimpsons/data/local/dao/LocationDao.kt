package com.example.thesimpsons.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.thesimpsons.data.local.entity.LocationEntity

@Dao
interface LocationDao {

    @Query("SELECT * FROM LocationEntity ORDER BY page, id")
    fun getPagingSource():PagingSource<Int,LocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locations:List<LocationEntity>)

    @Query("DELETE FROM LocationEntity")
    suspend fun clearAll()

}