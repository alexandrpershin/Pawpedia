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
        val items: List<BreedUiModel>,
    ) : BaseUiState {

        sealed class BreedUiModel {
            data class Parent(
                val name: String,
            ) : BreedUiModel()

            data class Child(
                val parentName: String,
                val name: String,
            ) : BreedUiModel()
        }
    }

    sealed interface UiEvents : BaseUiEvent {
        data class OnItemClick(val clickedIndex: Int) : UiEvents
        data object OnRetryClick : UiEvents
    }
}
