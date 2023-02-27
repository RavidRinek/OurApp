package com.our.data.di

import android.content.Context
import com.our.data.base.datasources.Prefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PersistenceDataModule {

    @Provides
    @Singleton
    fun provideGuruShotsPrefs(@ApplicationContext context: Context): Prefs =
        Prefs(context)
}