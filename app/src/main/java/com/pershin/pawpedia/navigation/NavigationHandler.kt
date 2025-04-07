package com.pershin.pawpedia.navigation

import kotlinx.coroutines.flow.MutableSharedFlow

interface NavigationHandler {
    val currentRoute: MutableSharedFlow<NavigationCommand>

    suspend fun navigate(newDestination: NavigationCommand)
}
