package com.pershin.pawpedia.feature.pawlist.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pershin.pawpedia.R
import com.pershin.pawpedia.feature.pawlist.presentation.PawListContract.UiEvents
import com.pershin.pawpedia.feature.pawlist.presentation.PawListContract.UiState
import com.pershin.pawpedia.ui.components.ErrorSection
import com.pershin.pawpedia.ui.components.LoadingSection
import com.pershin.pawpedia.ui.components.PawTopAppBar


@Composable
fun PawListScreen(modifier: Modifier = Modifier, viewModel: PawListViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PawListContent(
        uiState = uiState,
        uiEvents = viewModel::onUiEvent
    )
}

@Composable
private fun PawListContent(
    modifier: Modifier = Modifier,
    uiState: UiState,
    uiEvents: (UiEvents) -> Unit,
) {
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
                PawBreedItems(Modifier.padding(it), uiState.items, uiEvents)
            }

        }
    }
}

@Composable
fun PawBreedItems(
    modifier: Modifier,
    items: List<UiState.BreedUiModel>,
    uiEvents: (UiEvents) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(items) { index, item ->
            BreedItem(item, onClick = {
                uiEvents(UiEvents.OnItemClick(index))
            })
        }
    }

}

@Composable
fun BreedItem(item: UiState.BreedUiModel, onClick: () -> Unit) {
    val additionalPadding = if (item is UiState.BreedUiModel.Child) 16.dp else 0.dp

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = additionalPadding),
        onClick = onClick
    ) {
        Box(Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
            when (item) {
                is UiState.BreedUiModel.Child -> {
                    Text(
                        text = "${item.parentName} : ${item.name}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                is UiState.BreedUiModel.Parent -> {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

        }
    }

}

