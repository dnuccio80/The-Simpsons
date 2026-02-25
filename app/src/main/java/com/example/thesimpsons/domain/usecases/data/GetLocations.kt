package com.example.thesimpsons.domain.usecases.data

import androidx.paging.PagingData
import com.example.thesimpsons.domain.data_classes.LocationDomain
import com.example.thesimpsons.domain.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocations @Inject constructor(private val repository: DataRepository) {

    operator fun invoke(query:String): Flow<PagingData<LocationDomain>> {
        return if(query.isNotBlank()) {
            repository.getLocationsByQuery(query)
        } else {
            repository.getAllLocations()
        }
    }

}