package com.pershin.pawpedia.feature.pawdetails.presentation

import androidx.compose.runtime.Stable
import com.pershin.pawpedia.core.base.BaseUiEvent
import com.pershin.pawpedia.core.base.BaseUiState
import com.pershin.pawpedia.core.base.BaseViewModel

interface PawBreedDetailsUiContract {

    abstract class ViewModel : BaseViewModel<UiState, UiEvents>()

    @Stable
    data class UiState(
        val title: String,
        val isLoading: Boolean,
        val error: String?,
        val images: List<String>,
    ) : BaseUiState

    sealed interface UiEvents : BaseUiEvent {
        data object BackPressed : UiEvents
        data object TryAgainClicked : UiEvents
    }
}
