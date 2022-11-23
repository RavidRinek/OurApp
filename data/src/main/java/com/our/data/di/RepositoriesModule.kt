package com.our.data.di

import com.our.data.features.phase_one.datasources.PhaseOneDataSourceImpl
import com.our.data.features.phase_one.repositories.PhaseOneRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {

    @Provides
    @Singleton
    fun providePhaseOneRepository(dataSource: PhaseOneDataSourceImpl): PhaseOneRepositoryImpl =
        PhaseOneRepositoryImpl(dataSource)
}