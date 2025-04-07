package com.pershin.pawpedia.feature.pawdetails.presentation

import androidx.lifecycle.SavedStateHandle
import com.pershin.pawpedia.CoroutineTestExtension
import com.pershin.pawpedia.core.logging.Logger
import com.pershin.pawpedia.feature.pawdetails.domain.DecodeUrlUseCase
import com.pershin.pawpedia.feature.pawdetails.domain.GetBreedImagesUseCase
import com.pershin.pawpedia.feature.pawdetails.presentation.PawBreedDetailsUiContract.UiState
import com.pershin.pawpedia.navigation.CoreNavigationDestinations
import com.pershin.pawpedia.navigation.NavigationHandler
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
class PawBreedDetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @JvmField
    @RegisterExtension
    val coroutineTestExtension = CoroutineTestExtension(testDispatcher)

    private val navigationHandler: NavigationHandler = mockk(relaxed = true)
    private val logger: Logger = mockk(relaxed = true)
    private val getBreedImagesUseCase: GetBreedImagesUseCase = mockk(relaxed = true)
    private val decodeUrlUseCase: DecodeUrlUseCase = DecodeUrlUseCase()
    private val savedStateHandle: SavedStateHandle = mockk<SavedStateHandle>(relaxed = true).apply {
        every { get<String>(CoreNavigationDestinations.PawBreedDetails.KEY_NAME) } returns "Breed/Subbreed"
    }

    /**
     * [sut] stands for System Under Test
     * */
    private lateinit var sut: PawBreedDetailsViewModel

    private fun createSut() {
        sut = PawBreedDetailsViewModel(
            navigationHandler = navigationHandler,
            getBreedImagesUseCase = getBreedImagesUseCase,
            decodeUrlUseCase = decodeUrlUseCase,
            savedStateHandle = savedStateHandle,
            logger = logger
        )
    }

    @Test
    fun `Given sut is created, When fetch images request succeeds, THEN assert ui state has images`() =
        runTest {
            // given
            coEvery { getBreedImagesUseCase.invoke(any(), any()) } returns listOf("url1", "url2")

            // when
            createSut()
            advanceUntilIdle()

            // then
            val expectedState = UiState(
                title = "Breed:Subbreed",
                isLoading = false,
                error = null,
                images = listOf("url1", "url2")
            )

            Assertions.assertEquals(expectedState, sut.uiState.value)
        }

    @Test
    fun `When is TryAgain is clicked, Then assert ui event was handled`() = runTest {
        // given
        coEvery { getBreedImagesUseCase.invoke(any(), any()) }.answers {
            throw Exception("Api error")
        }

        // when
        createSut()
        sut.onUiEvent(PawBreedDetailsUiContract.UiEvents.TryAgainClicked)
        advanceUntilIdle()

        // then
        coVerify(exactly = 2) { getBreedImagesUseCase.invoke(any(), any()) }
    }

    @Test
    fun `Given sut is created, When fetch images request fails, THEN assert ui state has error`() =
        runTest {
            // given
            coEvery { getBreedImagesUseCase.invoke(any(), any()) }.answers {
                throw Exception("Api error")
            }

            // when
            createSut()
            advanceUntilIdle()

            // then
            Assertions.assertEquals("Oooops, something went wrong", sut.uiState.value.error)
            Assertions.assertFalse(sut.uiState.value.isLoading)
            verify { logger.e("PawBreedDetailsViewModel", "Api error") }
        }

}
