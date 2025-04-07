package com.pershin.pawpedia.core.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * `BaseViewModel` is an abstract base class for ViewModels.
 * It provides common functionality related to error handling, crash reporting,
 * analytics tracking, and user authentication.
 *
 * @param S Represents the type of UI state that the ViewModel manages. It must extend `BaseUiState`.
 * @param E Represents the type of UI events that the ViewModel handles. It must extend `BaseUiEvent`.
 */
abstract class BaseViewModel<S : BaseUiState, E : BaseUiEvent>() : ViewModel() {

    protected abstract val _uiState: MutableStateFlow<S>

    val uiState: StateFlow<S> by lazy { _uiState }

    abstract fun onUiEvent(event: E)

    protected open fun initialUiState(): S = _uiState.value

    protected fun updateUiState(updateBlock: (oldState: S) -> S) {
        _uiState.update { updateBlock(it) }
    }

}

interface BaseUiState

interface BaseUiEvent
