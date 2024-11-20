package com.nifa.fuel_buddy.core.data.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


private const val APP_PREFERENCES_NAME = "fuel_buddy_preferences"
private val Context.dataStore by preferencesDataStore(
    name = APP_PREFERENCES_NAME
)

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideUserPreferencesDatastore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}

