package com.example.thesimpsons.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.thesimpsons.data.local.converter.ListStringConverter
import com.example.thesimpsons.data.local.dao.CharacterDao
import com.example.thesimpsons.data.local.dao.EpisodeDao
import com.example.thesimpsons.data.local.entity.CharacterEntity
import com.example.thesimpsons.data.local.entity.EpisodeEntity

@Database(
    entities = [CharacterEntity::class, EpisodeEntity::class],
    version = 1
)
@TypeConverters(ListStringConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract val characterDao: CharacterDao
    abstract val episodeDao:EpisodeDao
}