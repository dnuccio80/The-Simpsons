package com.example.thesimpsons.domain.usecases

import androidx.paging.PagingData
import com.example.thesimpsons.data.RepositoryImpl
import com.example.thesimpsons.domain.LocationDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocations @Inject constructor(private val repository: RepositoryImpl) {

    operator fun invoke(query:String): Flow<PagingData<LocationDomain>> {
        return if(query.isNotBlank()) {
            repository.getLocationsByQuery(query)
        } else {
            repository.getAllLocations()
        }
    }

}