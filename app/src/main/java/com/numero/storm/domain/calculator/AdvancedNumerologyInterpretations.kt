package com.numero.storm.domain.calculator

/**
 * Advanced numerology interpretations providing deep, comprehensive analysis.
 * This system provides detailed explanations for all numerology concepts including:
 * - Master Numbers with their full spiritual significance
 * - Karmic Debt Numbers with transformation guidance
 * - Number combinations and their interactions
 * - Life cycle interpretations
 * - Practical life guidance
 */
object AdvancedNumerologyInterpretations {

    /**
     * Get comprehensive Master Number interpretation.
     * Master Numbers (11, 22, 33) carry higher vibrations and spiritual responsibilities.
     */
    fun getMasterNumberInterpretation(number: Int): MasterNumberInterpretation? {
        return when (number) {
            11 -> MasterNumberInterpretation(
                number = 11,
                archetype = "The Intuitive Illuminator",
                coreEssence = "Master Number 11 is the most intuitive of all numbers. It represents illumination, spiritual insight, and the channel between the conscious and unconscious mind. As a bearer of this number, you are a spiritual messenger meant to inspire and enlighten others.",
                spiritualSignificance = "The 11 is considered the 'psychic's number' and represents the gateway to the subconscious. It carries the vibration of spiritual awakening and enlightenment. Those with this number often have prophetic abilities, heightened intuition, and the power to inspire masses through their vision.",
                higherPurpose = "Your higher purpose is to be a channel for spiritual information and inspiration. You are here to raise the consciousness of humanity by sharing insights that come from higher realms. Your sensitivity allows you to perceive what others cannot see.",
                challenges = listOf(
                    "Managing nervous energy and anxiety that comes from heightened sensitivity",
                    "Learning to trust your intuition even when it defies logic",
                    "Avoiding the tendency to escape reality through fantasy or addiction",
                    "Dealing with feeling misunderstood by those operating at lower vibrations",
                    "Balancing spiritual awareness with practical everyday responsibilities",
                    "Overcoming self-doubt about your visions and insights"
                ),
                gifts = listOf(
                    "Exceptional intuitive and psychic abilities",
                    "Ability to inspire and uplift others with your presence alone",
                    "Deep connection to spiritual truths and higher wisdom",
                    "Natural healing abilities through energy and presence",
                    "Visionary thinking that can see possibilities others miss",
                    "Ability to channel creative inspiration from higher sources"
                ),
                lifeLesson = "Your primary life lesson is learning to trust and develop your intuitive gifts while staying grounded in physical reality. You must learn that your sensitivity is not a weakness but your greatest strength. The challenge is to share your visions without retreating from the world.",
                relationships = "In relationships, you need a partner who understands and respects your spiritual nature. You may find that ordinary relationships feel unsatisfying because you crave deep soul connections. The ideal partner is someone who can provide grounding energy while appreciating your visionary qualities. Avoid partners who dismiss your intuitive perceptions or try to make you 'more practical.'",
                career = "Careers that allow you to use your intuitive gifts are ideal. You excel in counseling, healing arts, spiritual teaching, creative arts, and any field where you can inspire others. Traditional corporate environments may feel stifling unless you can find meaning in your work. Many 11s become artists, musicians, psychics, therapists, or spiritual teachers.",
                health = "Your sensitive nervous system requires careful attention. Stress manifests quickly in your body, often through anxiety, sleep disturbances, or nervous disorders. Regular meditation, time in nature, and limiting exposure to negative environments is essential. You may be sensitive to foods, chemicals, and electromagnetic frequencies.",
                affirmation = "I trust my inner voice and share my light with the world. My sensitivity is my greatest gift.",
                shadowSide = "The shadow side of 11 can manifest as nervous disorders, depression, addiction, or retreating into fantasy worlds. When not living up to your potential, you may become overly critical, self-doubting, or prone to nervous breakdowns. The key is channeling your high-frequency energy constructively.",
                practicalAdvice = listOf(
                    "Develop a daily meditation practice to manage your sensitive nervous system",
                    "Keep a dream journal - your dreams often contain important messages",
                    "Trust your first impressions about people and situations",
                    "Create boundaries to protect yourself from energy vampires",
                    "Find creative outlets to channel your visionary energy",
                    "Seek environments and people who support your spiritual growth"
                ),
                famousExamples = "Many visionary leaders, artists, and spiritual teachers carry the 11 vibration."
            )
            22 -> MasterNumberInterpretation(
                number = 22,
                archetype = "The Master Builder",
                coreEssence = "Master Number 22 is the most powerful number in numerology - the 'Master Builder' who can turn dreams into reality. It combines the intuition of 11 with the practical ability of 4, creating the potential to manifest extraordinary visions on the material plane.",
                spiritualSignificance = "The 22 represents the ability to bring spiritual concepts into physical form. While 11 receives the vision, 22 builds the structure to manifest it. This number carries the weight of enormous potential - the ability to create lasting institutions, systems, and structures that benefit humanity for generations.",
                higherPurpose = "Your higher purpose is to be an architect of new paradigms. You are here to build something of lasting significance that serves humanity on a large scale. Whether it's an organization, a system, a movement, or a physical structure, your creations are meant to stand the test of time.",
                challenges = listOf(
                    "The weight of enormous potential can be paralyzing",
                    "Tendency toward nervous exhaustion from carrying big visions",
                    "Risk of becoming a workaholic in pursuit of grand goals",
                    "Difficulty relating to people who think on a smaller scale",
                    "Impatience with the slow pace of manifesting large visions",
                    "Perfectionism that can delay or prevent completion of projects"
                ),
                gifts = listOf(
                    "Ability to manifest grand visions into physical reality",
                    "Exceptional organizational and leadership abilities",
                    "Practical idealism - combining dreams with action",
                    "Discipline and focus to see large projects through to completion",
                    "Ability to inspire and coordinate large groups of people",
                    "Natural understanding of how systems and structures work"
                ),
                lifeLesson = "Your primary lesson is learning to handle the enormous potential you carry without being crushed by it. Many 22s never fully step into their power because the weight feels too heavy. You must learn that starting small is okay - even master builders lay one brick at a time.",
                relationships = "Relationships can be challenging because your mind is often occupied with large-scale projects and visions. You need a partner who can understand your mission or at least support it without feeling neglected. The best partners for 22s are those who have their own sense of purpose and don't require constant attention.",
                career = "You are suited for positions of significant responsibility - building companies, leading organizations, creating institutions, or developing systems that serve many people. Politics, large-scale business ventures, architecture, engineering, and international development are natural fits. Whatever you do, it should allow you to create lasting structures.",
                health = "The pressure of carrying such potential can manifest as stress-related conditions, particularly affecting the nervous system and heart. You need to build regular rest and recovery into your schedule. Physical exercise helps ground your high-voltage energy. Avoid burning out by recognizing that Rome wasn't built in a day.",
                affirmation = "I build bridges between dreams and reality. My vision serves humanity.",
                shadowSide = "The shadow 22 can become overwhelmed by potential and accomplish nothing, or conversely, become ruthless in pursuit of goals regardless of who gets hurt. Some 22s avoid their potential entirely, living as frustrated 4s. Others become megalomaniacs drunk on their own power.",
                practicalAdvice = listOf(
                    "Break your grand vision into manageable steps",
                    "Build a team - you cannot do it all alone",
                    "Take regular breaks to prevent burnout",
                    "Don't let perfectionism prevent progress",
                    "Remember that even master builders start with a foundation",
                    "Use your 4 energy for discipline and your 11 energy for vision"
                ),
                famousExamples = "Many of history's great builders, from institution creators to architectural visionaries, carried the 22 vibration."
            )
            33 -> MasterNumberInterpretation(
                number = 33,
                archetype = "The Master Teacher of Compassion",
                coreEssence = "Master Number 33 is the rarest and most spiritually evolved of the Master Numbers. Called the 'Master Teacher,' it represents the highest form of love in action - selfless service that uplifts humanity. The 33 embodies nurturing elevated to its divine potential.",
                spiritualSignificance = "The 33 combines the vision of 11 with the building capacity of 22, then adds the nurturing energy of 6 taken to its highest expression. This creates a number of profound healing and teaching ability. Those who truly embody 33 energy become spiritual guides and healers of extraordinary power.",
                higherPurpose = "Your higher purpose is to be a vessel of unconditional love and healing for humanity. You are here to teach by example - to demonstrate what is possible when humans operate from pure love. Your presence alone can be healing to those around you.",
                challenges = listOf(
                    "The weight of responsibility for others' wellbeing can be crushing",
                    "Risk of martyrdom and self-sacrifice to the point of self-destruction",
                    "Difficulty setting boundaries because you feel everyone's pain",
                    "Attracting those who want to take advantage of your giving nature",
                    "Struggling to receive when giving is so natural",
                    "Physical and emotional exhaustion from carrying others' burdens"
                ),
                gifts = listOf(
                    "Profound healing abilities - physical, emotional, and spiritual",
                    "Ability to see the divine potential in everyone",
                    "Teaching abilities that transform lives",
                    "Unconditional love that heals deep wounds",
                    "Ability to inspire mass movements toward compassion",
                    "Natural counseling and guidance abilities"
                ),
                lifeLesson = "Your primary lesson is learning that self-care is not selfish. To be the healer you are meant to be, you must maintain your own wellbeing. You cannot pour from an empty cup. Learning to receive is as important as learning to give.",
                relationships = "Your capacity for love is so vast that containing it within a single relationship can be challenging. You love everyone, which can make partners feel like they're not special. The ideal partner understands your universal love nature and feels secure enough not to compete with humanity for your attention.",
                career = "You are suited for any career that involves healing, teaching, or serving others on a large scale. Healthcare, counseling, spiritual leadership, humanitarian work, and teaching are natural fits. Many 33s become the founders of healing or educational institutions that serve many.",
                health = "Your tendency to absorb others' pain can manifest in your own body as chronic conditions. You must learn energetic protection and clearing practices. Regular solitude for regeneration is essential. Heart conditions and immune disorders can result from giving too much without replenishing.",
                affirmation = "I am a channel of divine love. In nurturing myself, I better nurture the world.",
                shadowSide = "The shadow 33 becomes a martyr, sacrificing self to the point of destruction while paradoxically sometimes enabling others' dysfunction. Some shadow 33s become controlling under the guise of 'knowing what's best' for others. Others become bitter when their sacrifices go unappreciated.",
                practicalAdvice = listOf(
                    "Schedule regular self-care as non-negotiable",
                    "Learn to say no without guilt",
                    "Practice energetic boundaries and clearing",
                    "Remember that enabling is not helping",
                    "Find others who can support your mission",
                    "Accept help and support from others graciously"
                ),
                famousExamples = "The greatest spiritual teachers, healers, and humanitarians in history often carry the 33 vibration."
            )
            else -> null
        }
    }

    /**
     * Get comprehensive Karmic Debt interpretation.
     * Karmic Debt Numbers (13, 14, 16, 19) indicate lessons carried from past lives.
     */
    fun getKarmicDebtInterpretation(number: Int): KarmicDebtInterpretation? {
        return when (number) {
            13 -> KarmicDebtInterpretation(
                number = 13,
                theme = "The Debt of Laziness and Shortcuts",
                coreLesson = "Karmic Debt 13 indicates that in past lives, you avoided hard work, took shortcuts, or built on unstable foundations. This lifetime requires learning the value of honest effort and persistence. Success must be earned through dedicated work, not luck or manipulation.",
                pastLifePattern = "In previous incarnations, you may have been lazy, relied on others to do your work, or used unethical shortcuts to achieve goals. You may have built things that collapsed because they lacked proper foundations. The debt accumulated from avoiding necessary work now must be repaid.",
                currentLifeManifestations = listOf(
                    "Situations repeatedly requiring you to start over",
                    "Frustration when shortcuts don't work",
                    "Learning the hard way that there's no substitute for effort",
                    "Projects that collapse when foundations are weak",
                    "Being forced into positions requiring hard work"
                ),
                transformationPath = "The path to clearing this debt is embracing discipline and honest effort. Every task completed properly, every commitment honored, every time you choose the harder right over the easier wrong - you pay down this debt. The transformation comes when you discover the deep satisfaction of earned achievement.",
                practicalGuidance = listOf(
                    "Avoid all shortcuts, even when they seem harmless",
                    "Complete every task you start, no matter how small",
                    "Build strong foundations before adding structure",
                    "Embrace delay as part of proper process",
                    "Value consistency over intensity",
                    "Honor your commitments absolutely"
                ),
                affirmation = "Through honest effort, I build lasting success. I embrace the dignity of dedicated work.",
                blessingInDisguise = "Once mastered, Karmic Debt 13 transforms into exceptional competence and the deep satisfaction that only comes from truly earned achievement. You become someone others can absolutely rely upon.",
                warningSign = "If you find yourself repeatedly taking shortcuts or having projects collapse, this is your karmic debt activating. The universe will continue creating these lessons until you learn.",
                relationships = "In relationships, this debt manifests as needing to put in genuine effort rather than expecting things to work automatically. You cannot coast on charm or past glory. Your relationships require consistent, dedicated attention to thrive."
            )
            14 -> KarmicDebtInterpretation(
                number = 14,
                theme = "The Debt of Freedom Abuse and Excess",
                coreLesson = "Karmic Debt 14 indicates past life abuse of freedom - indulgence, addiction, or using freedom to harm others. This lifetime requires learning that true freedom comes through self-discipline, not abandon. Moderation and commitment are your teachers.",
                pastLifePattern = "In previous incarnations, you may have been addicted, indulgent, or used personal freedom at others' expense. You may have abandoned responsibilities for pleasure or been unable to commit. The debt from these excesses now requires balancing.",
                currentLifeManifestations = listOf(
                    "Struggles with addiction or compulsive behaviors",
                    "Difficulty with commitment in relationships or work",
                    "Restlessness and inability to settle",
                    "Excessive need for stimulation and variety",
                    "Consequences from impulsive decisions",
                    "Freedom feeling like a trap rather than liberation"
                ),
                transformationPath = "The path to clearing this debt is developing healthy self-discipline while honoring your need for freedom. You must learn that commitment and freedom are not opposites - true freedom comes from mastering your impulses rather than being controlled by them.",
                practicalGuidance = listOf(
                    "Practice moderation in all pleasures",
                    "Make and keep commitments, even small ones",
                    "Find healthy outlets for your need for variety",
                    "Be honest with yourself about addictive patterns",
                    "Create structure that allows for freedom within boundaries",
                    "Take responsibility for consequences of your choices"
                ),
                affirmation = "I find freedom through self-mastery. My choices serve my highest good.",
                blessingInDisguise = "Once mastered, Karmic Debt 14 becomes exceptional adaptability combined with reliable commitment. You become someone who can handle change gracefully while remaining stable.",
                warningSign = "Watch for patterns of addiction, compulsion, or repeated inability to commit. These are signals that your karmic debt requires attention.",
                relationships = "This debt often manifests in relationships as fear of commitment, serial dating, or abandoning partners when things get difficult. Healing requires learning that deep intimacy is a form of freedom, not imprisonment."
            )
            16 -> KarmicDebtInterpretation(
                number = 16,
                theme = "The Debt of Ego and Relationship Destruction",
                coreLesson = "Karmic Debt 16 is often considered the most difficult karmic debt. It indicates past lives where ego, vanity, or illicit relationships caused destruction. This lifetime involves ego death and rebuilding from a place of authentic humility.",
                pastLifePattern = "In previous incarnations, you may have been vain, arrogant, or used love affairs destructively. You may have let pride destroy relationships or hurt others through narcissism. The debt from these ego-driven actions now requires facing the collapse of false identity.",
                currentLifeManifestations = listOf(
                    "Sudden falls from grace or status",
                    "Relationships that end dramatically",
                    "Public humiliation experiences",
                    "Loss of things you identified with",
                    "Repeated destruction of carefully built images",
                    "Being forced to start over after everything collapses"
                ),
                transformationPath = "The path through this debt is surrendering ego identification and rebuilding from authentic self. The tower must fall so that a temple can be built. Each humiliation, properly understood, is an invitation to drop false pride and discover who you really are beneath social masks.",
                practicalGuidance = listOf(
                    "Practice genuine humility, not false modesty",
                    "Don't tie your identity to external achievements",
                    "Welcome ego deaths as liberation, not defeat",
                    "Build relationships on authenticity, not image",
                    "Value integrity over appearance",
                    "Find the gift in every fall from grace"
                ),
                affirmation = "I release false pride and embrace my authentic self. Each fall is a doorway to truth.",
                blessingInDisguise = "Once mastered, Karmic Debt 16 produces profound spiritual wisdom and authentic humility. You become someone who cannot be shaken because you are rooted in truth rather than image.",
                warningSign = "If you find yourself experiencing sudden losses, public embarrassments, or relationship collapses - especially when things seemed stable - this is your karmic debt activating.",
                relationships = "This debt often manifests through relationships ending when they threaten your ego or image. True transformation comes when you can be fully seen and loved for who you really are, not who you pretend to be."
            )
            19 -> KarmicDebtInterpretation(
                number = 19,
                theme = "The Debt of Power Abuse and Isolation",
                coreLesson = "Karmic Debt 19 indicates past lives where you abused power, acted selfishly with authority, or hurt others through excessive self-interest. This lifetime requires learning to balance independence with interdependence, and to use personal power in service of others.",
                pastLifePattern = "In previous incarnations, you may have been a tyrant, used power selfishly, or refused to help others while pursuing personal goals. You may have achieved success at others' expense. The debt from this misuse of power must now be balanced.",
                currentLifeManifestations = listOf(
                    "Feeling alone even when surrounded by people",
                    "Having to do everything yourself because help doesn't come",
                    "Power struggles in relationships and work",
                    "Success that feels hollow or isolating",
                    "Others not trusting your leadership",
                    "Being forced into self-reliance through circumstance"
                ),
                transformationPath = "The path to clearing this debt is learning that true power empowers others. You must become self-reliant not out of selfishness but as a foundation for helping others. The transformation comes when you discover that giving your power away to help others actually increases it.",
                practicalGuidance = listOf(
                    "Use your strength to help others become strong",
                    "Practice receiving help graciously",
                    "Lead through service rather than domination",
                    "Build others up rather than competing with them",
                    "Find ways to make your success benefit others",
                    "Transform independence into interdependence"
                ),
                affirmation = "I am self-reliant so I can better serve others. My power uplifts those around me.",
                blessingInDisguise = "Once mastered, Karmic Debt 19 transforms into inspiring leadership that empowers others. You become a role model for healthy independence combined with genuine service.",
                warningSign = "If you find yourself feeling isolated despite success, struggling in power dynamics, or unable to receive help - this is your karmic debt requiring attention.",
                relationships = "This debt manifests in relationships as either dominating or fearing being dominated. Healthy partnership requires learning that interdependence is not weakness. Mutual support creates more power than isolated independence."
            )
            else -> null
        }
    }

    /**
     * Get interpretation for Life Path and Soul Urge combination.
     * This shows how your outer purpose and inner desires interact.
     */
    fun getLifePathSoulUrgeCombination(lifePath: Int, soulUrge: Int): NumberCombinationInterpretation {
        val reducedLifePath = if (lifePath in listOf(11, 22, 33)) lifePath else NumberReducer.reduceToSingleDigit(lifePath)
        val reducedSoulUrge = if (soulUrge in listOf(11, 22, 33)) soulUrge else NumberReducer.reduceToSingleDigit(soulUrge)

        val harmony = getNumberHarmony(reducedLifePath, reducedSoulUrge)

        return NumberCombinationInterpretation(
            number1 = lifePath,
            number1Type = "Life Path",
            number2 = soulUrge,
            number2Type = "Soul Urge",
            harmonyLevel = harmony,
            interpretation = generateCombinationInterpretation(reducedLifePath, reducedSoulUrge, "Life Path", "Soul Urge"),
            challenges = getCombinationChallenges(reducedLifePath, reducedSoulUrge),
            gifts = getCombinationGifts(reducedLifePath, reducedSoulUrge),
            advice = getCombinationAdvice(reducedLifePath, reducedSoulUrge)
        )
    }

    /**
     * Get Personal Year detailed interpretation with monthly breakdown.
     */
    fun getDetailedPersonalYearInterpretation(personalYear: Int): PersonalYearDetailedInterpretation {
        val year = NumberReducer.reduceToSingleDigit(personalYear)
        return when (year) {
            1 -> PersonalYearDetailedInterpretation(
                year = 1,
                theme = "New Beginnings and Independence",
                overview = "Personal Year 1 marks the beginning of a new nine-year cycle. This is a powerful year for planting seeds, starting fresh, and asserting your independence. What you initiate this year sets the foundation for the next eight years. This is your time to be bold, take risks, and step into leadership.",
                keyThemes = listOf(
                    "New beginnings and fresh starts",
                    "Independence and self-reliance",
                    "Leadership and taking charge",
                    "Planting seeds for the future",
                    "Breaking from the past",
                    "Courage and pioneering"
                ),
                monthlyFocus = mapOf(
                    1 to "Double 1 energy - extremely powerful for new starts. Launch major initiatives.",
                    2 to "Balance your new direction with relationships. Seek supportive partnerships.",
                    3 to "Express your new vision creatively. Network and communicate your ideas.",
                    4 to "Build foundations for your new ventures. Put in the groundwork.",
                    5 to "Embrace changes that support your new direction. Stay flexible.",
                    6 to "Focus on home and family aspects of your new path. Nurture what you've started.",
                    7 to "Reflect on your progress. Trust your intuition about next steps.",
                    8 to "Business and financial matters related to new beginnings take center stage.",
                    9 to "Release anything blocking your new path. Prepare for year 2's focus on cooperation.",
                    10 to "Another 1 month - reinforce your new direction with bold action.",
                    11 to "Intuitive insights about your path. Trust visionary ideas.",
                    12 to "Wrap up loose ends while maintaining momentum into the new year."
                ),
                opportunities = listOf(
                    "Starting a new business or career",
                    "Launching creative projects",
                    "Making major life changes",
                    "Developing new skills",
                    "Establishing new relationships",
                    "Reinventing yourself"
                ),
                challenges = listOf(
                    "Impatience when results don't come immediately",
                    "Tendency to be overly aggressive or forceful",
                    "Ignoring others' input in your excitement",
                    "Starting too many things without follow-through",
                    "Fear of standing alone or going first"
                ),
                advice = "Trust your instincts and take initiative. This year rewards boldness and originality. Don't wait for permission or perfect conditions - start now. What you begin this year will unfold over the next nine years, so plant seeds wisely."
            )
            2 -> PersonalYearDetailedInterpretation(
                year = 2,
                theme = "Patience, Partnership, and Detail",
                overview = "Personal Year 2 shifts focus from the bold starts of year 1 to patient cultivation. This is a year for developing relationships, attending to details, and trusting the process. Progress happens quietly through cooperation rather than aggressive action. Patience is essential.",
                keyThemes = listOf(
                    "Patience and waiting",
                    "Partnerships and cooperation",
                    "Attention to details",
                    "Diplomacy and sensitivity",
                    "Behind-the-scenes work",
                    "Intuition and receptivity"
                ),
                monthlyFocus = mapOf(
                    1 to "Balance need for action with year's patient energy. Plant seeds quietly.",
                    2 to "Peak partnership energy. Collaborate closely. Trust intuition.",
                    3 to "Express yourself while maintaining diplomatic sensitivity.",
                    4 to "Focus on practical details. Build systematically.",
                    5 to "Navigate changes with flexibility. Avoid impulsive decisions.",
                    6 to "Nurture relationships and home matters. Harmonize environments.",
                    7 to "Deep reflection and intuitive development. Trust inner guidance.",
                    8 to "Handle financial and business matters with partnership focus.",
                    9 to "Complete partnership matters. Release relationships that no longer serve.",
                    10 to "Take initiative in partnerships. Lead cooperatively.",
                    11 to "Heightened intuition. Pay attention to subtle guidance.",
                    12 to "Prepare for year 3's creative expansion while maintaining partnerships."
                ),
                opportunities = listOf(
                    "Deepening existing relationships",
                    "Learning patience and trust",
                    "Developing intuitive abilities",
                    "Handling delicate negotiations",
                    "Working on detailed projects",
                    "Supporting others' success"
                ),
                challenges = listOf(
                    "Frustration with slow pace",
                    "Over-sensitivity to criticism",
                    "Giving too much in relationships",
                    "Being too passive",
                    "Difficulty making decisions"
                ),
                advice = "Resist the urge to force results. This year's power lies in patience and cooperation. Focus on relationships and details. Trust that what you planted in year 1 is growing even if you can't see it yet."
            )
            3 -> PersonalYearDetailedInterpretation(
                year = 3,
                theme = "Creativity, Expression, and Joy",
                overview = "Personal Year 3 brings expansion, creativity, and social opportunity. After the patient cultivation of year 2, now is the time to express yourself, enjoy life, and let your creativity flourish. Social connections multiply, and joy should be actively cultivated.",
                keyThemes = listOf(
                    "Creative expression",
                    "Social expansion",
                    "Joy and optimism",
                    "Communication",
                    "Artistic endeavors",
                    "Celebration"
                ),
                monthlyFocus = mapOf(
                    1 to "Launch creative projects. Express yourself boldly.",
                    2 to "Balance creative expression with sensitivity to others.",
                    3 to "Triple creative energy. Peak time for artistic expression and socializing.",
                    4 to "Ground your creativity in practical application.",
                    5 to "Embrace variety in your creative expression. Travel if possible.",
                    6 to "Create beauty in home. Express love through creativity.",
                    7 to "Reflect on your creative direction. Develop deeper artistic vision.",
                    8 to "Monetize creativity. Business opportunities from creative work.",
                    9 to "Complete creative projects. Share your gifts broadly.",
                    10 to "New creative initiatives. Fresh artistic directions.",
                    11 to "Visionary creative insights. Trust inspired ideas.",
                    12 to "Celebrate the year's creative achievements. Joy and gratitude."
                ),
                opportunities = listOf(
                    "Launching creative projects",
                    "Expanding social circle",
                    "Public speaking or performance",
                    "Writing, art, music",
                    "Romance and celebration",
                    "Travel and adventure"
                ),
                challenges = listOf(
                    "Scattering energy across too many projects",
                    "Superficiality in relationships",
                    "Avoiding necessary serious matters",
                    "Overindulgence",
                    "Gossip or careless communication"
                ),
                advice = "Let yourself play and create without excessive self-criticism. This is your year to shine socially and artistically. Just maintain enough focus to complete what you start. Balance fun with responsibility."
            )
            4 -> PersonalYearDetailedInterpretation(
                year = 4,
                theme = "Building Foundations and Hard Work",
                overview = "Personal Year 4 requires discipline, organization, and dedicated effort. After the expansive creativity of year 3, now comes the time to build solid foundations. This year may feel restrictive, but the structures you create now will support you for years to come.",
                keyThemes = listOf(
                    "Hard work and discipline",
                    "Building foundations",
                    "Organization and systems",
                    "Health and routine",
                    "Practical matters",
                    "Long-term planning"
                ),
                monthlyFocus = mapOf(
                    1 to "Initiate new organizational systems. Start building projects.",
                    2 to "Partner in practical matters. Cooperative building.",
                    3 to "Balance work with some creative enjoyment.",
                    4 to "Peak building energy. Maximum focus on foundations.",
                    5 to "Navigate changes while maintaining structure.",
                    6 to "Focus on home foundations. Family responsibilities.",
                    7 to "Reflect on building progress. Plan next phases.",
                    8 to "Financial foundations. Business structure.",
                    9 to "Complete building phases. Evaluate what you've created.",
                    10 to "New building initiatives. Fresh practical projects.",
                    11 to "Intuitive insights about long-term building.",
                    12 to "Prepare for year 5's changes while cementing current foundations."
                ),
                opportunities = listOf(
                    "Creating lasting systems and structures",
                    "Developing discipline and work ethic",
                    "Real estate and property matters",
                    "Career advancement through hard work",
                    "Health improvements through routine",
                    "Building financial security"
                ),
                challenges = listOf(
                    "Feeling restricted or bored",
                    "Overwork and burnout",
                    "Rigidity and resistance to change",
                    "Focusing too much on limitations",
                    "Neglecting relationships for work"
                ),
                advice = "Embrace the discipline this year requires. The foundations you build now will support your future growth. Take care of your health, create systems, and work steadily toward long-term goals."
            )
            5 -> PersonalYearDetailedInterpretation(
                year = 5,
                theme = "Change, Freedom, and Adventure",
                overview = "Personal Year 5 brings change, excitement, and new experiences. After the discipline of year 4, the universe now invites exploration and freedom. Expect the unexpected, embrace change, and expand your horizons. This is the year of adventure.",
                keyThemes = listOf(
                    "Change and transformation",
                    "Freedom and independence",
                    "Adventure and travel",
                    "New experiences",
                    "Adaptability",
                    "Breaking free of limitations"
                ),
                monthlyFocus = mapOf(
                    1 to "Initiate changes. Break free from restrictions.",
                    2 to "Balance freedom with relationship needs.",
                    3 to "Express yourself freely. Social adventures.",
                    4 to "Ground changes in practical reality.",
                    5 to "Peak freedom energy. Major changes and adventures.",
                    6 to "Balance adventure with home responsibilities.",
                    7 to "Reflect on changes. Spiritual adventures.",
                    8 to "Financial changes. Business adventures.",
                    9 to "Release what blocks your freedom. Complete transitions.",
                    10 to "New adventures begin. Fresh freedom.",
                    11 to "Visionary changes. Trust intuitive guidance about new directions.",
                    12 to "Prepare for year 6's responsibility focus. Celebrate freedom gained."
                ),
                opportunities = listOf(
                    "Major life changes and transitions",
                    "Travel and exploration",
                    "New romantic connections",
                    "Career changes",
                    "Learning new skills",
                    "Breaking unhealthy patterns"
                ),
                challenges = listOf(
                    "Impulsive decisions you'll regret",
                    "Overindulgence in pleasure",
                    "Commitment phobia",
                    "Scattered energy",
                    "Instability affecting others"
                ),
                advice = "Say yes to new experiences while maintaining some stability. Not every change is necessary, but openness to change is. Travel if possible. Break free from what limits you, but don't abandon responsibilities recklessly."
            )
            6 -> PersonalYearDetailedInterpretation(
                year = 6,
                theme = "Love, Family, and Responsibility",
                overview = "Personal Year 6 centers on home, family, love, and responsibility. After the adventures of year 5, now comes the time to nurture relationships, create beauty, and accept responsibilities. This is often a year of significant relationship events and domestic focus.",
                keyThemes = listOf(
                    "Love and relationships",
                    "Family and home",
                    "Responsibility and duty",
                    "Beauty and harmony",
                    "Service to others",
                    "Domestic matters"
                ),
                monthlyFocus = mapOf(
                    1 to "Take initiative in relationships. Lead in family matters.",
                    2 to "Deep partnership focus. Harmony in relationships.",
                    3 to "Creative expression of love. Social family activities.",
                    4 to "Practical home matters. Family foundations.",
                    5 to "Balance family with need for some freedom.",
                    6 to "Peak love and home energy. Major relationship or family events.",
                    7 to "Reflect on relationships. Spiritual family connections.",
                    8 to "Financial family matters. Providing for loved ones.",
                    9 to "Complete family cycles. Release unhealthy relationship patterns.",
                    10 to "New relationship initiatives. Fresh family starts.",
                    11 to "Intuitive relationship insights. Soul connections.",
                    12 to "Prepare for year 7's reflection while cherishing family connections."
                ),
                opportunities = listOf(
                    "Marriage or deepening commitment",
                    "Starting or expanding family",
                    "Home purchase or improvement",
                    "Healing family relationships",
                    "Creative domestic projects",
                    "Taking on meaningful responsibilities"
                ),
                challenges = listOf(
                    "Feeling burdened by responsibilities",
                    "Becoming controlling or martyr-like",
                    "Perfectionism in relationships",
                    "Difficulty setting boundaries",
                    "Neglecting self for others"
                ),
                advice = "Embrace your responsibilities with love. Create beauty in your home and relationships. Be careful not to sacrifice yourself completely - you cannot pour from an empty cup. Balance giving with receiving."
            )
            7 -> PersonalYearDetailedInterpretation(
                year = 7,
                theme = "Reflection, Wisdom, and Spiritual Growth",
                overview = "Personal Year 7 calls for introspection, study, and spiritual development. After the relationship focus of year 6, now is the time to go within. This is a year for reflection, research, rest, and connecting with your deeper self.",
                keyThemes = listOf(
                    "Reflection and introspection",
                    "Study and research",
                    "Spiritual development",
                    "Rest and retreat",
                    "Inner wisdom",
                    "Quality over quantity"
                ),
                monthlyFocus = mapOf(
                    1 to "Initiate spiritual practices. Begin studies.",
                    2 to "Intuitive partnership. Soul connections.",
                    3 to "Express insights creatively. Thoughtful communication.",
                    4 to "Practical spiritual discipline. Structured study.",
                    5 to "Spiritual adventures. Mind-expanding experiences.",
                    6 to "Spiritual family connections. Sacred home space.",
                    7 to "Peak reflection and spiritual energy. Deep inner work.",
                    8 to "Manifest spiritual insights into material form.",
                    9 to "Complete spiritual phases. Release limiting beliefs.",
                    10 to "New spiritual directions. Fresh wisdom practices.",
                    11 to "Heightened intuition and spiritual vision.",
                    12 to "Prepare for year 8's manifestation while integrating spiritual growth."
                ),
                opportunities = listOf(
                    "Deep spiritual development",
                    "Academic or research pursuits",
                    "Developing intuitive abilities",
                    "Health through holistic approaches",
                    "Writing and intellectual work",
                    "Finding meaning and purpose"
                ),
                challenges = listOf(
                    "Isolation becoming loneliness",
                    "Over-analysis and mental loops",
                    "Skepticism blocking intuition",
                    "Feeling disconnected from worldly life",
                    "Depression from too much introspection"
                ),
                advice = "Honor your need for solitude and reflection without becoming isolated. This is a year for inner work, not external achievement. Trust your intuition. Study, reflect, and grow. Rest more than usual."
            )
            8 -> PersonalYearDetailedInterpretation(
                year = 8,
                theme = "Achievement, Power, and Karmic Return",
                overview = "Personal Year 8 brings focus to material achievement, power, and karmic returns. After the reflection of year 7, now is the time to manifest and achieve in the material world. This is a year of business success, financial opportunity, and reaping what you've sown.",
                keyThemes = listOf(
                    "Material achievement",
                    "Business and finance",
                    "Power and authority",
                    "Karmic return",
                    "Recognition and status",
                    "Abundance manifestation"
                ),
                monthlyFocus = mapOf(
                    1 to "Launch business initiatives. Take charge.",
                    2 to "Business partnerships. Cooperative achievement.",
                    3 to "Creative business expression. Networking.",
                    4 to "Build business foundations. Financial systems.",
                    5 to "Business changes. Financial adaptability.",
                    6 to "Family and business balance. Business responsibilities.",
                    7 to "Reflect on business direction. Strategic planning.",
                    8 to "Peak achievement energy. Maximum business and financial focus.",
                    9 to "Complete business cycles. Financial release and completion.",
                    10 to "New business beginnings. Fresh financial starts.",
                    11 to "Visionary business insights. Inspired achievement.",
                    12 to "Prepare for year 9's completion while celebrating achievements."
                ),
                opportunities = listOf(
                    "Career advancement and recognition",
                    "Financial gains and investment",
                    "Business success and expansion",
                    "Taking leadership positions",
                    "Real estate and property",
                    "Achieving long-held goals"
                ),
                challenges = listOf(
                    "Becoming materialistic or ruthless",
                    "Power struggles and ego conflicts",
                    "Financial setbacks if past actions were unethical",
                    "Workaholic tendencies",
                    "Neglecting spiritual for material"
                ),
                advice = "This is your year to achieve in the material world. Past efforts bear fruit now. Handle success responsibly and ethically. Remember that karmic justice operates - how you gained will determine what you keep."
            )
            9 -> PersonalYearDetailedInterpretation(
                year = 9,
                theme = "Completion, Release, and Transformation",
                overview = "Personal Year 9 marks the end of the nine-year cycle. This is a year of completion, letting go, and preparing for new beginnings. Release what no longer serves you, forgive, complete unfinished business, and clear space for the new cycle beginning next year.",
                keyThemes = listOf(
                    "Completion and endings",
                    "Release and letting go",
                    "Forgiveness",
                    "Humanitarian service",
                    "Transformation",
                    "Preparation for new cycle"
                ),
                monthlyFocus = mapOf(
                    1 to "Begin release processes. Initiate completions.",
                    2 to "Release relationships gently. Forgive partners.",
                    3 to "Express completions creatively. Farewell communications.",
                    4 to "Complete practical matters. Final foundations.",
                    5 to "Release need for control over change.",
                    6 to "Family completions. Release family burdens.",
                    7 to "Deep reflection on the cycle. Spiritual release.",
                    8 to "Complete financial matters. Release material attachments.",
                    9 to "Peak completion energy. Major releases and endings.",
                    10 to "First hints of new cycle. Fresh perspective emerging.",
                    11 to "Visionary insights about new cycle. Spiritual preparation.",
                    12 to "Final completions. Ready for year 1's new beginning."
                ),
                opportunities = listOf(
                    "Clearing karma through service",
                    "Healing old wounds",
                    "Completing long-term projects",
                    "Philanthropic activities",
                    "Global perspective and travel",
                    "Spiritual transcendence"
                ),
                challenges = listOf(
                    "Difficulty letting go",
                    "Grief over necessary endings",
                    "Trying to start new things prematurely",
                    "Exhaustion from completing everything",
                    "Feeling lost as old identity releases"
                ),
                advice = "Trust the process of release. What falls away now is making room for the new. Forgive everyone including yourself. Complete what you can and release what you cannot. Practice generosity and service. Prepare for rebirth."
            )
            else -> PersonalYearDetailedInterpretation(
                year = year,
                theme = "Personal Development",
                overview = "This year offers opportunities for growth and development based on its unique energy.",
                keyThemes = listOf("Growth", "Development", "Progress"),
                monthlyFocus = emptyMap(),
                opportunities = listOf("Personal growth", "New experiences"),
                challenges = listOf("Unknown challenges"),
                advice = "Stay open to the experiences this year brings."
            )
        }
    }

    // Helper functions for combinations
    private fun getNumberHarmony(num1: Int, num2: Int): HarmonyLevel {
        val harmonious = setOf(
            Pair(1, 3), Pair(1, 5), Pair(1, 9),
            Pair(2, 4), Pair(2, 6), Pair(2, 8),
            Pair(3, 5), Pair(3, 6), Pair(3, 9),
            Pair(4, 6), Pair(4, 8),
            Pair(5, 7), Pair(5, 9),
            Pair(6, 9)
        )
        val challenging = setOf(
            Pair(1, 4), Pair(1, 6), Pair(1, 8),
            Pair(2, 5), Pair(2, 9),
            Pair(3, 4), Pair(3, 8),
            Pair(4, 5), Pair(4, 9),
            Pair(5, 6), Pair(5, 8),
            Pair(7, 8), Pair(7, 9),
            Pair(8, 9)
        )

        val pair = if (num1 <= num2) Pair(num1, num2) else Pair(num2, num1)
        val samePair = num1 == num2

        return when {
            samePair -> HarmonyLevel.INTENSIFIED
            harmonious.contains(pair) -> HarmonyLevel.HARMONIOUS
            challenging.contains(pair) -> HarmonyLevel.CHALLENGING
            else -> HarmonyLevel.NEUTRAL
        }
    }

    private fun generateCombinationInterpretation(num1: Int, num2: Int, type1: String, type2: String): String {
        val harmony = getNumberHarmony(num1, num2)
        return when (harmony) {
            HarmonyLevel.HARMONIOUS -> "Your $type1 ($num1) and $type2 ($num2) work together harmoniously. Your outer path and inner desires support each other, creating alignment between what you're meant to do and what you want."
            HarmonyLevel.CHALLENGING -> "Your $type1 ($num1) and $type2 ($num2) create creative tension. Your outer path and inner desires may seem to conflict, but this tension can drive growth when you learn to integrate both energies."
            HarmonyLevel.INTENSIFIED -> "Your $type1 and $type2 share the same number ($num1), intensifying this energy in your life. You have clear focus but may need to consciously develop balancing energies."
            HarmonyLevel.NEUTRAL -> "Your $type1 ($num1) and $type2 ($num2) have a neutral relationship. They operate somewhat independently, giving you versatility in how you express your purpose and desires."
        }
    }

    private fun getCombinationChallenges(num1: Int, num2: Int): List<String> {
        return listOf(
            "Balancing outer demands with inner needs",
            "Finding ways to honor both energies",
            "Avoiding neglecting either aspect"
        )
    }

    private fun getCombinationGifts(num1: Int, num2: Int): List<String> {
        return listOf(
            "Multiple perspectives and approaches",
            "Creative tension that drives growth",
            "Ability to adapt to various situations"
        )
    }

    private fun getCombinationAdvice(num1: Int, num2: Int): String {
        return "Learn to see both numbers as complementary aspects of your complete self. Your Life Path shows what you're here to do, while your Soul Urge reveals your deepest motivations. Integration brings fulfillment."
    }
}

// Data Classes

data class MasterNumberInterpretation(
    val number: Int,
    val archetype: String,
    val coreEssence: String,
    val spiritualSignificance: String,
    val higherPurpose: String,
    val challenges: List<String>,
    val gifts: List<String>,
    val lifeLesson: String,
    val relationships: String,
    val career: String,
    val health: String,
    val affirmation: String,
    val shadowSide: String,
    val practicalAdvice: List<String>,
    val famousExamples: String
)

data class KarmicDebtInterpretation(
    val number: Int,
    val theme: String,
    val coreLesson: String,
    val pastLifePattern: String,
    val currentLifeManifestations: List<String>,
    val transformationPath: String,
    val practicalGuidance: List<String>,
    val affirmation: String,
    val blessingInDisguise: String,
    val warningSign: String,
    val relationships: String
)

data class NumberCombinationInterpretation(
    val number1: Int,
    val number1Type: String,
    val number2: Int,
    val number2Type: String,
    val harmonyLevel: HarmonyLevel,
    val interpretation: String,
    val challenges: List<String>,
    val gifts: List<String>,
    val advice: String
)

data class PersonalYearDetailedInterpretation(
    val year: Int,
    val theme: String,
    val overview: String,
    val keyThemes: List<String>,
    val monthlyFocus: Map<Int, String>,
    val opportunities: List<String>,
    val challenges: List<String>,
    val advice: String
)

enum class HarmonyLevel {
    HARMONIOUS,
    CHALLENGING,
    INTENSIFIED,
    NEUTRAL
}
