package com.example.thesimpsons.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.thesimpsons.data.local.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM CharacterEntity Where id = :id")
    fun getSingleCharacter(id:Int): Flow<CharacterEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacter(characterEntity: CharacterEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM characterentity where id = :id)")
    suspend fun exists(id:Int):Boolean

    @Query("SELECT * FROM CharacterEntity ORDER BY page, id")
    fun pagingSource():PagingSource<Int,CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Query("DELETE FROM CharacterEntity")
    suspend fun clearAll()

}