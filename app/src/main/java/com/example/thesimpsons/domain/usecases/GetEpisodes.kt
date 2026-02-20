package com.example.thesimpsons.domain.usecases

import androidx.paging.PagingData
import com.example.thesimpsons.data.RepositoryImpl
import com.example.thesimpsons.domain.EpisodeDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEpisodes @Inject constructor(private val repository: RepositoryImpl) {
    operator fun invoke(query:String): Flow<PagingData<EpisodeDomain>> {
        return if(query.isNotBlank()) {
            repository.getEpisodesByQuery(query)
        }else {
            repository.getAllEpisodes()
        }
    }
}