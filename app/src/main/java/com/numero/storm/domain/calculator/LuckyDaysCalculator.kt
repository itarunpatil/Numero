package com.numero.storm.domain.calculator

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

/**
 * Calculates lucky days, favorable times, and optimal periods based on numerological analysis.
 */
object LuckyDaysCalculator {

    /**
     * Get all lucky information for a person based on their numerology profile.
     */
    fun calculateLuckyProfile(
        lifePathNumber: Int,
        expressionNumber: Int,
        birthdayNumber: Int,
        birthDate: LocalDate
    ): LuckyProfile {
        val lifePathReduced = NumberReducer.reduce(lifePathNumber)
        val expressionReduced = NumberReducer.reduce(expressionNumber)
        val birthdayReduced = NumberReducer.reduceToSingleDigit(birthdayNumber)

        return LuckyProfile(
            luckyNumbers = calculateLuckyNumbers(lifePathReduced, expressionReduced, birthdayReduced),
            luckyDays = calculateLuckyDaysOfWeek(lifePathReduced),
            luckyDates = calculateLuckyDatesOfMonth(lifePathReduced, birthdayNumber),
            luckyMonths = calculateLuckyMonths(lifePathReduced, birthDate),
            luckyColors = getLuckyColors(lifePathReduced),
            luckyGemstones = getLuckyGemstones(lifePathReduced),
            luckyDirections = getLuckyDirections(lifePathReduced),
            favorableActivities = getFavorableActivities(lifePathReduced),
            planetaryInfluence = getPlanetaryInfluence(lifePathReduced),
            elementalAffinity = getElementalAffinity(lifePathReduced)
        )
    }

    /**
     * Find the most favorable days in a given month based on personal cycles.
     */
    fun findFavorableDaysInMonth(
        birthDate: LocalDate,
        yearMonth: YearMonth
    ): MonthlyFavorableDays {
        val days = mutableListOf<FavorableDay>()
        val personalYear = DateCalculator.calculatePersonalYear(birthDate, yearMonth.year)
        val personalMonth = DateCalculator.calculatePersonalMonth(personalYear, yearMonth.monthValue)

        for (day in 1..yearMonth.lengthOfMonth()) {
            val date = yearMonth.atDay(day)
            val personalDay = DateCalculator.calculatePersonalDay(personalMonth, day)
            val universalDay = DateCalculator.calculateUniversalDay(date)

            val favorability = calculateDayFavorability(
                personalYear, personalMonth, personalDay, universalDay, date.dayOfWeek
            )

            days.add(
                FavorableDay(
                    date = date,
                    personalDay = personalDay,
                    universalDay = universalDay,
                    favorabilityScore = favorability.score,
                    favorabilityLevel = favorability.level,
                    bestFor = favorability.bestFor,
                    caution = favorability.caution
                )
            )
        }

        val excellentDays = days.filter { it.favorabilityLevel == FavorabilityLevel.EXCELLENT }
        val goodDays = days.filter { it.favorabilityLevel == FavorabilityLevel.GOOD }
        val challengingDays = days.filter { it.favorabilityLevel == FavorabilityLevel.CHALLENGING }

        return MonthlyFavorableDays(
            yearMonth = yearMonth,
            personalMonth = personalMonth,
            allDays = days,
            excellentDays = excellentDays,
            goodDays = goodDays,
            challengingDays = challengingDays,
            monthTheme = getPersonalMonthTheme(personalMonth),
            monthAdvice = getPersonalMonthAdvice(personalMonth)
        )
    }

    /**
     * Get lucky timing recommendations for specific activities.
     */
    fun getActivityTiming(
        activity: ActivityType,
        lifePathNumber: Int,
        birthDate: LocalDate,
        targetMonth: YearMonth
    ): ActivityTimingRecommendation {
        val favorableDays = findFavorableDaysInMonth(birthDate, targetMonth)
        val activityNumbers = getActivityFavorableNumbers(activity)

        val bestDays = favorableDays.allDays.filter { day ->
            activityNumbers.contains(day.personalDay) || activityNumbers.contains(day.universalDay)
        }.sortedByDescending { it.favorabilityScore }

        val avoidDays = favorableDays.allDays.filter { day ->
            getActivityUnfavorableNumbers(activity).contains(day.personalDay)
        }

        return ActivityTimingRecommendation(
            activity = activity,
            bestDays = bestDays.take(5),
            acceptableDays = favorableDays.goodDays.filter { !avoidDays.contains(it) }.take(5),
            daysToAvoid = avoidDays.take(3),
            generalAdvice = getActivityAdvice(activity, lifePathNumber),
            bestHours = getActivityBestHours(activity)
        )
    }

    private fun calculateLuckyNumbers(lifePath: Int, expression: Int, birthday: Int): List<LuckyNumber> {
        val numbers = mutableListOf<LuckyNumber>()

        // Primary lucky number is Life Path
        numbers.add(LuckyNumber(
            number = lifePath,
            significance = "Primary",
            reason = "Your Life Path number is your most significant lucky number, representing your core essence."
        ))

        // Secondary numbers
        numbers.add(LuckyNumber(
            number = expression,
            significance = "Secondary",
            reason = "Your Expression number brings luck in areas of self-expression and talents."
        ))

        numbers.add(LuckyNumber(
            number = birthday,
            significance = "Birth Gift",
            reason = "Your birthday number carries special luck tied to your birth energy."
        ))

        // Compatible numbers
        val compatible = getCompatibleNumbers(lifePath)
        compatible.forEach { num ->
            if (num != lifePath && num != expression && num != birthday) {
                numbers.add(LuckyNumber(
                    number = num,
                    significance = "Compatible",
                    reason = "This number harmonizes well with your Life Path energy."
                ))
            }
        }

        return numbers.distinctBy { it.number }
    }

    private fun getCompatibleNumbers(lifePath: Int): List<Int> {
        return when (lifePath) {
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
            else -> listOf(1, 5, 9)
        }
    }

    private fun calculateLuckyDaysOfWeek(lifePath: Int): List<LuckyDayOfWeek> {
        val days = when (lifePath) {
            1 -> listOf(DayOfWeek.SUNDAY to "Ruled by Sun, enhances leadership", DayOfWeek.TUESDAY to "Mars energy supports action")
            2 -> listOf(DayOfWeek.MONDAY to "Moon day for intuition", DayOfWeek.FRIDAY to "Venus enhances relationships")
            3 -> listOf(DayOfWeek.WEDNESDAY to "Mercury boosts communication", DayOfWeek.THURSDAY to "Jupiter expands creativity")
            4 -> listOf(DayOfWeek.SATURDAY to "Saturn supports discipline", DayOfWeek.SUNDAY to "Sun energy for authority")
            5 -> listOf(DayOfWeek.WEDNESDAY to "Mercury enhances versatility", DayOfWeek.FRIDAY to "Venus brings enjoyment")
            6 -> listOf(DayOfWeek.FRIDAY to "Venus rules love and beauty", DayOfWeek.THURSDAY to "Jupiter expands family")
            7 -> listOf(DayOfWeek.MONDAY to "Moon deepens intuition", DayOfWeek.SATURDAY to "Saturn supports reflection")
            8 -> listOf(DayOfWeek.SATURDAY to "Saturn rules achievement", DayOfWeek.THURSDAY to "Jupiter brings abundance")
            9 -> listOf(DayOfWeek.TUESDAY to "Mars supports humanitarian action", DayOfWeek.THURSDAY to "Jupiter expands compassion")
            11 -> listOf(DayOfWeek.MONDAY to "Moon heightens psychic ability", DayOfWeek.SUNDAY to "Sun illuminates vision")
            22 -> listOf(DayOfWeek.SATURDAY to "Saturn supports building", DayOfWeek.MONDAY to "Moon guides intuition")
            33 -> listOf(DayOfWeek.FRIDAY to "Venus enhances healing love", DayOfWeek.THURSDAY to "Jupiter expands service")
            else -> listOf(DayOfWeek.SUNDAY to "Universal day of new beginnings")
        }

        return days.map { (day, reason) ->
            LuckyDayOfWeek(
                day = day,
                reason = reason,
                bestActivities = getBestActivitiesForDay(day)
            )
        }
    }

    private fun getBestActivitiesForDay(day: DayOfWeek): List<String> {
        return when (day) {
            DayOfWeek.SUNDAY -> listOf("Starting new projects", "Leadership activities", "Creative work")
            DayOfWeek.MONDAY -> listOf("Reflection", "Intuitive work", "Emotional connections")
            DayOfWeek.TUESDAY -> listOf("Taking action", "Physical activities", "Competition")
            DayOfWeek.WEDNESDAY -> listOf("Communication", "Travel", "Learning", "Business")
            DayOfWeek.THURSDAY -> listOf("Expansion", "Legal matters", "Teaching", "Publishing")
            DayOfWeek.FRIDAY -> listOf("Social events", "Romance", "Art", "Beauty treatments")
            DayOfWeek.SATURDAY -> listOf("Organization", "Career focus", "Discipline", "Long-term planning")
        }
    }

    private fun calculateLuckyDatesOfMonth(lifePath: Int, birthday: Int): List<Int> {
        val dates = mutableSetOf<Int>()

        // Birthday is always lucky
        if (birthday in 1..31) dates.add(birthday)

        // Dates that reduce to life path
        for (i in 1..31) {
            if (NumberReducer.reduceToSingleDigit(i) == NumberReducer.reduceToSingleDigit(lifePath)) {
                dates.add(i)
            }
        }

        // Compatible number dates
        getCompatibleNumbers(lifePath).forEach { num ->
            if (num in 1..31) dates.add(num)
            for (i in 1..31) {
                if (NumberReducer.reduceToSingleDigit(i) == num) {
                    dates.add(i)
                }
            }
        }

        return dates.sorted().take(10)
    }

    private fun calculateLuckyMonths(lifePath: Int, birthDate: LocalDate): List<LuckyMonth> {
        val months = mutableListOf<LuckyMonth>()
        val birthMonth = birthDate.monthValue

        // Birth month is special
        months.add(LuckyMonth(
            month = birthMonth,
            reason = "Your birth month carries your personal energy amplified",
            favorableFor = listOf("Personal projects", "Self-improvement", "New beginnings")
        ))

        // Months based on life path
        val favorableMonths = when (lifePath) {
            1 -> listOf(1, 10)
            2 -> listOf(2, 11)
            3 -> listOf(3, 12)
            4 -> listOf(4)
            5 -> listOf(5)
            6 -> listOf(6)
            7 -> listOf(7)
            8 -> listOf(8)
            9 -> listOf(9)
            else -> listOf(birthMonth)
        }

        favorableMonths.forEach { month ->
            if (month != birthMonth && month in 1..12) {
                months.add(LuckyMonth(
                    month = month,
                    reason = "This month resonates with your Life Path energy",
                    favorableFor = listOf("Major decisions", "Important events", "Life changes")
                ))
            }
        }

        return months.distinctBy { it.month }
    }

    private fun getLuckyColors(lifePath: Int): List<LuckyColor> {
        return when (lifePath) {
            1 -> listOf(
                LuckyColor("Red", "Primary", "Enhances leadership and vitality"),
                LuckyColor("Gold", "Secondary", "Attracts success and recognition"),
                LuckyColor("Yellow", "Accent", "Stimulates mental clarity")
            )
            2 -> listOf(
                LuckyColor("White", "Primary", "Enhances purity and peace"),
                LuckyColor("Cream", "Secondary", "Softens interactions"),
                LuckyColor("Light Green", "Accent", "Promotes harmony")
            )
            3 -> listOf(
                LuckyColor("Yellow", "Primary", "Enhances creativity and joy"),
                LuckyColor("Orange", "Secondary", "Stimulates social energy"),
                LuckyColor("Pink", "Accent", "Attracts love and appreciation")
            )
            4 -> listOf(
                LuckyColor("Green", "Primary", "Promotes stability and growth"),
                LuckyColor("Brown", "Secondary", "Grounds energy"),
                LuckyColor("Blue", "Accent", "Enhances focus")
            )
            5 -> listOf(
                LuckyColor("Turquoise", "Primary", "Enhances communication and freedom"),
                LuckyColor("Light Blue", "Secondary", "Promotes adaptability"),
                LuckyColor("Silver", "Accent", "Attracts change")
            )
            6 -> listOf(
                LuckyColor("Blue", "Primary", "Enhances harmony and responsibility"),
                LuckyColor("Pink", "Secondary", "Attracts love"),
                LuckyColor("Green", "Accent", "Promotes healing")
            )
            7 -> listOf(
                LuckyColor("Purple", "Primary", "Enhances spirituality and intuition"),
                LuckyColor("Violet", "Secondary", "Deepens meditation"),
                LuckyColor("Grey", "Accent", "Supports reflection")
            )
            8 -> listOf(
                LuckyColor("Black", "Primary", "Enhances power and authority"),
                LuckyColor("Dark Blue", "Secondary", "Promotes success"),
                LuckyColor("Brown", "Accent", "Grounds abundance")
            )
            9 -> listOf(
                LuckyColor("Red", "Primary", "Enhances passion and service"),
                LuckyColor("Gold", "Secondary", "Attracts universal love"),
                LuckyColor("Crimson", "Accent", "Deepens compassion")
            )
            11 -> listOf(
                LuckyColor("White", "Primary", "Enhances spiritual clarity"),
                LuckyColor("Silver", "Secondary", "Amplifies intuition"),
                LuckyColor("Light Blue", "Accent", "Promotes vision")
            )
            22 -> listOf(
                LuckyColor("Coral", "Primary", "Balances vision with action"),
                LuckyColor("White", "Secondary", "Clarifies purpose"),
                LuckyColor("Gold", "Accent", "Attracts manifestation")
            )
            33 -> listOf(
                LuckyColor("Turquoise", "Primary", "Enhances healing communication"),
                LuckyColor("Green", "Secondary", "Promotes growth"),
                LuckyColor("Pink", "Accent", "Amplifies love")
            )
            else -> listOf(
                LuckyColor("Blue", "Primary", "Universal harmony"),
                LuckyColor("Green", "Secondary", "Balance"),
                LuckyColor("White", "Accent", "Clarity")
            )
        }
    }

    private fun getLuckyGemstones(lifePath: Int): List<LuckyGemstone> {
        return when (lifePath) {
            1 -> listOf(
                LuckyGemstone("Ruby", "Power and leadership", "Wear on right hand"),
                LuckyGemstone("Garnet", "Vitality and courage", "Carry in pocket"),
                LuckyGemstone("Tiger's Eye", "Confidence", "Wear as pendant")
            )
            2 -> listOf(
                LuckyGemstone("Pearl", "Intuition and peace", "Wear as earrings"),
                LuckyGemstone("Moonstone", "Emotional balance", "Wear during full moon"),
                LuckyGemstone("Rose Quartz", "Love and harmony", "Keep near bed")
            )
            3 -> listOf(
                LuckyGemstone("Yellow Topaz", "Creativity and joy", "Wear as ring"),
                LuckyGemstone("Citrine", "Success and optimism", "Keep in workspace"),
                LuckyGemstone("Amber", "Communication", "Wear near throat")
            )
            4 -> listOf(
                LuckyGemstone("Emerald", "Stability and prosperity", "Wear on little finger"),
                LuckyGemstone("Green Aventurine", "Luck in endeavors", "Carry when working"),
                LuckyGemstone("Jade", "Harmony and balance", "Keep in home")
            )
            5 -> listOf(
                LuckyGemstone("Turquoise", "Protection during travel", "Wear when traveling"),
                LuckyGemstone("Aquamarine", "Clarity and courage", "Wear as pendant"),
                LuckyGemstone("Blue Topaz", "Communication", "Wear during important talks")
            )
            6 -> listOf(
                LuckyGemstone("Sapphire", "Love and loyalty", "Wear on left hand"),
                LuckyGemstone("Lapis Lazuli", "Truth and harmony", "Meditate with it"),
                LuckyGemstone("Pink Tourmaline", "Compassion", "Wear near heart")
            )
            7 -> listOf(
                LuckyGemstone("Amethyst", "Spiritual awareness", "Place near bed"),
                LuckyGemstone("Clear Quartz", "Clarity and amplification", "Meditate with it"),
                LuckyGemstone("Labradorite", "Intuition", "Wear as pendant")
            )
            8 -> listOf(
                LuckyGemstone("Black Onyx", "Power and protection", "Wear on right hand"),
                LuckyGemstone("Obsidian", "Grounding", "Keep in office"),
                LuckyGemstone("Diamond", "Success and abundance", "Wear for important meetings")
            )
            9 -> listOf(
                LuckyGemstone("Bloodstone", "Courage and healing", "Carry when helping others"),
                LuckyGemstone("Red Jasper", "Grounding compassion", "Wear as bracelet"),
                LuckyGemstone("Opal", "Universal love", "Wear for spiritual work")
            )
            else -> listOf(
                LuckyGemstone("Clear Quartz", "Universal amplifier", "Versatile use"),
                LuckyGemstone("Amethyst", "Spiritual connection", "Meditation aid")
            )
        }
    }

    private fun getLuckyDirections(lifePath: Int): List<LuckyDirection> {
        return when (lifePath) {
            1, 9 -> listOf(
                LuckyDirection("East", "New beginnings and fresh energy"),
                LuckyDirection("South", "Success and recognition")
            )
            2, 6 -> listOf(
                LuckyDirection("Southwest", "Relationships and partnerships"),
                LuckyDirection("West", "Reflection and peace")
            )
            3 -> listOf(
                LuckyDirection("Southeast", "Creativity and expansion"),
                LuckyDirection("East", "New creative ventures")
            )
            4, 8 -> listOf(
                LuckyDirection("Northeast", "Knowledge and stability"),
                LuckyDirection("Northwest", "Authority and achievement")
            )
            5 -> listOf(
                LuckyDirection("Center", "Balance amid change"),
                LuckyDirection("All directions", "Adaptability to any situation")
            )
            7 -> listOf(
                LuckyDirection("Northwest", "Spiritual mentorship"),
                LuckyDirection("North", "Career and life path")
            )
            else -> listOf(
                LuckyDirection("East", "New beginnings"),
                LuckyDirection("North", "Career advancement")
            )
        }
    }

    private fun getFavorableActivities(lifePath: Int): List<FavorableActivity> {
        return when (lifePath) {
            1 -> listOf(
                FavorableActivity("Starting new projects", "Your pioneering energy supports initiation"),
                FavorableActivity("Leadership roles", "Natural authority shines"),
                FavorableActivity("Independent work", "Solo efforts succeed")
            )
            2 -> listOf(
                FavorableActivity("Partnership activities", "Collaboration brings success"),
                FavorableActivity("Mediation", "Your diplomatic skills excel"),
                FavorableActivity("Artistic pursuits", "Creative sensitivity flows")
            )
            3 -> listOf(
                FavorableActivity("Creative expression", "Artistic talents flourish"),
                FavorableActivity("Social events", "Charisma attracts opportunities"),
                FavorableActivity("Communication projects", "Words have power")
            )
            4 -> listOf(
                FavorableActivity("Building and construction", "Practical skills shine"),
                FavorableActivity("Organization tasks", "Systems come together"),
                FavorableActivity("Long-term planning", "Patience pays off")
            )
            5 -> listOf(
                FavorableActivity("Travel", "Adventures bring growth"),
                FavorableActivity("Sales and marketing", "Persuasion succeeds"),
                FavorableActivity("Learning new skills", "Quick absorption")
            )
            6 -> listOf(
                FavorableActivity("Family activities", "Bonds strengthen"),
                FavorableActivity("Healing work", "Natural abilities flow"),
                FavorableActivity("Home improvement", "Creating beauty")
            )
            7 -> listOf(
                FavorableActivity("Research and study", "Deep insights emerge"),
                FavorableActivity("Spiritual practices", "Connection deepens"),
                FavorableActivity("Writing", "Wisdom flows into words")
            )
            8 -> listOf(
                FavorableActivity("Business deals", "Material success beckons"),
                FavorableActivity("Financial planning", "Abundance grows"),
                FavorableActivity("Career advancement", "Authority recognized")
            )
            9 -> listOf(
                FavorableActivity("Humanitarian work", "Service brings fulfillment"),
                FavorableActivity("Teaching", "Wisdom shared multiplies"),
                FavorableActivity("Artistic creation", "Beauty manifests")
            )
            else -> listOf(
                FavorableActivity("Personal development", "Growth in all areas"),
                FavorableActivity("Balanced activities", "Harmony in action")
            )
        }
    }

    private fun getPlanetaryInfluence(lifePath: Int): PlanetaryInfluence {
        return when (lifePath) {
            1 -> PlanetaryInfluence("Sun", "Leadership, vitality, and self-expression", "Sunday is your power day")
            2 -> PlanetaryInfluence("Moon", "Intuition, emotions, and receptivity", "Monday enhances sensitivity")
            3 -> PlanetaryInfluence("Jupiter", "Expansion, optimism, and creativity", "Thursday brings opportunities")
            4 -> PlanetaryInfluence("Saturn", "Discipline, structure, and achievement", "Saturday favors hard work")
            5 -> PlanetaryInfluence("Mercury", "Communication, adaptability, and intellect", "Wednesday enhances versatility")
            6 -> PlanetaryInfluence("Venus", "Love, beauty, and harmony", "Friday brings romance and art")
            7 -> PlanetaryInfluence("Neptune", "Spirituality, intuition, and dreams", "Monday deepens insight")
            8 -> PlanetaryInfluence("Saturn", "Authority, karma, and material mastery", "Saturday brings achievement")
            9 -> PlanetaryInfluence("Mars", "Action, passion, and humanitarian drive", "Tuesday energizes service")
            11 -> PlanetaryInfluence("Neptune/Uranus", "Spiritual vision and illumination", "Monday and Sunday are powerful")
            22 -> PlanetaryInfluence("Saturn/Uranus", "Master building with innovation", "Saturday for manifestation")
            33 -> PlanetaryInfluence("Venus/Neptune", "Divine love and healing", "Friday and Monday are blessed")
            else -> PlanetaryInfluence("Sun", "Core vitality and purpose", "Sunday renews energy")
        }
    }

    private fun getElementalAffinity(lifePath: Int): ElementalAffinity {
        return when (lifePath) {
            1, 5, 9 -> ElementalAffinity("Fire", "Passion, action, and transformation",
                listOf("Warm climates", "Sunlight exposure", "Active pursuits"))
            2, 6 -> ElementalAffinity("Water", "Emotion, intuition, and flow",
                listOf("Near water", "Humid environments", "Fluid activities"))
            3, 7 -> ElementalAffinity("Air", "Intellect, communication, and spirit",
                listOf("Open spaces", "Fresh air", "Mental activities"))
            4, 8 -> ElementalAffinity("Earth", "Stability, material, and grounding",
                listOf("Nature walks", "Gardening", "Physical activities"))
            11 -> ElementalAffinity("Air/Spirit", "Higher mind and vision",
                listOf("Meditation spaces", "High places", "Spiritual practices"))
            22 -> ElementalAffinity("Earth/Spirit", "Manifested vision",
                listOf("Sacred sites", "Building projects", "Group work"))
            33 -> ElementalAffinity("Water/Spirit", "Divine love flow",
                listOf("Healing spaces", "Near water", "Service activities"))
            else -> ElementalAffinity("Ether", "Balance of all elements",
                listOf("Varied environments", "Balance activities"))
        }
    }

    private fun calculateDayFavorability(
        personalYear: Int,
        personalMonth: Int,
        personalDay: Int,
        universalDay: Int,
        dayOfWeek: DayOfWeek
    ): DayFavorabilityResult {
        var score = 50 // Base score

        // Personal day influence
        score += when (personalDay) {
            1 -> 15 // Great for new beginnings
            3, 5 -> 10 // Creative and dynamic
            8 -> 10 // Achievement oriented
            4, 7 -> 5 // Steady progress
            2, 6 -> 5 // Relationship focused
            9 -> 0 // Endings - neutral
            else -> 0
        }

        // Harmony between personal and universal
        if (personalDay == universalDay) score += 10
        if (getNumberHarmony(personalDay, universalDay)) score += 5

        // Day of week influence
        score += when (dayOfWeek) {
            DayOfWeek.SUNDAY -> 5 // New beginnings
            DayOfWeek.MONDAY -> 3 // Intuition
            DayOfWeek.WEDNESDAY -> 5 // Communication
            DayOfWeek.THURSDAY -> 7 // Expansion
            DayOfWeek.FRIDAY -> 5 // Social/Creative
            else -> 0
        }

        val level = when {
            score >= 75 -> FavorabilityLevel.EXCELLENT
            score >= 60 -> FavorabilityLevel.GOOD
            score >= 45 -> FavorabilityLevel.MODERATE
            else -> FavorabilityLevel.CHALLENGING
        }

        return DayFavorabilityResult(
            score = score.coerceIn(0, 100),
            level = level,
            bestFor = getBestActivitiesForPersonalDay(personalDay),
            caution = getCautionForPersonalDay(personalDay)
        )
    }

    private fun getNumberHarmony(num1: Int, num2: Int): Boolean {
        val harmonious = setOf(
            Pair(1, 3), Pair(1, 5), Pair(1, 9),
            Pair(2, 4), Pair(2, 6), Pair(2, 8),
            Pair(3, 5), Pair(3, 6), Pair(3, 9),
            Pair(4, 6), Pair(4, 8),
            Pair(5, 7), Pair(5, 9),
            Pair(6, 9)
        )
        val pair = if (num1 <= num2) Pair(num1, num2) else Pair(num2, num1)
        return harmonious.contains(pair)
    }

    private fun getBestActivitiesForPersonalDay(day: Int): List<String> {
        return when (day) {
            1 -> listOf("Starting projects", "Making decisions", "Leadership")
            2 -> listOf("Partnerships", "Patience", "Detail work")
            3 -> listOf("Creative work", "Socializing", "Communication")
            4 -> listOf("Organization", "Building", "Practical matters")
            5 -> listOf("Travel", "Changes", "Adventure")
            6 -> listOf("Family", "Home", "Healing")
            7 -> listOf("Reflection", "Study", "Spiritual practice")
            8 -> listOf("Business", "Finance", "Achievement")
            9 -> listOf("Completion", "Service", "Release")
            else -> listOf("General activities")
        }
    }

    private fun getCautionForPersonalDay(day: Int): String? {
        return when (day) {
            4 -> "Avoid overworking"
            5 -> "Avoid impulsive decisions"
            7 -> "Avoid crowded situations"
            8 -> "Avoid ego conflicts"
            9 -> "Avoid starting new long-term commitments"
            else -> null
        }
    }

    private fun getActivityFavorableNumbers(activity: ActivityType): List<Int> {
        return when (activity) {
            ActivityType.NEW_BUSINESS -> listOf(1, 8)
            ActivityType.ROMANCE -> listOf(2, 6)
            ActivityType.CREATIVE_WORK -> listOf(3)
            ActivityType.MAJOR_PURCHASE -> listOf(4, 8)
            ActivityType.TRAVEL -> listOf(5)
            ActivityType.FAMILY_EVENT -> listOf(6)
            ActivityType.STUDY -> listOf(7)
            ActivityType.CAREER_MOVE -> listOf(1, 8)
            ActivityType.SPIRITUAL_PRACTICE -> listOf(7, 9)
            ActivityType.HEALTH_FOCUS -> listOf(6)
        }
    }

    private fun getActivityUnfavorableNumbers(activity: ActivityType): List<Int> {
        return when (activity) {
            ActivityType.NEW_BUSINESS -> listOf(9) // Endings
            ActivityType.ROMANCE -> listOf(1, 7) // Independence, isolation
            ActivityType.MAJOR_PURCHASE -> listOf(5) // Change, instability
            ActivityType.TRAVEL -> listOf(4) // Restriction
            else -> emptyList()
        }
    }

    private fun getActivityAdvice(activity: ActivityType, lifePath: Int): String {
        return when (activity) {
            ActivityType.NEW_BUSINESS -> "Choose days when your personal number aligns with 1 or 8 energy for best business launches."
            ActivityType.ROMANCE -> "Days with 2 or 6 energy enhance romantic connections. Avoid 7 days for intimate matters."
            ActivityType.CREATIVE_WORK -> "3 energy days boost creativity. Your Life Path $lifePath adds specific creative flavor."
            ActivityType.MAJOR_PURCHASE -> "4 and 8 days support major purchases. Avoid 5 days when instability could affect decisions."
            ActivityType.TRAVEL -> "5 energy days are ideal for travel. Your adventure will flow more smoothly."
            ActivityType.FAMILY_EVENT -> "6 energy days strengthen family bonds and support gatherings."
            ActivityType.STUDY -> "7 energy days enhance learning and deep study."
            ActivityType.CAREER_MOVE -> "1 and 8 days support career advancement and new positions."
            ActivityType.SPIRITUAL_PRACTICE -> "7 and 9 days deepen spiritual connection and practice."
            ActivityType.HEALTH_FOCUS -> "6 days support healing and health improvements."
        }
    }

    private fun getActivityBestHours(activity: ActivityType): List<String> {
        return when (activity) {
            ActivityType.NEW_BUSINESS -> listOf("9 AM - 12 PM (Sun energy)", "2 PM - 4 PM (Saturn focus)")
            ActivityType.ROMANCE -> listOf("6 PM - 9 PM (Venus hours)", "Evening moon hours")
            ActivityType.CREATIVE_WORK -> listOf("Morning hours", "Late night (when world is quiet)")
            ActivityType.MAJOR_PURCHASE -> listOf("10 AM - 2 PM (practical hours)")
            ActivityType.TRAVEL -> listOf("Early morning (fresh energy)", "Afternoon departures")
            ActivityType.FAMILY_EVENT -> listOf("Afternoon", "Early evening")
            ActivityType.STUDY -> listOf("Early morning", "Late evening (7-9 PM)")
            ActivityType.CAREER_MOVE -> listOf("9 AM - 11 AM", "2 PM - 4 PM")
            ActivityType.SPIRITUAL_PRACTICE -> listOf("Dawn", "Dusk", "Midnight")
            ActivityType.HEALTH_FOCUS -> listOf("Morning for exercise", "Afternoon for treatments")
        }
    }

    private fun getPersonalMonthTheme(personalMonth: Int): String {
        return when (personalMonth) {
            1 -> "New beginnings and initiative"
            2 -> "Patience and partnerships"
            3 -> "Creativity and self-expression"
            4 -> "Building and organization"
            5 -> "Change and adventure"
            6 -> "Home, family, and responsibility"
            7 -> "Reflection and inner development"
            8 -> "Achievement and material matters"
            9 -> "Completion and release"
            else -> "Personal growth and development"
        }
    }

    private fun getPersonalMonthAdvice(personalMonth: Int): String {
        return when (personalMonth) {
            1 -> "Take initiative on important matters. Plant seeds for the year ahead."
            2 -> "Be patient. Focus on relationships and cooperative efforts."
            3 -> "Express yourself creatively. Socialize and enjoy life."
            4 -> "Work hard on practical matters. Build solid foundations."
            5 -> "Embrace change. Travel if possible. Stay flexible."
            6 -> "Focus on family and home. Take on responsibilities with love."
            7 -> "Take time for reflection and study. Trust your intuition."
            8 -> "Focus on career and finances. Achievement is favored."
            9 -> "Complete unfinished business. Release what no longer serves you."
            else -> "Stay open to the month's unique opportunities."
        }
    }
}

// Data Classes for Lucky Days

data class LuckyProfile(
    val luckyNumbers: List<LuckyNumber>,
    val luckyDays: List<LuckyDayOfWeek>,
    val luckyDates: List<Int>,
    val luckyMonths: List<LuckyMonth>,
    val luckyColors: List<LuckyColor>,
    val luckyGemstones: List<LuckyGemstone>,
    val luckyDirections: List<LuckyDirection>,
    val favorableActivities: List<FavorableActivity>,
    val planetaryInfluence: PlanetaryInfluence,
    val elementalAffinity: ElementalAffinity
)

data class LuckyNumber(
    val number: Int,
    val significance: String,
    val reason: String
)

data class LuckyDayOfWeek(
    val day: DayOfWeek,
    val reason: String,
    val bestActivities: List<String>
)

data class LuckyMonth(
    val month: Int,
    val reason: String,
    val favorableFor: List<String>
)

data class LuckyColor(
    val color: String,
    val significance: String,
    val usage: String
)

data class LuckyGemstone(
    val name: String,
    val properties: String,
    val howToUse: String
)

data class LuckyDirection(
    val direction: String,
    val meaning: String
)

data class FavorableActivity(
    val activity: String,
    val reason: String
)

data class PlanetaryInfluence(
    val planet: String,
    val influence: String,
    val advice: String
)

data class ElementalAffinity(
    val element: String,
    val meaning: String,
    val recommendations: List<String>
)

data class MonthlyFavorableDays(
    val yearMonth: YearMonth,
    val personalMonth: Int,
    val allDays: List<FavorableDay>,
    val excellentDays: List<FavorableDay>,
    val goodDays: List<FavorableDay>,
    val challengingDays: List<FavorableDay>,
    val monthTheme: String,
    val monthAdvice: String
)

data class FavorableDay(
    val date: LocalDate,
    val personalDay: Int,
    val universalDay: Int,
    val favorabilityScore: Int,
    val favorabilityLevel: FavorabilityLevel,
    val bestFor: List<String>,
    val caution: String?
)

data class DayFavorabilityResult(
    val score: Int,
    val level: FavorabilityLevel,
    val bestFor: List<String>,
    val caution: String?
)

enum class FavorabilityLevel {
    EXCELLENT,
    GOOD,
    MODERATE,
    CHALLENGING
}

enum class ActivityType {
    NEW_BUSINESS,
    ROMANCE,
    CREATIVE_WORK,
    MAJOR_PURCHASE,
    TRAVEL,
    FAMILY_EVENT,
    STUDY,
    CAREER_MOVE,
    SPIRITUAL_PRACTICE,
    HEALTH_FOCUS
}

data class ActivityTimingRecommendation(
    val activity: ActivityType,
    val bestDays: List<FavorableDay>,
    val acceptableDays: List<FavorableDay>,
    val daysToAvoid: List<FavorableDay>,
    val generalAdvice: String,
    val bestHours: List<String>
)
