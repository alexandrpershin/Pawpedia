package com.pershin.pawpedia.navigation.di

import com.pershin.pawpedia.navigation.NavigationHandler
import com.pershin.pawpedia.navigation.NavigationHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface NavigationModule {
    @Binds
    @Singleton
    fun bindNavigationHandler(iml: NavigationHandlerImpl): NavigationHandler
}
