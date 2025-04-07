package com.pershin.pawpedia.feature.pawdetails.presentation

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pershin.pawpedia.feature.pawdetails.presentation.PawBreedDetailsUiContract.UiEvents
import com.pershin.pawpedia.feature.pawdetails.presentation.PawBreedDetailsUiContract.UiState
import com.pershin.pawpedia.feature.pawdetails.presentation.carousel.ImagesCarousel
import com.pershin.pawpedia.ui.components.ErrorSection
import com.pershin.pawpedia.ui.components.LoadingSection
import com.pershin.pawpedia.ui.components.NavigationButton
import com.pershin.pawpedia.ui.components.PawTopAppBar
import com.pershin.pawpedia.ui.theme.PawpediaTheme

@Composable
fun PawBreedDetailsScreen(
    viewModel: PawBreedDetailsUiContract.ViewModel = hiltViewModel<PawBreedDetailsViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BackHandler {
        viewModel.onUiEvent(UiEvents.BackPressed)
    }

    PawBreedDetailsContent(
        uiState,
        viewModel::onUiEvent
    )
}

@Composable
private fun PawBreedDetailsContent(
    uiState: UiState,
    uiEvents: (UiEvents) -> Unit,
) {
    val onBackInvokedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    Scaffold(
        topBar = {
            PawTopAppBar(
                title = uiState.title,
                navigationButton = NavigationButton(onClick = {
                    onBackInvokedDispatcher?.onBackPressed()
                })
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) {

        when {
            uiState.isLoading -> {
                LoadingSection()
            }

            uiState.error != null -> {
                ErrorSection(message = uiState.error, onRetryClick = {
                    uiEvents(UiEvents.TryAgainClicked)
                })
            }

            else -> {
                BreedImages(Modifier.padding(it), uiState.images)
            }

        }
    }
}

@Composable
fun BreedImages(
    modifier: Modifier,
    images: List<String>,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        ImagesCarousel(
            items = images,
            onItemClick = {}
        )
    }
}

@Preview
@Composable
private fun PawBreedDetailsContentSuccessPreview() {
    PawpediaTheme {
        PawBreedDetailsContent(
            uiState = UiState(
                title = "Borzoi",
                isLoading = false,
                error = null,
                images = listOf()
            ),
            uiEvents = {}
        )
    }
}

@Preview
@Composable
private fun PawBreedDetailsContentLoadingPreview() {
    PawpediaTheme {
        PawBreedDetailsContent(
            uiState = UiState(
                title = "Borzoi",
                isLoading = true,
                error = null,
                images = listOf()
            ),
            uiEvents = {}
        )
    }
}


@Preview
@Composable
private fun PawBreedDetailsContentErrorPreview() {
    PawpediaTheme {
        PawBreedDetailsContent(
            uiState = UiState(
                title = "Borzoi",
                isLoading = false,
                error = "No internet connection",
                images = listOf()
            ),
            uiEvents = {}
        )
    }
}


