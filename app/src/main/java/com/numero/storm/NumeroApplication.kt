package com.numero.storm

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.numero.storm.data.model.AppLanguage
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.util.Locale

private val Context.appDataStore by preferencesDataStore(name = "numero_settings")

/**
 * Main Application class for Numero.
 * Annotated with @HiltAndroidApp to enable Hilt dependency injection.
 */
@HiltAndroidApp
class NumeroApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Apply saved language on app startup
        applySavedLanguage()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Reapply saved language when configuration changes
        applySavedLanguage()
    }

    private fun applySavedLanguage() {
        runBlocking {
            try {
                val languageCode = appDataStore.data.map { preferences ->
                    preferences[stringPreferencesKey("language")] ?: AppLanguage.ENGLISH.code
                }.first()

                val locale = Locale(languageCode)
                Locale.setDefault(locale)

                val config = Configuration(resources.configuration)
                config.setLocale(locale)

                @Suppress("DEPRECATION")
                resources.updateConfiguration(config, resources.displayMetrics)
            } catch (e: Exception) {
                // Silently fail if DataStore is not yet initialized
            }
        }
    }
}
