package com.pershin.pawpedia.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

internal class NavigationHandlerImpl @Inject constructor() : NavigationHandler {
    override val currentRoute = MutableSharedFlow<NavigationCommand>()

    override suspend fun navigate(newDestination: NavigationCommand) {
        currentRoute.emit(newDestination)
    }
}
