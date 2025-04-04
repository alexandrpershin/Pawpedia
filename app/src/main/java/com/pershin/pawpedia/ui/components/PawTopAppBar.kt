package com.pershin.pawpedia.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.pershin.pawpedia.ui.theme.PawpediaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PawTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationButton: NavigationButton? = null,
    containerColor: Color = Color.White,
    contentColor: Color = Color.Black,
) {
    TopAppBar(
        modifier = modifier,
        colors = topAppBarColors(
            containerColor = containerColor,
            scrolledContainerColor = contentColor,
            navigationIconContentColor = contentColor,
            titleContentColor = contentColor,
            actionIconContentColor = contentColor,
        ),
        title = { Text(text = title) },
        navigationIcon = {
            navigationButton?.let {
                IconButton(
                    onClick = navigationButton.onClick,
                ) {
                    Icon(navigationButton.icon, contentDescription = "Back")
                }
            }
        },

        )
}

data class NavigationButton(
    val icon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    val onClick: () -> Unit
)

@Preview
@Composable
private fun QuidcoTopAppBarPreviewTitle() {
    PawpediaTheme {
        PawTopAppBar(title = "Title with action", navigationButton = null)
    }
}

@Preview
@Composable
private fun QuidcoTopAppBarPreviewTitleAndNavigationButton() {
    PawpediaTheme {
        PawTopAppBar(
            title = "Title with action",
            navigationButton = NavigationButton(onClick = {})
        )
    }
}
