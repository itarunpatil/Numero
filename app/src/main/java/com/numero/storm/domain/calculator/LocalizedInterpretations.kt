package com.numero.storm.domain.calculator

import android.content.Context
import com.numero.storm.data.model.AppLanguage

/**
 * Provides localized numerology interpretations based on the selected language.
 * Reads interpretation strings from Android resources (values/strings.xml and values-ne/strings.xml).
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

        return when (reducedNumber) {
            1 -> getLocalizedString(context, "expression_1", language,
                "Your Expression Number 1 reveals natural talents in leadership and pioneering. You are meant to express originality and independence. Your destiny involves creating new paths and inspiring others to follow their own unique vision.")
            2 -> getLocalizedString(context, "expression_2", language,
                "Your Expression Number 2 shows talents in diplomacy and cooperation. You are destined to bring harmony and balance. Your natural abilities lie in supporting others, mediating conflicts, and creating peaceful environments.")
            3 -> getLocalizedString(context, "expression_3", language,
                "Your Expression Number 3 indicates creative and communicative talents. You are meant to express yourself through art, writing, or speaking. Your destiny involves inspiring joy and optimism in others through your creative gifts.")
            4 -> getLocalizedString(context, "expression_4", language,
                "Your Expression Number 4 reveals practical and organizational talents. You are destined to build lasting foundations. Your abilities lie in systematic thinking, detailed work, and creating stable structures.")
            5 -> getLocalizedString(context, "expression_5", language,
                "Your Expression Number 5 shows talents in adaptability and communication. You are meant to experience and share life's variety. Your destiny involves promoting change, freedom, and progressive ideas.")
            6 -> getLocalizedString(context, "expression_6", language,
                "Your Expression Number 6 indicates nurturing and artistic talents. You are destined to create harmony in home and community. Your natural abilities lie in healing, teaching, and caring for others.")
            7 -> getLocalizedString(context, "expression_7", language,
                "Your Expression Number 7 reveals analytical and spiritual talents. You are meant to seek and share wisdom. Your destiny involves deep research, spiritual exploration, and teaching others what you discover.")
            8 -> getLocalizedString(context, "expression_8", language,
                "Your Expression Number 8 shows executive and financial talents. You are destined for material achievement and authority. Your abilities lie in organization, business, and manifesting abundance.")
            9 -> getLocalizedString(context, "expression_9", language,
                "Your Expression Number 9 indicates humanitarian and artistic talents. You are meant to serve humanity broadly. Your destiny involves compassion, wisdom, and contributing to the greater good.")
            11 -> getLocalizedString(context, "expression_11", language,
                "Your Expression Number 11 reveals visionary and intuitive talents. You are destined to inspire and illuminate. Your abilities lie in spiritual insight, artistic vision, and uplifting others.")
            22 -> getLocalizedString(context, "expression_22", language,
                "Your Expression Number 22 shows master building talents. You are destined to create lasting institutions. Your abilities lie in manifesting grand visions into practical reality.")
            33 -> getLocalizedString(context, "expression_33", language,
                "Your Expression Number 33 indicates master teaching talents. You are destined to heal and uplift humanity. Your abilities lie in selfless service, healing, and spiritual guidance.")
            else -> getLocalizedExpressionInterpretation(context, NumberReducer.reduceToSingleDigit(number), language)
        }
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

        return when (reducedNumber) {
            1 -> "Your Soul Urge 1 reveals an inner desire for independence and achievement. Deep down, you crave being first, original, and recognized for your individual accomplishments."
            2 -> "Your Soul Urge 2 shows an inner desire for love and partnership. At your core, you crave harmony, deep connections, and working cooperatively with others."
            3 -> "Your Soul Urge 3 reveals an inner desire for creative expression and joy. Deep down, you crave artistic fulfillment, social recognition, and the freedom to express yourself."
            4 -> "Your Soul Urge 4 shows an inner desire for security and order. At your core, you crave stability, a solid foundation, and the satisfaction of hard work paying off."
            5 -> "Your Soul Urge 5 reveals an inner desire for freedom and adventure. Deep down, you crave variety, new experiences, and the liberty to explore life without restrictions."
            6 -> "Your Soul Urge 6 shows an inner desire for love and family. At your core, you crave a harmonious home, meaningful relationships, and the opportunity to nurture others."
            7 -> "Your Soul Urge 7 reveals an inner desire for knowledge and understanding. Deep down, you crave wisdom, spiritual connection, and time alone to reflect and analyze."
            8 -> "Your Soul Urge 8 shows an inner desire for success and recognition. At your core, you crave material achievement, authority, and the respect that comes with accomplishment."
            9 -> "Your Soul Urge 9 reveals an inner desire to make a difference. Deep down, you crave contributing to humanity, universal love, and leaving a meaningful legacy."
            11 -> "Your Soul Urge 11 shows an inner desire for spiritual illumination. At your core, you crave inspiring others, receiving spiritual insights, and living a purpose-driven life."
            22 -> "Your Soul Urge 22 reveals an inner desire to build something lasting. Deep down, you crave manifesting great visions that benefit humanity on a large scale."
            33 -> "Your Soul Urge 33 shows an inner desire for universal healing. At your core, you crave uplifting humanity through selfless love and spiritual guidance."
            else -> getLocalizedSoulUrgeInterpretation(context, NumberReducer.reduceToSingleDigit(number), language)
        }
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

        return when (reducedNumber) {
            1 -> "Others see you as confident, independent, and a natural leader. You project an image of strength and self-reliance that others find inspiring."
            2 -> "Others see you as cooperative, diplomatic, and supportive. You project an image of gentleness and approachability that puts people at ease."
            3 -> "Others see you as creative, charming, and optimistic. You project an image of joy and enthusiasm that attracts social attention."
            4 -> "Others see you as reliable, practical, and hardworking. You project an image of stability and trustworthiness that people depend on."
            5 -> "Others see you as dynamic, adventurous, and versatile. You project an image of freedom and excitement that intrigues those around you."
            6 -> "Others see you as nurturing, responsible, and artistic. You project an image of warmth and domesticity that makes people feel cared for."
            7 -> "Others see you as intelligent, reserved, and mysterious. You project an image of wisdom and depth that commands respect."
            8 -> "Others see you as powerful, successful, and authoritative. You project an image of capability and ambition that impresses others."
            9 -> "Others see you as compassionate, sophisticated, and worldly. You project an image of wisdom and humanitarianism that inspires trust."
            11 -> "Others see you as inspirational, intuitive, and visionary. You project an image of spiritual depth that fascinates and uplifts."
            22 -> "Others see you as capable, disciplined, and visionary. You project an image of someone who can achieve great things."
            33 -> "Others see you as loving, healing, and selflessly devoted. You project an image of spiritual mastery and unconditional love."
            else -> getLocalizedPersonalityInterpretation(context, NumberReducer.reduceToSingleDigit(number), language)
        }
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
