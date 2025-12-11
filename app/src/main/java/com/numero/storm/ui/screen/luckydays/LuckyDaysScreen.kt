package com.numero.storm.ui.screen.luckydays

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Numbers
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.numero.storm.R
import com.numero.storm.domain.calculator.ElementalAffinity
import com.numero.storm.domain.calculator.FavorabilityLevel
import com.numero.storm.domain.calculator.FavorableActivity
import com.numero.storm.domain.calculator.FavorableDay
import com.numero.storm.domain.calculator.LuckyColor
import com.numero.storm.domain.calculator.LuckyDayOfWeek
import com.numero.storm.domain.calculator.LuckyDirection
import com.numero.storm.domain.calculator.LuckyGemstone
import com.numero.storm.domain.calculator.LuckyMonth
import com.numero.storm.domain.calculator.LuckyNumber
import com.numero.storm.domain.calculator.LuckyProfile
import com.numero.storm.domain.calculator.MonthlyFavorableDays
import com.numero.storm.domain.calculator.PlanetaryInfluence
import com.numero.storm.ui.components.LoadingIndicator
import com.numero.storm.ui.components.NumberDisplay
import com.numero.storm.ui.components.NumberDisplaySize
import com.numero.storm.ui.components.NumeroTopBar
import com.numero.storm.ui.components.getNumberColor
import com.numero.storm.ui.viewmodel.LuckyDaysViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun LuckyDaysScreen(
    profileId: Long,
    onNavigateBack: () -> Unit,
    viewModel: LuckyDaysViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(profileId) {
        viewModel.loadLuckyDays(profileId)
    }

    Scaffold(
        topBar = {
            NumeroTopBar(
                title = stringResource(R.string.lucky_days),
                onNavigateBack = onNavigateBack
            )
        }
    ) { padding ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingIndicator()
                }
            }
            uiState.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.error!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            uiState.luckyProfile != null -> {
                LuckyDaysContent(
                    luckyProfile = uiState.luckyProfile!!,
                    currentMonthDays = uiState.currentMonthDays,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
private fun LuckyDaysContent(
    luckyProfile: LuckyProfile,
    currentMonthDays: MonthlyFavorableDays?,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Lucky Numbers
        item {
            LuckyNumbersCard(luckyNumbers = luckyProfile.luckyNumbers)
        }

        // Lucky Days of Week
        item {
            LuckyDaysOfWeekCard(luckyDays = luckyProfile.luckyDays)
        }

        // Monthly Calendar Overview
        currentMonthDays?.let { monthDays ->
            item {
                MonthlyOverviewCard(monthDays = monthDays)
            }
        }

        // Lucky Colors
        item {
            LuckyColorsCard(luckyColors = luckyProfile.luckyColors)
        }

        // Lucky Gemstones
        item {
            LuckyGemstonesCard(luckyGemstones = luckyProfile.luckyGemstones)
        }

        // Favorable Activities
        item {
            FavorableActivitiesCard(activities = luckyProfile.favorableActivities)
        }

        // Planetary Influence
        item {
            PlanetaryInfluenceCard(
                planetaryInfluence = luckyProfile.planetaryInfluence,
                elementalAffinity = luckyProfile.elementalAffinity
            )
        }

        // Lucky Directions
        item {
            LuckyDirectionsCard(luckyDirections = luckyProfile.luckyDirections)
        }

        // Lucky Months
        item {
            LuckyMonthsCard(luckyMonths = luckyProfile.luckyMonths)
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LuckyNumbersCard(luckyNumbers: List<LuckyNumber>) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isExpanded = !isExpanded }
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Numbers,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = stringResource(R.string.lucky_numbers),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null
                    )
                }

                // Number display
                FlowRow(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    luckyNumbers.forEach { luckyNumber ->
                        LuckyNumberChip(luckyNumber = luckyNumber)
                    }
                }

                // Expanded details
                AnimatedVisibility(visible = isExpanded) {
                    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                        luckyNumbers.forEach { luckyNumber ->
                            LuckyNumberDetail(luckyNumber = luckyNumber)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun LuckyNumberChip(luckyNumber: LuckyNumber) {
    val color = getNumberColor(luckyNumber.number)
    val isPrimary = luckyNumber.significance == "Primary"

    Surface(
        color = if (isPrimary) color.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(12.dp),
        border = if (isPrimary) androidx.compose.foundation.BorderStroke(2.dp, color) else null
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = luckyNumber.number.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = if (isPrimary) color else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = luckyNumber.significance,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun LuckyNumberDetail(luckyNumber: LuckyNumber) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberDisplay(
                number = luckyNumber.number,
                size = NumberDisplaySize.SMALL
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = luckyNumber.significance,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = luckyNumber.reason,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun LuckyDaysOfWeekCard(luckyDays: List<LuckyDayOfWeek>) {
    var isExpanded by remember { mutableStateOf(false) }
    val today = LocalDate.now().dayOfWeek

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.lucky_days_of_week),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null
                )
            }

            // Days row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DayOfWeek.entries.forEach { day ->
                    val isLucky = luckyDays.any { it.day == day }
                    val isToday = day == today

                    DayChip(
                        day = day,
                        isLucky = isLucky,
                        isToday = isToday
                    )
                }
            }

            // Expanded details
            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    luckyDays.forEach { luckyDay ->
                        LuckyDayDetail(luckyDay = luckyDay, isToday = luckyDay.day == today)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun DayChip(day: DayOfWeek, isLucky: Boolean, isToday: Boolean) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(
                when {
                    isLucky && isToday -> MaterialTheme.colorScheme.primary
                    isLucky -> MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    isToday -> MaterialTheme.colorScheme.surfaceVariant
                    else -> Color.Transparent
                }
            )
            .then(
                if (isToday && !isLucky) Modifier.border(
                    1.dp,
                    MaterialTheme.colorScheme.outline,
                    CircleShape
                ) else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.getDisplayName(TextStyle.NARROW, Locale.getDefault()),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (isLucky) FontWeight.Bold else FontWeight.Normal,
            color = when {
                isLucky && isToday -> MaterialTheme.colorScheme.onPrimary
                isLucky -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.onSurfaceVariant
            }
        )
    }
}

@Composable
private fun LuckyDayDetail(luckyDay: LuckyDayOfWeek, isToday: Boolean) {
    Surface(
        color = if (isToday)
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
        else
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = luckyDay.day.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    if (isToday) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.today),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = luckyDay.reason,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.best_for),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 4.dp)
            ) {
                items(luckyDay.bestActivities) { activity ->
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = activity,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MonthlyOverviewCard(monthDays: MonthlyFavorableDays) {
    var isExpanded by remember { mutableStateOf(false) }
    val today = LocalDate.now().dayOfMonth

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${monthDays.yearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${monthDays.yearMonth.year}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Personal Month ${monthDays.personalMonth}: ${monthDays.monthTheme}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null
                )
            }

            // Summary chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SummaryChip(
                    count = monthDays.excellentDays.size,
                    label = stringResource(R.string.excellent_days),
                    color = Color(0xFF4CAF50)
                )
                SummaryChip(
                    count = monthDays.goodDays.size,
                    label = stringResource(R.string.good_days),
                    color = Color(0xFF2196F3)
                )
                SummaryChip(
                    count = monthDays.challengingDays.size,
                    label = stringResource(R.string.challenging_days),
                    color = Color(0xFFFF9800)
                )
            }

            // Calendar grid when expanded
            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = monthDays.monthAdvice,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Day grid
                    val weeks = monthDays.allDays.chunked(7)
                    weeks.forEach { week ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            week.forEach { day ->
                                DayCell(
                                    day = day,
                                    isToday = day.date.dayOfMonth == today && day.date.month == LocalDate.now().month
                                )
                            }
                            // Fill empty cells
                            repeat(7 - week.size) {
                                Spacer(modifier = Modifier.size(40.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    // Legend
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        LegendItem(color = Color(0xFF4CAF50), label = "Excellent")
                        LegendItem(color = Color(0xFF2196F3), label = "Good")
                        LegendItem(color = Color(0xFFFF9800), label = "Challenging")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SummaryChip(count: Int, label: String, color: Color) {
    Surface(
        color = color.copy(alpha = 0.2f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun DayCell(day: FavorableDay, isToday: Boolean) {
    val color = when (day.favorabilityLevel) {
        FavorabilityLevel.EXCELLENT -> Color(0xFF4CAF50)
        FavorabilityLevel.GOOD -> Color(0xFF2196F3)
        FavorabilityLevel.MODERATE -> MaterialTheme.colorScheme.surfaceVariant
        FavorabilityLevel.CHALLENGING -> Color(0xFFFF9800)
    }

    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = if (day.favorabilityLevel == FavorabilityLevel.MODERATE) 1f else 0.3f))
            .then(
                if (isToday) Modifier.border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
            color = if (day.favorabilityLevel == FavorabilityLevel.MODERATE)
                MaterialTheme.colorScheme.onSurfaceVariant
            else
                color
        )
    }
}

@Composable
private fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.3f))
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LuckyColorsCard(luckyColors: List<LuckyColor>) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Palette,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.lucky_colors),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null
                )
            }

            // Color chips
            FlowRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                luckyColors.forEach { color ->
                    ColorChip(luckyColor = color)
                }
            }

            // Expanded details
            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    luckyColors.forEach { color ->
                        ColorDetail(luckyColor = color)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ColorChip(luckyColor: LuckyColor) {
    val colorValue = getColorFromName(luckyColor.color)
    val isPrimary = luckyColor.significance == "Primary"

    Surface(
        color = colorValue.copy(alpha = 0.3f),
        shape = RoundedCornerShape(16.dp),
        border = if (isPrimary) androidx.compose.foundation.BorderStroke(2.dp, colorValue) else null
    ) {
        Text(
            text = luckyColor.color,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (isPrimary) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
private fun ColorDetail(luckyColor: LuckyColor) {
    val colorValue = getColorFromName(luckyColor.color)

    Surface(
        color = colorValue.copy(alpha = 0.1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(colorValue)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = luckyColor.color,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = luckyColor.significance,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = luckyColor.usage,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun getColorFromName(name: String): Color {
    return when (name.lowercase()) {
        "red" -> Color(0xFFE53935)
        "gold" -> Color(0xFFFFD700)
        "yellow" -> Color(0xFFFFEB3B)
        "white" -> Color(0xFFF5F5F5)
        "cream" -> Color(0xFFFFFDD0)
        "light green" -> Color(0xFF90EE90)
        "orange" -> Color(0xFFFF9800)
        "pink" -> Color(0xFFE91E63)
        "green" -> Color(0xFF4CAF50)
        "brown" -> Color(0xFF795548)
        "blue" -> Color(0xFF2196F3)
        "turquoise" -> Color(0xFF00BCD4)
        "light blue" -> Color(0xFF03A9F4)
        "silver" -> Color(0xFFC0C0C0)
        "purple" -> Color(0xFF9C27B0)
        "violet" -> Color(0xFF7B1FA2)
        "grey", "gray" -> Color(0xFF9E9E9E)
        "black" -> Color(0xFF212121)
        "dark blue" -> Color(0xFF1565C0)
        "crimson" -> Color(0xFFDC143C)
        "coral" -> Color(0xFFFF7F50)
        else -> Color(0xFF9E9E9E)
    }
}

@Composable
private fun LuckyGemstonesCard(luckyGemstones: List<LuckyGemstone>) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Diamond,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.lucky_gemstones),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null
                )
            }

            // Gemstone names
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(luckyGemstones) { gemstone ->
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = gemstone.name,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            // Expanded details
            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    luckyGemstones.forEach { gemstone ->
                        GemstoneDetail(gemstone = gemstone)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun GemstoneDetail(gemstone: LuckyGemstone) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = gemstone.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = gemstone.properties,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = gemstone.howToUse,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun FavorableActivitiesCard(activities: List<FavorableActivity>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.favorable_activities),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            activities.forEach { activity ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = activity.activity,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = activity.reason,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PlanetaryInfluenceCard(
    planetaryInfluence: PlanetaryInfluence,
    elementalAffinity: ElementalAffinity
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Planetary Influence
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Public,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.planetary_influence),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = planetaryInfluence.planet,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = planetaryInfluence.influence,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = planetaryInfluence.advice,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Elemental Affinity
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocalFireDepartment,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.elemental_affinity),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = elementalAffinity.element,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = elementalAffinity.meaning,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.recommendations),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Medium
                    )
                    elementalAffinity.recommendations.forEach { recommendation ->
                        Text(
                            text = "- $recommendation",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LuckyDirectionsCard(luckyDirections: List<LuckyDirection>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Explore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.lucky_directions),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            luckyDirections.forEach { direction ->
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = direction.direction,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = direction.meaning,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LuckyMonthsCard(luckyMonths: List<LuckyMonth>) {
    var isExpanded by remember { mutableStateOf(false) }
    val currentMonth = LocalDate.now().monthValue

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.AutoAwesome,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.lucky_months),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null
                )
            }

            // Month chips
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(luckyMonths) { month ->
                    val isCurrentMonth = month.month == currentMonth
                    Surface(
                        color = if (isCurrentMonth)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = Month.of(month.month).getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                            style = MaterialTheme.typography.labelMedium,
                            color = if (isCurrentMonth)
                                MaterialTheme.colorScheme.onPrimary
                            else
                                MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            // Expanded details
            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    luckyMonths.forEach { month ->
                        val isCurrentMonth = month.month == currentMonth
                        Surface(
                            color = if (isCurrentMonth)
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                            else
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = Month.of(month.month).getDisplayName(TextStyle.FULL, Locale.getDefault()),
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    if (isCurrentMonth) {
                                        Surface(
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                text = stringResource(R.string.current),
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }
                                Text(
                                    text = month.reason,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = stringResource(R.string.favorable_for),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Medium
                                )
                                month.favorableFor.forEach { activity ->
                                    Text(
                                        text = "- $activity",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
