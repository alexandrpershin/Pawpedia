package com.pershin.pawpedia.feature.pawdetails.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.pershin.pawpedia.core.logging.Logger
import com.pershin.pawpedia.feature.pawdetails.domain.DecodeUrlUseCase
import com.pershin.pawpedia.feature.pawdetails.domain.GetBreedImagesUseCase
import com.pershin.pawpedia.feature.pawdetails.presentation.PawBreedDetailsUiContract.UiEvents
import com.pershin.pawpedia.feature.pawdetails.presentation.PawBreedDetailsUiContract.UiState
import com.pershin.pawpedia.navigation.CoreNavigationDestinations
import com.pershin.pawpedia.navigation.NavigationCommand
import com.pershin.pawpedia.navigation.NavigationHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PawBreedDetailsViewModel @Inject constructor(
    private val navigationHandler: NavigationHandler,
    private val getBreedImagesUseCase: GetBreedImagesUseCase,
    private val decodeUrlUseCase: DecodeUrlUseCase,
    private val logger: Logger,
    savedStateHandle: SavedStateHandle,
) : PawBreedDetailsUiContract.ViewModel() {

    private val TAG: String =
        PawBreedDetailsViewModel::class.simpleName ?: "PawBreedDetailsViewModel"

    private val breedName: String by lazy {
        val breedNameExtra =
            requireNotNull(savedStateHandle.get<String>(CoreNavigationDestinations.PawBreedDetails.KEY_NAME))

        decodeUrlUseCase(breedNameExtra)
    }

    override val _uiState: MutableStateFlow<UiState> = MutableStateFlow(initialState())

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        logger.e(TAG, "${throwable.message}")

        updateUiState {
            it.copy(
                isLoading = false,
                error = "Oooops, something went wrong"
            )
        }
    }


    init {
        viewModelScope.launch(coroutineExceptionHandler) {
            fetchImages()
        }
    }

    private suspend fun fetchImages() {
        val images = getBreedImagesUseCase(breedName.lowercase(), IMAGES_NUMBER)

        updateUiState { it.copy(isLoading = false, images = images) }
    }

    override fun onUiEvent(event: UiEvents) {
        when (event) {
            UiEvents.BackPressed -> viewModelScope.launch {
                navigationHandler.navigate(NavigationCommand.Back)
            }

            UiEvents.TryAgainClicked -> viewModelScope.launch(coroutineExceptionHandler) {
                updateUiState { it.copy(error = null, isLoading = true) }
                fetchImages()
            }
        }
    }

    private fun initialState(): UiState {
        return UiState(
            isLoading = true,
            error = null,
            title = breedName.replace("/", ":"),
            images = listOf()
        )
    }

    private companion object {
        private const val IMAGES_NUMBER = 10
    }
}

