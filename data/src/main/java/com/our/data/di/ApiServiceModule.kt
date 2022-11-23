package com.our.data.di

import com.our.data.common.datasources.MockApiService
import com.our.data.features.phase_one.datasources.PhaseOneApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiServiceModule {
    @Provides
    @Singleton
    fun provideMockApiService(retrofit: Retrofit): MockApiService =
        retrofit.create(MockApiService::class.java)

    @Provides
    @Singleton
    fun providePhaseOneApiService(retrofit: Retrofit): PhaseOneApiService =
        retrofit.create(PhaseOneApiService::class.java)
}