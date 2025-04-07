package com.pershin.pawpedia.feature.pawlist.presentation

import androidx.lifecycle.viewModelScope
import com.pershin.pawpedia.core.logging.Logger
import com.pershin.pawpedia.feature.pawlist.domain.EncodeUrlUseCase
import com.pershin.pawpedia.feature.pawlist.domain.GetPawBreedsUseCase
import com.pershin.pawpedia.feature.pawlist.presentation.PawListContract.UiState.BreedUiModel
import com.pershin.pawpedia.feature.pawlist.presentation.mapper.BreedUiModelMapper
import com.pershin.pawpedia.navigation.CoreNavigationDestinations
import com.pershin.pawpedia.navigation.NavigationHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PawListViewModel @Inject constructor(
    private val getPawBreedsUseCase: GetPawBreedsUseCase,
    private val breedUiModelMapper: BreedUiModelMapper,
    private val navigationHandler: NavigationHandler,
    private val encodeUrlUseCase: EncodeUrlUseCase,
    private val logger: Logger,
) : PawListContract.ViewModel() {

    private val TAG: String = PawListViewModel::class.simpleName ?: "PawListViewModel"

    override val _uiState = MutableStateFlow(initialUiState())

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        logger.e(TAG, throwable.toString())

        updateUiState {
            it.copy(
                loading = false,
                error = "Oooops, something went wrong"
            )
        }
    }

    init {
        viewModelScope.launch(coroutineExceptionHandler) {
            fetchPawBreeds()
        }
    }

    private suspend fun fetchPawBreeds() {
        val result = getPawBreedsUseCase()

        val uiItems: List<BreedUiModel> = breedUiModelMapper.map(result)
        updateUiState { it.copy(loading = false, items = uiItems) }
    }

    override fun onUiEvent(event: PawListContract.UiEvents) {
        when (event) {
            is PawListContract.UiEvents.OnItemClick -> viewModelScope.launch {
                val clickedItem = _uiState.value.items[event.clickedIndex]

                val name = when (clickedItem) {
                    is BreedUiModel.Child -> "${clickedItem.parentName}/${clickedItem.name}"
                    is BreedUiModel.Parent -> clickedItem.name
                }

                val encodedName = encodeUrlUseCase(name)
                navigationHandler.navigate(CoreNavigationDestinations.PawBreedDetails(encodedName))
            }

            PawListContract.UiEvents.OnRetryClick -> viewModelScope.launch(coroutineExceptionHandler) {
                updateUiState { it.copy(loading = true, error = null) }
                fetchPawBreeds()
            }
        }
    }

    override fun initialUiState() = PawListContract.UiState(
        error = null,
        loading = true,
        items = listOf()
    )
}

