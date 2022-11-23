package com.our.data.di

import com.our.data.features.phase_one.repositories.PhaseOneRepositoryImpl
import com.our.domain.features.phase_one.repositories.PhaseOneRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesBindModule {

    @Binds
    abstract fun bindPhaseOneRepository(repositoryImpl: PhaseOneRepositoryImpl): PhaseOneRepository
}