package com.pershin.pawpedia.navigation

/**
 * Base Navigation command which represents common navigation actions like back, close, etc.
 * */
sealed class NavigationCommand(val route: String) {
    data object Back : NavigationCommand("")
}

/**
 * This sealed class represents navigation commands to screens across app
 * */
sealed class CoreNavigationCommand(route: String) : NavigationCommand(route) {
    data object PawList : CoreNavigationCommand("paw_list")
    data class PawDescription(val name: String) : CoreNavigationCommand("paw_description")
}
