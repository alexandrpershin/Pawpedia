package com.pershin.pawpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pershin.pawpedia.feature.pawdetails.presentation.PawBreedDetailsScreen
import com.pershin.pawpedia.feature.pawlist.presentation.PawListScreen
import com.pershin.pawpedia.navigation.CoreNavigationDestinations
import com.pershin.pawpedia.navigation.NavigationCommand
import com.pershin.pawpedia.navigation.NavigationHandler
import com.pershin.pawpedia.ui.theme.PawpediaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationHandler: NavigationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val navHostController = rememberNavController()
            NavigationHandler(navHostController)

            PawpediaTheme {
                NavHost(
                    navController = navHostController,
                    startDestination = CoreNavigationDestinations.PawList.route,
                    modifier = Modifier.systemBarsPadding(),
                ) {
                    composable(CoreNavigationDestinations.PawList.route) {
                        PawListScreen(viewModel = hiltViewModel())
                    }
                    composable(
                        route = CoreNavigationDestinations.PawBreedDetails.route,
                        arguments = CoreNavigationDestinations.PawBreedDetails.navArguments,
                    ) {
                        PawBreedDetailsScreen()
                    }
                }
            }
        }
    }

    @Composable
    private fun NavigationHandler(navController: NavHostController) {
        LaunchedEffect(navigationHandler.currentRoute) {
            navigationHandler.currentRoute.collectLatest {
                when (it) {
                    is NavigationCommand.Back -> {
                        handleBackPress(navController)
                    }

                    else -> {
                        navController.navigate(it.route)
                    }
                }
            }
        }
    }

    private fun handleBackPress(navController: NavHostController) {
        if (!navController.popBackStack()) {
            finish()
        }
    }
}
