package com.numero.storm

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.hilt.navigation.compose.hiltViewModel
import com.numero.storm.data.model.AppLanguage
import com.numero.storm.data.model.ThemeMode
import com.numero.storm.ui.navigation.NumeroNavHost
import com.numero.storm.ui.theme.NumeroTheme
import com.numero.storm.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.util.Locale

private val Context.mainDataStore by preferencesDataStore(name = "numero_settings")

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var currentLanguage: AppLanguage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Store current language on creation
        currentLanguage = getCurrentLanguage()

        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val settings by mainViewModel.settings.collectAsState()

            // Monitor language changes and recreate activity
            LaunchedEffect(settings.language) {
                if (currentLanguage != null && currentLanguage != settings.language) {
                    recreate()
                }
            }

            NumeroApp()
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(applyLocale(newBase))
    }

    private fun applyLocale(context: Context): Context {
        return try {
            val languageCode = runBlocking {
                context.mainDataStore.data.map { preferences ->
                    preferences[stringPreferencesKey("language")] ?: AppLanguage.ENGLISH.code
                }.first()
            }

            val locale = Locale(languageCode)
            Locale.setDefault(locale)

            val config = Configuration(context.resources.configuration)
            config.setLocale(locale)

            context.createConfigurationContext(config)
        } catch (e: Exception) {
            context
        }
    }

    private fun getCurrentLanguage(): AppLanguage {
        return try {
            runBlocking {
                val code = mainDataStore.data.map { preferences ->
                    preferences[stringPreferencesKey("language")] ?: AppLanguage.ENGLISH.code
                }.first()
                AppLanguage.entries.find { it.code == code } ?: AppLanguage.ENGLISH
            }
        } catch (e: Exception) {
            AppLanguage.ENGLISH
        }
    }
}

@Composable
fun NumeroApp() {
    val mainViewModel: MainViewModel = hiltViewModel()
    val settings by mainViewModel.settings.collectAsState()

    val isDarkTheme = when (settings.themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    NumeroTheme(darkTheme = isDarkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NumeroNavHost(
                startDestination = if (settings.hasCompletedOnboarding) {
                    "home"
                } else {
                    "onboarding"
                }
            )
        }
    }
}
