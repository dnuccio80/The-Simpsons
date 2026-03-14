package com.danucdev.thesimpsons.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.danucdev.thesimpsons.data.local.converter.ListStringConverter
import com.danucdev.thesimpsons.data.local.dao.CharacterDao
import com.danucdev.thesimpsons.data.local.dao.EpisodeDao
import com.danucdev.thesimpsons.data.local.dao.LocationDao
import com.danucdev.thesimpsons.data.local.entity.CharacterEntity
import com.danucdev.thesimpsons.data.local.entity.EpisodeEntity
import com.danucdev.thesimpsons.data.local.entity.LocationEntity

@Database(
    entities = [CharacterEntity::class, EpisodeEntity::class, LocationEntity::class],
    version = 1
)
@TypeConverters(ListStringConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract val characterDao: CharacterDao
    abstract val episodeDao: EpisodeDao
    abstract val locationDao: LocationDao
}