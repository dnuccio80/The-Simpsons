package com.example.thesimpsons.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.thesimpsons.data.local.converter.ListStringConverter
import com.example.thesimpsons.data.local.entity.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 1
)
@TypeConverters(ListStringConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract val characterDao: CharacterDao
}