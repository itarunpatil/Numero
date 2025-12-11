package com.numero.storm.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.numero.storm.data.model.AppLanguage
import com.numero.storm.data.model.ChallengeEntity
import com.numero.storm.data.model.LifePeriodEntity
import com.numero.storm.data.model.NumerologyAnalysis
import com.numero.storm.data.model.PinnacleEntity
import com.numero.storm.data.model.Profile
import com.numero.storm.data.model.getKarmicLessonsList
import com.numero.storm.data.repository.NumerologyRepository
import com.numero.storm.data.repository.ProfileRepository
import com.numero.storm.data.repository.SettingsRepository
import com.numero.storm.domain.calculator.DateCalculator
import com.numero.storm.domain.calculator.LocalizedInterpretations
import com.numero.storm.domain.calculator.NumberInterpretation
import com.numero.storm.domain.calculator.NumerologyInterpretations
import com.numero.storm.domain.calculator.NumerologySystem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class AnalysisUiState(
    val isLoading: Boolean = true,
    val profile: Profile? = null,
    val analysis: NumerologyAnalysis? = null,
    val pinnacles: List<PinnacleEntity> = emptyList(),
    val challenges: List<ChallengeEntity> = emptyList(),
    val lifePeriods: List<LifePeriodEntity> = emptyList(),
    val currentAge: Int = 0,
    val currentPinnacleIndex: Int = 0,
    val currentChallengeIndex: Int = 0,
    val currentLifePeriodIndex: Int = 0,
    val numerologySystem: NumerologySystem = NumerologySystem.PYTHAGOREAN,
    val showMasterNumbers: Boolean = true,
    val showKarmicDebt: Boolean = true,
    val error: String? = null
)

data class AnalysisDetailUiState(
    val isLoading: Boolean = true,
    val profile: Profile? = null,
    val numberType: String = "",
    val numberValue: Int = 0,
    val isMasterNumber: Boolean = false,
    val karmicDebt: Int? = null,
    val interpretation: NumberInterpretation? = null,
    val interpretationText: String = "",
    val additionalInfo: Map<String, Any> = emptyMap(),
    val error: String? = null
)

@HiltViewModel
class AnalysisViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val numerologyRepository: NumerologyRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnalysisUiState())
    val uiState: StateFlow<AnalysisUiState> = _uiState.asStateFlow()

    fun loadAnalysis(profileId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val settings = settingsRepository.getSettings().first()
                val profile = profileRepository.getProfileByIdOnce(profileId)

                if (profile == null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Profile not found"
                    )
                    return@launch
                }

                var analysis = numerologyRepository.getAnalysis(profileId, settings.numerologySystem)

                if (analysis == null) {
                    val analysisId = numerologyRepository.calculateAndSaveAnalysis(
                        profile,
                        settings.numerologySystem
                    )
                    analysis = numerologyRepository.getAnalysis(profileId, settings.numerologySystem)
                }

                val pinnacles = analysis?.let { numerologyRepository.getPinnacles(it.id) } ?: emptyList()
                val challenges = analysis?.let { numerologyRepository.getChallenges(it.id) } ?: emptyList()
                val lifePeriods = analysis?.let { numerologyRepository.getLifePeriods(it.id) } ?: emptyList()

                val currentAge = DateCalculator.calculateAge(profile.birthDate)

                val currentPinnacleIndex = pinnacles.indexOfFirst { pinnacle ->
                    currentAge >= pinnacle.startAge && (pinnacle.endAge == null || currentAge <= pinnacle.endAge)
                }.takeIf { it >= 0 } ?: 0

                val currentChallengeIndex = challenges.indexOfFirst { challenge ->
                    currentAge >= challenge.startAge && (challenge.endAge == null || currentAge <= challenge.endAge)
                }.takeIf { it >= 0 } ?: 0

                val currentLifePeriodIndex = lifePeriods.indexOfFirst { period ->
                    currentAge >= period.startAge && (period.endAge == null || currentAge <= period.endAge)
                }.takeIf { it >= 0 } ?: 0

                _uiState.value = AnalysisUiState(
                    isLoading = false,
                    profile = profile,
                    analysis = analysis,
                    pinnacles = pinnacles,
                    challenges = challenges,
                    lifePeriods = lifePeriods,
                    currentAge = currentAge,
                    currentPinnacleIndex = currentPinnacleIndex,
                    currentChallengeIndex = currentChallengeIndex,
                    currentLifePeriodIndex = currentLifePeriodIndex,
                    numerologySystem = settings.numerologySystem,
                    showMasterNumbers = settings.showMasterNumbers,
                    showKarmicDebt = settings.showKarmicDebt
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load analysis"
                )
            }
        }
    }

    fun recalculateAnalysis() {
        val profile = _uiState.value.profile ?: return
        val system = _uiState.value.numerologySystem

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                numerologyRepository.deleteAnalysesForProfile(profile.id)
                loadAnalysis(profile.id)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to recalculate"
                )
            }
        }
    }
}

@HiltViewModel
class AnalysisDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val profileRepository: ProfileRepository,
    private val numerologyRepository: NumerologyRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnalysisDetailUiState())
    val uiState: StateFlow<AnalysisDetailUiState> = _uiState.asStateFlow()

    fun loadDetail(profileId: Long, numberType: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val settings = settingsRepository.getSettings().first()
                val profile = profileRepository.getProfileByIdOnce(profileId)

                if (profile == null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Profile not found"
                    )
                    return@launch
                }

                val analysis = numerologyRepository.getAnalysis(profileId, settings.numerologySystem)

                if (analysis == null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Analysis not found"
                    )
                    return@launch
                }

                val (numberValue, isMaster, karmicDebt, interpretation, interpretationText, additionalInfo) =
                    getNumberDetails(numberType, analysis, profile, settings.language)

                _uiState.value = AnalysisDetailUiState(
                    isLoading = false,
                    profile = profile,
                    numberType = numberType,
                    numberValue = numberValue,
                    isMasterNumber = isMaster,
                    karmicDebt = karmicDebt,
                    interpretation = interpretation,
                    interpretationText = interpretationText,
                    additionalInfo = additionalInfo
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load details"
                )
            }
        }
    }

    private fun getNumberDetails(
        numberType: String,
        analysis: NumerologyAnalysis,
        profile: Profile,
        language: AppLanguage
    ): NumberDetails {
        return when (numberType.uppercase()) {
            "LIFE_PATH" -> NumberDetails(
                numberValue = analysis.lifePathNumber,
                isMaster = analysis.lifePathMasterNumber,
                karmicDebt = analysis.lifePathKarmicDebt,
                interpretation = LocalizedInterpretations.getLocalizedLifePathInterpretation(
                    context, analysis.lifePathNumber, language
                ),
                interpretationText = "",
                additionalInfo = mapOf(
                    "birthDate" to profile.birthDate.toString()
                )
            )
            "EXPRESSION" -> NumberDetails(
                numberValue = analysis.expressionNumber,
                isMaster = analysis.expressionMasterNumber,
                karmicDebt = analysis.expressionKarmicDebt,
                interpretation = null,
                interpretationText = LocalizedInterpretations.getLocalizedExpressionInterpretation(
                    context, analysis.expressionNumber, language
                ),
                additionalInfo = mapOf(
                    "fullName" to profile.fullName
                )
            )
            "SOUL_URGE" -> NumberDetails(
                numberValue = analysis.soulUrgeNumber,
                isMaster = analysis.soulUrgeMasterNumber,
                karmicDebt = analysis.soulUrgeKarmicDebt,
                interpretation = null,
                interpretationText = LocalizedInterpretations.getLocalizedSoulUrgeInterpretation(
                    context, analysis.soulUrgeNumber, language
                ),
                additionalInfo = emptyMap()
            )
            "PERSONALITY" -> NumberDetails(
                numberValue = analysis.personalityNumber,
                isMaster = analysis.personalityMasterNumber,
                karmicDebt = analysis.personalityKarmicDebt,
                interpretation = null,
                interpretationText = LocalizedInterpretations.getLocalizedPersonalityInterpretation(
                    context, analysis.personalityNumber, language
                ),
                additionalInfo = emptyMap()
            )
            "BIRTHDAY" -> NumberDetails(
                numberValue = analysis.birthdayNumber,
                isMaster = analysis.birthdayMasterNumber,
                karmicDebt = null,
                interpretation = null,
                interpretationText = LocalizedInterpretations.getLocalizedBirthdayInterpretation(
                    context, profile.birthDate.dayOfMonth, language
                ),
                additionalInfo = mapOf(
                    "birthDay" to profile.birthDate.dayOfMonth
                )
            )
            "MATURITY" -> NumberDetails(
                numberValue = analysis.maturityNumber,
                isMaster = analysis.maturityMasterNumber,
                karmicDebt = null,
                interpretation = null,
                interpretationText = LocalizedInterpretations.getLocalizedMaturityInterpretation(
                    context, analysis.maturityNumber, language
                ),
                additionalInfo = mapOf(
                    "lifePath" to analysis.lifePathNumber,
                    "expression" to analysis.expressionNumber
                )
            )
            "BALANCE" -> NumberDetails(
                numberValue = analysis.balanceNumber,
                isMaster = false,
                karmicDebt = null,
                interpretation = null,
                interpretationText = LocalizedInterpretations.getLocalizedBalanceInterpretation(
                    context, analysis.balanceNumber, language
                ),
                additionalInfo = emptyMap()
            )
            "HIDDEN_PASSION" -> NumberDetails(
                numberValue = analysis.hiddenPassionNumber ?: 0,
                isMaster = false,
                karmicDebt = null,
                interpretation = null,
                interpretationText = LocalizedInterpretations.getLocalizedHiddenPassionInterpretation(
                    context, analysis.hiddenPassionNumber, language
                ),
                additionalInfo = emptyMap()
            )
            "KARMIC_LESSONS" -> {
                val lessons = analysis.getKarmicLessonsList()
                val lessonsText = if (lessons.isEmpty()) {
                    LocalizedInterpretations.getLocalizedNoKarmicLessons(context, language)
                } else {
                    val lessonsInterpretations = lessons.mapNotNull { lessonNum ->
                        LocalizedInterpretations.getLocalizedKarmicLessonInterpretation(context, lessonNum, language)
                    }.joinToString("\n\n")
                    lessonsInterpretations.ifEmpty {
                        lessons.joinToString(", ")
                    }
                }
                NumberDetails(
                    numberValue = lessons.size,
                    isMaster = false,
                    karmicDebt = null,
                    interpretation = null,
                    interpretationText = lessonsText,
                    additionalInfo = mapOf(
                        "lessons" to lessons.toList()
                    )
                )
            }
            else -> NumberDetails(
                numberValue = 0,
                isMaster = false,
                karmicDebt = null,
                interpretation = null,
                interpretationText = "Unknown number type",
                additionalInfo = emptyMap()
            )
        }
    }

    private data class NumberDetails(
        val numberValue: Int,
        val isMaster: Boolean,
        val karmicDebt: Int?,
        val interpretation: NumberInterpretation?,
        val interpretationText: String,
        val additionalInfo: Map<String, Any>
    )
}
