package com.example.thesimpsons.domain.usecases.data

import androidx.paging.PagingData
import com.example.thesimpsons.domain.data_classes.EpisodeDomain
import com.example.thesimpsons.domain.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEpisodes @Inject constructor(private val repository: DataRepository) {
    operator fun invoke(query:String): Flow<PagingData<EpisodeDomain>> {
        return if(query.isNotBlank()) {
            repository.getEpisodesByQuery(query)
        }else {
            repository.getAllEpisodes()
        }
    }
}