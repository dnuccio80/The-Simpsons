package com.example.thesimpsons.domain.usecases.data

import androidx.paging.PagingSource
import app.cash.paging.PagingData
import com.example.thesimpsons.domain.data_classes.CharacterDomain
import com.example.thesimpsons.domain.repository.DataRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals


class GetCharactersTest {

    companion object {
        val CHARACTER_LIST = listOf(
            CharacterDomain(
                id = 1,
                age = 25,
                birthdate = "",
                gender = "",
                name = "Homer",
                occupation = "",
                portraitImage = "",
                phrases = emptyList(),
                isAlive = true
            ),
            CharacterDomain(
                id = 1,
                age = 25,
                birthdate = "",
                gender = "",
                name = "Marge",
                occupation = "",
                portraitImage = "",
                phrases = emptyList(),
                isAlive = true
            ),
            CharacterDomain(
                id = 1,
                age = 25,
                birthdate = "",
                gender = "",
                name = "Bart",
                occupation = "",
                portraitImage = "",
                phrases = emptyList(),
                isAlive = true
            )
        )
    }

    @RelaxedMockK
    private lateinit var repository: DataRepository

    private lateinit var getCharacters: GetCharacters

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getCharacters = GetCharacters(repository)
    }

    @Test
    fun `when query is empty, all characters fun is run`() = runTest {
        // Given

        val pagingData = PagingData.from(CHARACTER_LIST)

        coEvery { repository.getAllCharacters() } returns flowOf()

        // When
        getCharacters("")

        // Then
        coVerify(exactly = 1) { repository.getAllCharacters() }
        coVerify(exactly = 0) { repository.getAllCharactersByQuery("") }

    }

    @Test
    fun `when query is Marge, returns Marge character`() = runTest {

        // Given
        coEvery { repository.getAllCharactersByQuery("Marge") } returns flowOf(PagingData.from(CHARACTER_LIST))

        // When
        val result = repository.getAllCharactersByQuery("Marge")

        // Then
        coVerify(exactly = 1) { repository.getAllCharactersByQuery("Marge") }
        coVerify(exactly = 0) { repository.getAllCharacters() }

        assertEquals(result, repository.getAllCharactersByQuery("Marge"))
    }

    @Test
    fun `when query does not match with any character, returns empty list`() = runTest {

        // Given
        coEvery { repository.getAllCharactersByQuery("asd") } returns flowOf(
            PagingData.from(
                emptyList()
            )
        )

        // When
        val result = repository.getAllCharactersByQuery("asd")

        // Then
        assertEquals(result, repository.getAllCharactersByQuery("asd"))

    }


}