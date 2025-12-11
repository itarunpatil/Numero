package com.numero.storm.domain.calculator

import android.content.Context
import com.numero.storm.data.model.AppLanguage

/**
 * Provides localized numerology interpretations based on the selected language.
 * Reads interpretation strings from Android resources (values/strings.xml and values-ne/strings.xml).
 *
 * This object serves as the primary interface for retrieving localized numerology interpretations
 * throughout the app. It reads from Android string resources, which automatically handle
 * language selection based on the app's configured locale.
 */
object LocalizedInterpretations {

    /**
     * Get localized Life Path interpretation
     */
    fun getLocalizedLifePathInterpretation(
        context: Context,
        number: Int,
        language: AppLanguage
    ): NumberInterpretation {
        // Get the base reduced number for interpretation
        val interpretationNumber = when {
            number in 1..9 -> number
            number in listOf(11, 22, 33) -> number
            else -> NumberReducer.reduceToSingleDigit(number)
        }

        val prefix = "life_path_${interpretationNumber}_"

        return NumberInterpretation(
            number = number,
            title = getLocalizedString(context, prefix + "title", language),
            generalMeaning = getLocalizedString(context, prefix + "meaning", language),
            strengths = (1..5).map {
                getLocalizedString(context, prefix + "strength_$it", language)
            },
            challenges = (1..5).map {
                getLocalizedString(context, prefix + "challenge_$it", language)
            },
            careerPaths = (1..6).map {
                getLocalizedString(context, prefix + "career_$it", language)
            },
            relationshipInsights = getLocalizedString(context, prefix + "relationship", language),
            spiritualGuidance = getLocalizedString(context, prefix + "spiritual", language),
            luckyColors = getLocalizedString(context, prefix + "colors", language)
                .split(", "),
            luckyDays = getLocalizedString(context, prefix + "days", language)
                .split(", "),
            compatibleNumbers = getCompatibleNumbers(interpretationNumber)
        )
    }

    /**
     * Get localized Expression Number interpretation
     */
    fun getLocalizedExpressionInterpretation(
        context: Context,
        number: Int,
        language: AppLanguage
    ): String {
        val reducedNumber = NumberReducer.reduce(number)
        val interpretationNumber = when (reducedNumber) {
            in 1..9, 11, 22, 33 -> reducedNumber
            else -> NumberReducer.reduceToSingleDigit(reducedNumber)
        }
        return getLocalizedString(
            context,
            "expression_$interpretationNumber",
            language,
            NumerologyInterpretations.getExpressionInterpretation(number)
        )
    }

    /**
     * Get localized Soul Urge interpretation
     */
    fun getLocalizedSoulUrgeInterpretation(
        context: Context,
        number: Int,
        language: AppLanguage
    ): String {
        val reducedNumber = NumberReducer.reduce(number)
        val interpretationNumber = when (reducedNumber) {
            in 1..9, 11, 22, 33 -> reducedNumber
            else -> NumberReducer.reduceToSingleDigit(reducedNumber)
        }
        return getLocalizedString(
            context,
            "soul_urge_$interpretationNumber",
            language,
            NumerologyInterpretations.getSoulUrgeInterpretation(number)
        )
    }

    /**
     * Get localized Personality interpretation
     */
    fun getLocalizedPersonalityInterpretation(
        context: Context,
        number: Int,
        language: AppLanguage
    ): String {
        val reducedNumber = NumberReducer.reduce(number)
        val interpretationNumber = when (reducedNumber) {
            in 1..9, 11, 22, 33 -> reducedNumber
            else -> NumberReducer.reduceToSingleDigit(reducedNumber)
        }
        return getLocalizedString(
            context,
            "personality_$interpretationNumber",
            language,
            NumerologyInterpretations.getPersonalityInterpretation(number)
        )
    }

    /**
     * Get localized Birthday Number interpretation
     */
    fun getLocalizedBirthdayInterpretation(
        context: Context,
        day: Int,
        language: AppLanguage
    ): String {
        val interpretationDay = if (day in 1..31) day else NumberReducer.reduceToSingleDigit(day)
        return getLocalizedString(
            context,
            "birthday_$interpretationDay",
            language,
            NumerologyInterpretations.getBirthdayInterpretation(day)
        )
    }

    /**
     * Get localized Personal Year interpretation
     */
    fun getLocalizedPersonalYearInterpretation(
        context: Context,
        number: Int,
        language: AppLanguage
    ): String {
        val reducedNumber = NumberReducer.reduceToSingleDigit(number)
        return getLocalizedString(
            context,
            "personal_year_$reducedNumber",
            language,
            NumerologyInterpretations.getPersonalYearInterpretation(number)
        )
    }

    /**
     * Get localized Personal Month interpretation
     */
    fun getLocalizedPersonalMonthInterpretation(
        context: Context,
        number: Int,
        language: AppLanguage
    ): String {
        val reducedNumber = NumberReducer.reduceToSingleDigit(number)
        return getLocalizedString(
            context,
            "personal_month_$reducedNumber",
            language,
            "This month carries the energy of $reducedNumber. Focus on activities aligned with this vibration."
        )
    }

    /**
     * Get localized Personal Day interpretation
     */
    fun getLocalizedPersonalDayInterpretation(
        context: Context,
        number: Int,
        language: AppLanguage
    ): String {
        val reducedNumber = NumberReducer.reduceToSingleDigit(number)
        return getLocalizedString(
            context,
            "personal_day_$reducedNumber",
            language,
            "Today carries the energy of $reducedNumber."
        )
    }

    /**
     * Get localized Karmic Debt interpretation
     */
    fun getLocalizedKarmicDebtInterpretation(
        context: Context,
        number: Int,
        language: AppLanguage
    ): String {
        return when (number) {
            13, 14, 16, 19 -> getLocalizedString(
                context,
                "karmic_debt_$number",
                language,
                NumerologyInterpretations.getKarmicDebtInterpretation(number)
            )
            else -> NumerologyInterpretations.getKarmicDebtInterpretation(number)
        }
    }

    /**
     * Get localized Karmic Lesson interpretation
     */
    fun getLocalizedKarmicLessonInterpretation(
        context: Context,
        number: Int,
        language: AppLanguage
    ): String {
        return if (number in 1..9) {
            getLocalizedString(
                context,
                "karmic_lesson_$number",
                language,
                "Missing $number in your name indicates a lesson to develop the qualities of this number."
            )
        } else {
            "This number does not represent a karmic lesson."
        }
    }

    /**
     * Get localized Pinnacle interpretation
     */
    fun getLocalizedPinnacleInterpretation(
        context: Context,
        number: Int,
        language: AppLanguage
    ): String {
        val interpretationNumber = when (number) {
            in 1..9, 11, 22 -> number
            else -> NumberReducer.reduceToSingleDigit(number)
        }
        return getLocalizedString(
            context,
            "pinnacle_$interpretationNumber",
            language,
            "This pinnacle carries the energy of $interpretationNumber."
        )
    }

    /**
     * Get localized Challenge interpretation
     */
    fun getLocalizedChallengeInterpretation(
        context: Context,
        number: Int,
        language: AppLanguage
    ): String {
        val interpretationNumber = if (number in 0..8) number else NumberReducer.reduceToSingleDigit(number)
        return getLocalizedString(
            context,
            "challenge_$interpretationNumber",
            language,
            "This challenge involves learning to work with the energy of $interpretationNumber."
        )
    }

    /**
     * Get localized Health Guidance for Life Path
     */
    fun getLocalizedHealthGuidance(
        context: Context,
        lifePathNumber: Int,
        language: AppLanguage
    ): String {
        val interpretationNumber = when (lifePathNumber) {
            in 1..9 -> lifePathNumber
            else -> NumberReducer.reduceToSingleDigit(lifePathNumber)
        }
        return getLocalizedString(
            context,
            "health_life_path_$interpretationNumber",
            language,
            "Focus on balance and self-care to maintain optimal health."
        )
    }

    /**
     * Get localized Maturity Number interpretation
     */
    fun getLocalizedMaturityInterpretation(
        context: Context,
        number: Int,
        language: AppLanguage
    ): String {
        val interpretationNumber = when (number) {
            in 1..9, 11, 22, 33 -> number
            else -> NumberReducer.reduceToSingleDigit(number)
        }
        return getLocalizedString(
            context,
            "maturity_$interpretationNumber",
            language,
            getLocalizedString(context, "maturity_number_interpretation", language,
                "Your Maturity Number represents the true self that emerges in the second half of life.")
        )
    }

    /**
     * Get localized Balance Number interpretation
     */
    fun getLocalizedBalanceInterpretation(
        context: Context,
        number: Int,
        language: AppLanguage
    ): String {
        val interpretationNumber = if (number in 1..9) number else NumberReducer.reduceToSingleDigit(number)
        return getLocalizedString(
            context,
            "balance_$interpretationNumber",
            language,
            getLocalizedString(context, "balance_number_interpretation", language,
                "Your Balance Number reveals how you handle difficult situations and find equilibrium during challenging times.")
        )
    }

    /**
     * Get localized Hidden Passion interpretation
     */
    fun getLocalizedHiddenPassionInterpretation(
        context: Context,
        number: Int?,
        language: AppLanguage
    ): String {
        return if (number != null && number in 1..9) {
            getLocalizedString(
                context,
                "hidden_passion_$number",
                language,
                getLocalizedString(context, "hidden_passion_interpretation", language,
                    "Your Hidden Passion Number indicates your strongest talents and abilities.")
            )
        } else {
            getLocalizedString(
                context,
                "no_hidden_passion",
                language,
                "No clear Hidden Passion number was found in your name."
            )
        }
    }

    /**
     * Get localized No Karmic Lessons message
     */
    fun getLocalizedNoKarmicLessons(
        context: Context,
        language: AppLanguage
    ): String {
        return getLocalizedString(
            context,
            "no_karmic_lessons",
            language,
            "You have no karmic lessons - all numbers are represented in your name."
        )
    }

    /**
     * Get compatible numbers for a given life path number
     */
    private fun getCompatibleNumbers(number: Int): List<Int> {
        return when (number) {
            1 -> listOf(1, 3, 5, 9)
            2 -> listOf(2, 4, 6, 8)
            3 -> listOf(1, 3, 5, 6, 9)
            4 -> listOf(2, 4, 6, 7, 8)
            5 -> listOf(1, 3, 5, 7, 9)
            6 -> listOf(2, 3, 4, 6, 9)
            7 -> listOf(4, 5, 7, 9)
            8 -> listOf(2, 4, 6, 8)
            9 -> listOf(1, 3, 5, 6, 7, 9)
            11 -> listOf(2, 4, 6, 11, 22)
            22 -> listOf(4, 6, 8, 11, 22)
            33 -> listOf(6, 9, 11, 22, 33)
            else -> emptyList()
        }
    }

    /**
     * Helper function to get localized string from context resources
     * Falls back to English default if string not found
     */
    private fun getLocalizedString(
        context: Context,
        stringName: String,
        language: AppLanguage,
        fallback: String = ""
    ): String {
        return try {
            val resourceId = context.resources.getIdentifier(
                stringName,
                "string",
                context.packageName
            )
            if (resourceId != 0) {
                context.getString(resourceId)
            } else {
                fallback
            }
        } catch (e: Exception) {
            fallback
        }
    }
}
