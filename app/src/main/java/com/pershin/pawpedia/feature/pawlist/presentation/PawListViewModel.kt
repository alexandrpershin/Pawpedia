package com.pershin.pawpedia.feature.pawlist.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.pershin.pawpedia.feature.pawlist.domain.GetPawBreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PawListViewModel @Inject constructor(
    private val getPawBreedsUseCase: GetPawBreedsUseCase
) : PawListContract.ViewModel() {

    override val _uiState = MutableStateFlow(initialUiState())

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.i("SUKA", throwable.toString())

//        val quidcoDomainError = quidcoErrorMapper.map(throwable)
//        handleError(quidcoDomainError)
    }


    init {
        viewModelScope.launch(coroutineExceptionHandler) {
            fetchPawBreeds()
        }
    }

    private suspend fun fetchPawBreeds() {
        val items = getPawBreedsUseCase().getOrThrow()

        Log.i("SUKA", items.joinToString())

        updateUiState { it.copy(loading = false) }

//        updateUiState {
//
//        }

    }

    override fun onUiEvent(event: PawListContract.UiEvents) {
        when (event) {
            PawListContract.UiEvents.OnItemClick -> {
                // TODO handle click
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
