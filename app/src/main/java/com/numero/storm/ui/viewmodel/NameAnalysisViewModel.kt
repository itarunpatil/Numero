package com.numero.storm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.numero.storm.data.repository.ProfileRepository
import com.numero.storm.data.repository.SettingsRepository
import com.numero.storm.domain.calculator.FullNameAnalysis
import com.numero.storm.domain.calculator.NameAnalysis
import com.numero.storm.domain.calculator.NumerologySystem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NameAnalysisUiState(
    val isLoading: Boolean = false,
    val profileName: String = "",
    val analysis: FullNameAnalysis? = null,
    val error: String? = null
)

@HiltViewModel
class NameAnalysisViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NameAnalysisUiState())
    val uiState: StateFlow<NameAnalysisUiState> = _uiState.asStateFlow()

    fun loadNameAnalysis(profileId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val profile = profileRepository.getProfileById(profileId)
                if (profile == null) {
                    _uiState.update {
                        it.copy(isLoading = false, error = "Profile not found")
                    }
                    return@launch
                }

                val settings = settingsRepository.getSettings().first()
                val system = settings.numerologySystem

                val fullName = profile.fullName
                val analysis = NameAnalysis.analyzeFullName(fullName, system)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        profileName = fullName,
                        analysis = analysis
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message ?: "Unknown error occurred")
                }
            }
        }
    }

}
