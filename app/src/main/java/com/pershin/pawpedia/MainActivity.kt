package com.pershin.pawpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pershin.pawpedia.feature.pawlist.presentation.PawListScreen
import com.pershin.pawpedia.navigation.CoreNavigationCommand
import com.pershin.pawpedia.navigation.NavigationCommand
import com.pershin.pawpedia.ui.theme.PawpediaTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val navHostController = rememberNavController()

            PawpediaTheme {
                NavHost(
                    navController = navHostController,
                    startDestination = CoreNavigationCommand.PawList.route,
                    modifier = Modifier.systemBarsPadding(),
                ) {
                    composable(CoreNavigationCommand.PawList.route) {
                        PawListScreen(viewModel = hiltViewModel())
                    }
                }

            }
        }
    }

    private fun handleNavigation(
        navigationCommand: NavigationCommand,
        navHostController: NavHostController
    ) {
        when (navigationCommand) {
            NavigationCommand.Back -> {
                if (!navHostController.popBackStack()) {
                    finish()
                }
            }

            else -> navHostController.navigate(navigationCommand.route)
        }
    }
}