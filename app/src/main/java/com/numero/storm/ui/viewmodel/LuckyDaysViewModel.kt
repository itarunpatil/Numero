package com.numero.storm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.numero.storm.data.repository.ProfileRepository
import com.numero.storm.domain.calculator.LuckyDaysCalculator
import com.numero.storm.domain.calculator.LuckyProfile
import com.numero.storm.domain.calculator.MonthlyFavorableDays
import com.numero.storm.domain.calculator.NumerologyCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

data class LuckyDaysUiState(
    val isLoading: Boolean = false,
    val profileName: String = "",
    val luckyProfile: LuckyProfile? = null,
    val currentMonthDays: MonthlyFavorableDays? = null,
    val nextMonthDays: MonthlyFavorableDays? = null,
    val selectedYearMonth: YearMonth = YearMonth.now(),
    val error: String? = null
)

@HiltViewModel
class LuckyDaysViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LuckyDaysUiState())
    val uiState: StateFlow<LuckyDaysUiState> = _uiState.asStateFlow()

    fun loadLuckyDays(profileId: Long) {
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

                val birthDate = profile.birthDate

                // Calculate core numbers needed for lucky profile
                val lifePathNumber = NumerologyCalculator.calculateLifePath(birthDate)
                val expressionNumber = NumerologyCalculator.calculateExpression(profile.fullName)
                val birthdayNumber = birthDate.dayOfMonth

                // Calculate lucky profile
                val luckyProfile = LuckyDaysCalculator.calculateLuckyProfile(
                    lifePathNumber = lifePathNumber,
                    expressionNumber = expressionNumber,
                    birthdayNumber = birthdayNumber,
                    birthDate = birthDate
                )

                // Get current and next month favorable days
                val currentYearMonth = YearMonth.now()
                val nextYearMonth = currentYearMonth.plusMonths(1)

                val currentMonthDays = LuckyDaysCalculator.findFavorableDaysInMonth(
                    birthDate = birthDate,
                    yearMonth = currentYearMonth
                )

                val nextMonthDays = LuckyDaysCalculator.findFavorableDaysInMonth(
                    birthDate = birthDate,
                    yearMonth = nextYearMonth
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        profileName = profile.fullName,
                        luckyProfile = luckyProfile,
                        currentMonthDays = currentMonthDays,
                        nextMonthDays = nextMonthDays,
                        selectedYearMonth = currentYearMonth
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message ?: "Unknown error occurred")
                }
            }
        }
    }

    fun selectMonth(yearMonth: YearMonth, birthDate: LocalDate) {
        viewModelScope.launch {
            val monthDays = LuckyDaysCalculator.findFavorableDaysInMonth(
                birthDate = birthDate,
                yearMonth = yearMonth
            )

            _uiState.update {
                it.copy(
                    selectedYearMonth = yearMonth,
                    currentMonthDays = monthDays
                )
            }
        }
    }
}
