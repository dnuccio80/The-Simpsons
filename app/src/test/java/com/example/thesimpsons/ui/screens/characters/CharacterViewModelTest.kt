package com.example.thesimpsons.ui.screens.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import app.cash.paging.PagingData
import com.example.thesimpsons.domain.data_classes.CharacterDomain
import com.example.thesimpsons.domain.repository.DataRepository
import com.example.thesimpsons.domain.repository.UserPreferencesRepository
import com.example.thesimpsons.domain.usecases.data.GetCharacters
import com.example.thesimpsons.domain.usecases.user_preferences.GetDarkMode
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterViewModelTest {

    @RelaxedMockK
    private lateinit var getCharacters: GetCharacters

    @RelaxedMockK
    private lateinit var getDarkMode: GetDarkMode

    private lateinit var viewModel: CharacterViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = CharacterViewModel(getCharacters, getDarkMode)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewModel starts, characters value have all the character list`() = runTest {
        // Given
        val characters = listOf(
            CharacterDomain(
                id = 1,
                age = 23,
                birthdate = "",
                gender = "",
                name = "",
                occupation = "",
                portraitImage = "",
                phrases = emptyList(),
                isAlive = true
            )
        )
        val flow = flowOf(PagingData.from(characters))

        coEvery { getCharacters("") } returns flow

        // When
        viewModel.updateQuery("")
        advanceTimeBy(300)

        val differ = AsyncPagingDataDiffer(
            diffCallback = object : DiffUtil.ItemCallback<CharacterDomain>() {
                override fun areItemsTheSame(oldItem: CharacterDomain, newItem: CharacterDomain) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: CharacterDomain,
                    newItem: CharacterDomain,
                ) =
                    oldItem == newItem
            },
            updateCallback = NoopListCallback(),
            workerDispatcher = Dispatchers.Main
        )

        differ.submitData(viewModel.characters.first())

        // Then
        assert(differ.snapshot().items == characters)
    }

}

class NoopListCallback : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}