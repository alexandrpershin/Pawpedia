package com.pershin.pawpedia.feature.pawlist.presentation

import androidx.compose.runtime.Immutable
import com.pershin.pawpedia.core.base.BaseUiEvent
import com.pershin.pawpedia.core.base.BaseUiState
import com.pershin.pawpedia.core.base.BaseViewModel

interface PawListContract {

    abstract class ViewModel() : BaseViewModel<UiState, UiEvents>()

    @Immutable
    data class UiState(
        val error: String?,
        val loading: Boolean,
        val items: List<String>
    ) : BaseUiState {
        data class Breed(
            val image: String,
            val name: String,
            val parentName: String?
        )
    }

    sealed interface UiEvents : BaseUiEvent {
        data object OnItemClick : UiEvents
        data object OnRetryClick : UiEvents
    }
}