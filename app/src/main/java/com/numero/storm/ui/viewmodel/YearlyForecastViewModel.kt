package com.numero.storm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.numero.storm.data.repository.ProfileRepository
import com.numero.storm.domain.calculator.AdvancedNumerologyInterpretations
import com.numero.storm.domain.calculator.DateCalculator
import com.numero.storm.domain.calculator.PersonalYearDetailedInterpretation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class YearlyForecastUiState(
    val isLoading: Boolean = false,
    val currentYear: Int = LocalDate.now().year,
    val personalYear: Int = 1,
    val interpretation: PersonalYearDetailedInterpretation? = null,
    val nineYearCycle: List<Pair<Int, Int>> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class YearlyForecastViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(YearlyForecastUiState())
    val uiState: StateFlow<YearlyForecastUiState> = _uiState.asStateFlow()

    fun loadForecast(profileId: Long) {
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

                val currentYear = LocalDate.now().year
                val birthDate = profile.birthDate

                // Calculate personal year
                val personalYear = DateCalculator.calculatePersonalYear(birthDate, currentYear)

                // Get detailed interpretation
                val interpretation = AdvancedNumerologyInterpretations.getDetailedPersonalYearInterpretation(personalYear)

                // Calculate 9-year cycle (4 years back, current, 4 years forward)
                val nineYearCycle = (-4..4).map { offset ->
                    val year = currentYear + offset
                    val pYear = DateCalculator.calculatePersonalYear(birthDate, year)
                    year to pYear
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        currentYear = currentYear,
                        personalYear = personalYear,
                        interpretation = interpretation,
                        nineYearCycle = nineYearCycle
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
