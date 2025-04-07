package com.pershin.pawpedia.core.logging

import com.pershin.pawpedia.core.logging.impl.LoggerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface LoggerModule {

    @Binds
    fun bindErrorLogger(impl: LoggerImpl): Logger

}
