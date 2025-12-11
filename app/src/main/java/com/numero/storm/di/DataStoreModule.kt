package com.numero.storm.di

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

/**
 * Single source of truth for the DataStore instance.
 *
 * IMPORTANT: This extension property MUST be the only preferencesDataStore delegate
 * in the entire application. Using multiple delegates with the same filename
 * causes "There are multiple DataStores active for the same file" crash.
 *
 * This extension is defined at the top level of this file and accessed by:
 * - DataStoreModule (for Hilt injection into repositories)
 * - NumeroApplication (for early language initialization via NumeroDataStore.get())
 * - MainActivity (for locale configuration via NumeroDataStore.get())
 */
val Context.numeroDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "numero_settings"
)

/**
 * Singleton accessor for DataStore to be used before Hilt is fully initialized.
 * This ensures we always use the same DataStore instance throughout the app lifecycle.
 */
object NumeroDataStore {
    /**
     * Gets the DataStore instance from the given context.
     * Always use this method when accessing DataStore outside of Hilt-injected classes.
     */
    fun get(context: Context): DataStore<Preferences> {
        return context.applicationContext.numeroDataStore
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return context.numeroDataStore
    }
}
