package com.numero.storm.domain.calculator

/**
 * Comprehensive name analysis providing letter-by-letter breakdown,
 * plane analysis, and detailed number distributions.
 */
object NameAnalysis {

    /**
     * Perform complete name analysis with all aspects.
     */
    fun analyzeFullName(
        fullName: String,
        system: NumerologySystem = NumerologySystem.PYTHAGOREAN
    ): FullNameAnalysis {
        val cleanName = fullName.uppercase().filter { it.isLetter() || it == ' ' }
        val letterValues = system.getLetterValues()

        val letterAnalyses = mutableListOf<LetterAnalysis>()
        val numberCounts = mutableMapOf<Int, Int>()
        val planeDistribution = mutableMapOf<Plane, MutableList<Char>>()

        Plane.values().forEach { planeDistribution[it] = mutableListOf() }

        var position = 0
        for (char in cleanName) {
            if (char.isLetter()) {
                position++
                val value = letterValues[char] ?: continue
                val plane = getPlaneForLetter(char)
                val isVowel = NumerologyConstants.VOWELS.contains(char)

                letterAnalyses.add(
                    LetterAnalysis(
                        letter = char,
                        position = position,
                        value = value,
                        isVowel = isVowel,
                        plane = plane,
                        interpretation = getLetterInterpretation(char)
                    )
                )

                numberCounts[value] = (numberCounts[value] ?: 0) + 1
                planeDistribution[plane]?.add(char)
            }
        }

        // Calculate inclusion chart (Karmic lessons and Hidden Passion)
        val inclusionChart = (1..9).map { num ->
            InclusionNumber(
                number = num,
                count = numberCounts[num] ?: 0,
                status = when {
                    (numberCounts[num] ?: 0) == 0 -> InclusionStatus.MISSING
                    (numberCounts[num] ?: 0) >= 3 -> InclusionStatus.ABUNDANT
                    else -> InclusionStatus.PRESENT
                },
                interpretation = getInclusionInterpretation(num, numberCounts[num] ?: 0)
            )
        }

        // Calculate planes of expression
        val planeAnalyses = Plane.values().map { plane ->
            val letters = planeDistribution[plane] ?: emptyList()
            val total = letters.sumOf { letterValues[it] ?: 0 }
            val reduced = NumberReducer.reduce(total)
            PlaneAnalysis(
                plane = plane,
                letters = letters,
                totalValue = total,
                reducedValue = reduced,
                percentage = if (position > 0) (letters.size.toFloat() / position * 100).toInt() else 0,
                interpretation = getPlaneInterpretation(plane, reduced, letters.size)
            )
        }

        // Find cornerstone, capstone, and first vowel
        val nameParts = fullName.split(" ").filter { it.isNotBlank() }
        val firstName = nameParts.firstOrNull() ?: ""
        val cornerstone = firstName.firstOrNull { it.isLetter() }?.uppercaseChar()
        val capstone = firstName.lastOrNull { it.isLetter() }?.uppercaseChar()
        val firstVowel = fullName.firstOrNull {
            it.uppercaseChar() in NumerologyConstants.VOWELS
        }?.uppercaseChar()

        return FullNameAnalysis(
            originalName = fullName,
            letterAnalyses = letterAnalyses,
            inclusionChart = inclusionChart,
            planeAnalyses = planeAnalyses,
            totalLetters = position,
            cornerstone = cornerstone?.let {
                SpecialLetterAnalysis(
                    letter = it,
                    value = letterValues[it] ?: 0,
                    type = "Cornerstone",
                    meaning = getCornerstoneInterpretation(it)
                )
            },
            capstone = capstone?.let {
                SpecialLetterAnalysis(
                    letter = it,
                    value = letterValues[it] ?: 0,
                    type = "Capstone",
                    meaning = getCapstoneInterpretation(it)
                )
            },
            firstVowel = firstVowel?.let {
                SpecialLetterAnalysis(
                    letter = it,
                    value = letterValues[it] ?: 0,
                    type = "First Vowel",
                    meaning = getFirstVowelInterpretation(it)
                )
            },
            hiddenPassionNumber = findHiddenPassion(numberCounts),
            subconsciousSelfNumber = numberCounts.count { it.value > 0 }
        )
    }

    private fun findHiddenPassion(counts: Map<Int, Int>): Int? {
        val maxCount = counts.values.maxOrNull() ?: return null
        val maxNumbers = counts.filter { it.value == maxCount }.keys
        return if (maxCount >= 2 && maxNumbers.size == 1) maxNumbers.first() else null
    }

    private fun getPlaneForLetter(letter: Char): Plane {
        return when (letter.uppercaseChar()) {
            'A', 'E', 'I', 'O', 'U' -> Plane.EMOTIONAL
            'H', 'J', 'N', 'P' -> Plane.PHYSICAL
            'B', 'K', 'T', 'C', 'L', 'F', 'V', 'X' -> Plane.MENTAL
            'D', 'M', 'Q', 'G', 'W', 'Z', 'R', 'S', 'Y' -> Plane.INTUITIVE
            else -> Plane.MENTAL
        }
    }

    private fun getLetterInterpretation(letter: Char): String {
        return when (letter.uppercaseChar()) {
            'A' -> "Independent, ambitious, and pioneering. The first letter symbolizes new beginnings and leadership."
            'B' -> "Cooperative, sensitive, and diplomatic. Brings harmony and partnership energy."
            'C' -> "Creative, expressive, and social. Carries artistic and communication abilities."
            'D' -> "Determined, practical, and disciplined. Builds solid foundations through hard work."
            'E' -> "Freedom-loving, versatile, and curious. Brings adaptability and desire for experience."
            'F' -> "Nurturing, responsible, and loving. Carries domestic and family-oriented energy."
            'G' -> "Analytical, introspective, and spiritual. Seeks knowledge and deeper understanding."
            'H' -> "Achievement-oriented, authoritative, and ambitious. Carries material success energy."
            'I' -> "Humanitarian, compassionate, and artistic. Brings completion and universal love."
            'J' -> "Pioneering with added ambition. Contains leadership with practical drive."
            'K' -> "Intuitive master energy. Carries spiritual insight and high vibration."
            'L' -> "Creative communicator. Brings self-expression and artistic talents."
            'M' -> "Hardworking builder. Contains practical and methodical energy."
            'N' -> "Freedom-seeking communicator. Brings curiosity and versatility."
            'O' -> "Responsible nurturer. Carries love and domestic harmony."
            'P' -> "Deep thinker. Contains analytical and sometimes secretive energy."
            'Q' -> "Powerfully ambitious. Carries strong manifestation abilities."
            'R' -> "Humanitarian leader. Brings compassion with practical action."
            'S' -> "Independent initiator. Contains leadership and pioneering spirit."
            'T' -> "Emotionally perceptive. Carries partnership and intuitive energy."
            'U' -> "Creative accumulator. Brings artistic talent with material focus."
            'V' -> "Practical builder. Contains organizational and methodical energy."
            'W' -> "Freedom through expression. Carries versatility and adventure."
            'X' -> "Sensual and creative. Contains emotional depth and artistic talent."
            'Y' -> "Spiritually perceptive. Carries analytical and mystical qualities."
            'Z' -> "Ambitious achiever. Contains leadership with manifestation power."
            else -> "This letter carries unique vibrations contributing to your name energy."
        }
    }

    private fun getCornerstoneInterpretation(letter: Char): String {
        val base = when (letter.uppercaseChar()) {
            'A' -> "You approach new situations with independence and ambition. You like to take charge and prefer to be first."
            'B' -> "You approach new situations with sensitivity and diplomacy. You naturally consider others' feelings."
            'C' -> "You approach new situations with creativity and optimism. You bring joy and self-expression."
            'D' -> "You approach new situations with determination and practicality. You build methodically."
            'E' -> "You approach new situations with curiosity and adaptability. You embrace change and variety."
            'F' -> "You approach new situations with responsibility and care. You nurture and protect."
            'G' -> "You approach new situations analytically and thoughtfully. You seek understanding first."
            'H' -> "You approach new situations with ambition and authority. You aim for success."
            'I' -> "You approach new situations with compassion and idealism. You think of the greater good."
            'J' -> "You approach new situations with leadership and drive. You're both ambitious and pioneering."
            'K' -> "You approach new situations with intuition and spiritual awareness. You sense deeper meanings."
            'L' -> "You approach new situations with creativity and communication. You express yourself naturally."
            'M' -> "You approach new situations with hard work and patience. You build steadily."
            'N' -> "You approach new situations with curiosity and versatility. You're quick to adapt."
            'O' -> "You approach new situations with love and responsibility. Family matters guide you."
            'P' -> "You approach new situations quietly and analytically. You observe before acting."
            'Q' -> "You approach new situations with power and confidence. You expect success."
            'R' -> "You approach new situations with humanitarian concern. You want to help."
            'S' -> "You approach new situations with independence and determination. You're self-starting."
            'T' -> "You approach new situations with emotional awareness. You feel your way forward."
            'U' -> "You approach new situations with creativity and practicality combined. You're resourceful."
            'V' -> "You approach new situations methodically. You plan before acting."
            'W' -> "You approach new situations with enthusiasm and adaptability. You welcome change."
            'X' -> "You approach new situations with depth and emotion. You feel deeply."
            'Y' -> "You approach new situations thoughtfully and spiritually. You seek meaning."
            'Z' -> "You approach new situations with ambition and determination. You aim high."
            else -> "Your cornerstone shapes how you begin new endeavors."
        }
        return "Your Cornerstone ($letter) reveals how you approach new situations and begin projects. $base"
    }

    private fun getCapstoneInterpretation(letter: Char): String {
        val base = when (letter.uppercaseChar()) {
            'A' -> "You complete projects independently, often taking personal credit. You finish what you start."
            'B' -> "You complete projects by considering everyone involved. Harmony in endings matters to you."
            'C' -> "You complete projects with creative flourishes. You like to end on a high note."
            'D' -> "You complete projects thoroughly and practically. You ensure proper endings."
            'E' -> "You complete projects and quickly move to the next. You don't linger on endings."
            'F' -> "You complete projects by ensuring everyone is cared for. Responsible endings matter."
            'G' -> "You complete projects after careful reflection. You analyze what was learned."
            'H' -> "You complete projects with a focus on results achieved. Success is your measure."
            'I' -> "You complete projects with the bigger picture in mind. Universal impact matters."
            'J' -> "You complete projects with drive and determination. You push through to finish."
            'K' -> "You complete projects with spiritual awareness. The deeper meaning matters."
            'L' -> "You complete projects creatively. You express completion meaningfully."
            'M' -> "You complete projects thoroughly and systematically. Nothing is left undone."
            'N' -> "You complete projects but remain open to what's next. Flexibility in endings."
            'O' -> "You complete projects with care for all affected. Loving endings matter."
            'P' -> "You complete projects quietly and thoroughly. You reflect on completion."
            'Q' -> "You complete projects powerfully. You end on a strong note."
            'R' -> "You complete projects with humanitarian concern. How it affects others matters."
            'S' -> "You complete projects independently. You finish what you start on your terms."
            'T' -> "You complete projects with emotional sensitivity. Feelings guide endings."
            'U' -> "You complete projects practically and creatively. Resourceful endings."
            'V' -> "You complete projects systematically. Organized, thorough endings."
            'W' -> "You complete projects enthusiastically. You celebrate endings as new beginnings."
            'X' -> "You complete projects with emotional depth. Meaningful endings matter."
            'Y' -> "You complete projects thoughtfully. Spiritual reflection on completion."
            'Z' -> "You complete projects ambitiously. You aim to end on top."
            else -> "Your capstone shapes how you complete endeavors."
        }
        return "Your Capstone ($letter) reveals how you bring projects to completion and handle endings. $base"
    }

    private fun getFirstVowelInterpretation(letter: Char): String {
        val base = when (letter.uppercaseChar()) {
            'A' -> "Your first instinctive reaction is independence and self-assertion. You respond to challenges by taking charge."
            'E' -> "Your first instinctive reaction is curiosity and adaptation. You respond to challenges by seeking alternatives."
            'I' -> "Your first instinctive reaction is compassion and idealism. You respond to challenges by considering the greater good."
            'O' -> "Your first instinctive reaction is responsibility and care. You respond to challenges by protecting loved ones."
            'U' -> "Your first instinctive reaction is creativity and resourcefulness. You respond to challenges by finding unique solutions."
            'Y' -> "Your first instinctive reaction is spiritual and analytical. You respond to challenges by seeking deeper understanding."
            else -> "Your first vowel indicates your instinctive reactions."
        }
        return "Your First Vowel ($letter) reveals your instinctive, gut-level reactions to situations before conscious thought kicks in. $base"
    }

    private fun getInclusionInterpretation(number: Int, count: Int): String {
        val status = when {
            count == 0 -> "missing"
            count == 1 -> "present once"
            count == 2 -> "present twice"
            count >= 3 -> "abundant ($count times)"
            else -> "present"
        }

        val numberMeaning = when (number) {
            1 -> "independence, leadership, and initiative"
            2 -> "cooperation, partnership, and diplomacy"
            3 -> "creativity, expression, and communication"
            4 -> "stability, discipline, and hard work"
            5 -> "freedom, change, and adaptability"
            6 -> "responsibility, nurturing, and family"
            7 -> "analysis, spirituality, and introspection"
            8 -> "power, achievement, and material success"
            9 -> "humanitarianism, compassion, and completion"
            else -> "unique qualities"
        }

        return when {
            count == 0 -> "The number $number is missing from your name, indicating a karmic lesson around $numberMeaning. This is an area requiring conscious development."
            count >= 3 -> "The number $number appears $count times, making $numberMeaning a dominant theme and natural strength in your life."
            else -> "The number $number is $status, showing balanced access to $numberMeaning."
        }
    }

    private fun getPlaneInterpretation(plane: Plane, reducedValue: Int, letterCount: Int): String {
        val planeName = plane.displayName
        val planeDesc = when (plane) {
            Plane.PHYSICAL -> "how you deal with the material world, your body, and practical matters"
            Plane.MENTAL -> "how you think, communicate, and process information"
            Plane.EMOTIONAL -> "how you feel, relate to others, and handle emotions"
            Plane.INTUITIVE -> "how you access intuition, spirituality, and inner knowing"
        }

        val strength = when {
            letterCount == 0 -> "absent"
            letterCount <= 2 -> "minimal"
            letterCount <= 4 -> "moderate"
            letterCount <= 6 -> "strong"
            else -> "dominant"
        }

        return "Your $planeName Plane ($strength with $letterCount letters) governs $planeDesc. The reduced value of $reducedValue adds its specific influence to how you express through this plane."
    }
}

// Data Classes for Name Analysis

data class FullNameAnalysis(
    val originalName: String,
    val letterAnalyses: List<LetterAnalysis>,
    val inclusionChart: List<InclusionNumber>,
    val planeAnalyses: List<PlaneAnalysis>,
    val totalLetters: Int,
    val cornerstone: SpecialLetterAnalysis?,
    val capstone: SpecialLetterAnalysis?,
    val firstVowel: SpecialLetterAnalysis?,
    val hiddenPassionNumber: Int?,
    val subconsciousSelfNumber: Int
)

data class LetterAnalysis(
    val letter: Char,
    val position: Int,
    val value: Int,
    val isVowel: Boolean,
    val plane: Plane,
    val interpretation: String
)

data class InclusionNumber(
    val number: Int,
    val count: Int,
    val status: InclusionStatus,
    val interpretation: String
)

data class PlaneAnalysis(
    val plane: Plane,
    val letters: List<Char>,
    val totalValue: Int,
    val reducedValue: Int,
    val percentage: Int,
    val interpretation: String
)

data class SpecialLetterAnalysis(
    val letter: Char,
    val value: Int,
    val type: String,
    val meaning: String
)

enum class Plane(val displayName: String) {
    PHYSICAL("Physical"),
    MENTAL("Mental"),
    EMOTIONAL("Emotional"),
    INTUITIVE("Intuitive")
}

enum class InclusionStatus {
    MISSING,
    PRESENT,
    ABUNDANT
}

// Extension function for NumerologySystem
private fun NumerologySystem.getLetterValues(): Map<Char, Int> {
    return when (this) {
        NumerologySystem.PYTHAGOREAN -> NumerologyConstants.PYTHAGOREAN_VALUES
        NumerologySystem.CHALDEAN -> NumerologyConstants.CHALDEAN_VALUES
    }
}
