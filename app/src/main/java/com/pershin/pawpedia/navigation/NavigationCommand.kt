package com.pershin.pawpedia.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * Base Navigation command which represents common navigation actions like back, close, etc.
 * */
sealed class NavigationCommand(val route: String) {
    data object Back : NavigationCommand("")
}

/**
 * This sealed class represents navigation commands to screens across app
 * */
sealed class CoreNavigationDestinations(route: String) : NavigationCommand(route) {
    data object PawList : CoreNavigationDestinations("paw_list")

    data class PawBreedDetails(val name: String) :
        CoreNavigationDestinations("paw_description/$name") {

        companion object {
            const val KEY_NAME = "name"
            const val route: String =
                "paw_description/{$KEY_NAME}"

            val navArguments: List<NamedNavArgument> =
                listOf(
                    navArgument(KEY_NAME) {
                        type = NavType.StringType
                    },
                )
        }
    }
}
