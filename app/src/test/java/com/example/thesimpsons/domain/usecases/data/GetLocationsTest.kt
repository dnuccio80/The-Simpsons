package com.example.thesimpsons.domain.usecases.data

import app.cash.paging.PagingData
import com.example.thesimpsons.domain.data_classes.LocationDomain
import com.example.thesimpsons.domain.repository.DataRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetLocationsTest {

    @RelaxedMockK
    private lateinit var repository: DataRepository
    private lateinit var getLocations: GetLocations

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getLocations = GetLocations(repository)
    }

    @Test
    fun `when query is empty, get all locations`() = runTest {
        // Given
        coEvery { repository.getAllLocations() } returns flowOf()

        // When
        getLocations("    ")

        // Then
        coVerify(exactly = 1) { repository.getAllLocations() }
        coVerify(exactly = 0) { repository.getLocationsByQuery("   ") }
    }

    @Test
    fun `when query is not empty, get only locations by query`() = runTest {
        // Given
        val flowList = flowOf(PagingData.from<LocationDomain>(emptyList()))
        coEvery { repository.getLocationsByQuery("test") } returns flowList

        // When
        val result = getLocations("test")

        // Then
        coVerify { repository.getLocationsByQuery("test") }
        assertEquals(result, flowList)
    }

}