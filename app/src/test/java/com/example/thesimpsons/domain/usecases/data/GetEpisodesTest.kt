package com.example.thesimpsons.domain.usecases.data

import com.example.thesimpsons.domain.repository.DataRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class GetEpisodesTest {

    @RelaxedMockK
    private lateinit var repository: DataRepository
    private lateinit var getEpisodes: GetEpisodes

    @Before
    fun onStart() {
        MockKAnnotations.init(this)
        getEpisodes = GetEpisodes(repository)
    }

    @Test
    fun `when query is blank, getAllEpisodes is called`() = runTest {
        // Given
        coEvery { repository.getAllEpisodes() } returns flowOf()

        // When
        getEpisodes("")

        // Then
        coVerify(exactly = 1) { repository.getAllEpisodes() }
        coVerify(exactly = 0) { repository.getEpisodesByQuery("") }

    }

    @Test
    fun `when query is not blank, getEpisodesByQuery is called`() = runTest {
        // Given
        coEvery { repository.getEpisodesByQuery("test") } returns flowOf()

        // When
        getEpisodes("test")

        // Then
        coVerify(exactly = 1) { repository.getEpisodesByQuery("test") }
        coVerify(exactly = 0) { repository.getAllEpisodes() }
    }


}