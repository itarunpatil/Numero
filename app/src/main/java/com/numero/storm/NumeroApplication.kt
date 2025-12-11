package com.numero.storm

import android.app.Application
import android.content.res.Configuration
import androidx.datastore.preferences.core.stringPreferencesKey
import com.numero.storm.crash.CrashHandler
import com.numero.storm.data.model.AppLanguage
import com.numero.storm.di.NumeroDataStore
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.util.Locale

/**
 * Main Application class for Numero.
 * Annotated with @HiltAndroidApp to enable Hilt dependency injection.
 */
@HiltAndroidApp
class NumeroApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize crash handler first to catch any crashes during startup
        CrashHandler.initialize(this)
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
                val dataStore = NumeroDataStore.get(this@NumeroApplication)
                val languageCode = dataStore.data.map { preferences ->
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
