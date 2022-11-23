package com.our.data.di

import com.our.data.common.datasources.MockApiService
import com.our.data.features.phase_one.datasources.MockPhaseOneDataSourceImpl
import com.our.data.features.phase_one.datasources.PhaseOneApiService
import com.our.data.features.phase_one.datasources.PhaseOneDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun provideMockPhaseOneRemoteDataSource(apiService: MockApiService): MockPhaseOneDataSourceImpl =
        MockPhaseOneDataSourceImpl(apiService)

    @Provides
    @Singleton
    fun providePhaseOneRemoteDataSource(apiService: PhaseOneApiService): PhaseOneDataSourceImpl =
        PhaseOneDataSourceImpl(apiService)
}