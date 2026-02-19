package com.example.thesimpsons.data.local.data_source

import com.example.thesimpsons.data.local.dao.CharacterDao
import com.example.thesimpsons.data.local.dao.EpisodeDao
import com.example.thesimpsons.data.local.dao.LocationDao
import com.example.thesimpsons.data.local.entity.CharacterEntity
import com.example.thesimpsons.data.local.entity.EpisodeEntity
import com.example.thesimpsons.data.local.entity.LocationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val characterDao: CharacterDao,
    private val episodeDao: EpisodeDao,
    private val locationDao: LocationDao
) {

    // Characters

    suspend fun characterExists(id: Int): Boolean = characterDao.exists(id)

    suspend fun addCharacter(character: CharacterEntity) = characterDao.addCharacter(character)

    fun getSingleCharacter(id: Int): Flow<CharacterEntity?> = characterDao.getSingleCharacter(id)

    // Episodes

    suspend fun episodeExists(id: Int): Boolean = episodeDao.exists(id)

    suspend fun addSingleEpisode(episodeEntity: EpisodeEntity) = episodeDao.addSingleEpisode(episodeEntity)

    fun getSingleEpisode(id:Int): Flow<EpisodeEntity?> = episodeDao.getSingleEpisode(id)

    // Locations

    suspend fun addLocations(locations:List<LocationEntity>) = locationDao.insertAll(locations)

}