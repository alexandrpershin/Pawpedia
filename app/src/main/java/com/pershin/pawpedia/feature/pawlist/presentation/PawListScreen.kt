package com.pershin.pawpedia.feature.pawlist.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pershin.pawpedia.R
import com.pershin.pawpedia.feature.pawlist.presentation.PawListContract.UiEvents
import com.pershin.pawpedia.feature.pawlist.presentation.PawListContract.UiState
import com.pershin.pawpedia.ui.components.PawTopAppBar
import com.pershin.pawpedia.ui.components.PrimaryButton


@Composable
fun PawListScreen(modifier: Modifier = Modifier, viewModel: PawListViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PawListContent(
        uiState = uiState,
        uiEvents = viewModel::onUiEvent
    )
}

@Composable
fun PawListContent(modifier: Modifier = Modifier, uiState: UiState, uiEvents: (UiEvents) -> Unit) {
    Scaffold(modifier = modifier, topBar = {
        PawTopAppBar(title = stringResource(R.string.breeds))
    }) {
        when {
            uiState.loading -> {
                LoadingSection()
            }

            uiState.error != null -> {
                ErrorSection(message = uiState.error, onRetryClick = {
                    uiEvents(UiEvents.OnRetryClick)
                })
            }

            else -> {
                PawBreedItems(Modifier.padding(it))
            }

        }
    }
}

@Composable
fun PawBreedItems(modifier: Modifier) {


}

@Composable
fun ErrorSection(message: String, onRetryClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(message, style = MaterialTheme.typography.headlineMedium)
        PrimaryButton(
            text = message,
            onClick = onRetryClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
fun LoadingSection() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.Center),
            strokeWidth = 2.dp
        )
    }
}

