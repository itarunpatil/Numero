package com.numero.storm.ui.screen.nameanalysis

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.numero.storm.R
import com.numero.storm.domain.calculator.FullNameAnalysis
import com.numero.storm.domain.calculator.InclusionNumber
import com.numero.storm.domain.calculator.InclusionStatus
import com.numero.storm.domain.calculator.LetterAnalysis
import com.numero.storm.domain.calculator.NameAnalysis
import com.numero.storm.domain.calculator.Plane
import com.numero.storm.domain.calculator.PlaneAnalysis
import com.numero.storm.domain.calculator.SpecialLetterAnalysis
import com.numero.storm.ui.components.LoadingIndicator
import com.numero.storm.ui.components.NumberDisplay
import com.numero.storm.ui.components.NumberDisplaySize
import com.numero.storm.ui.components.NumeroTopBar
import com.numero.storm.ui.components.getNumberColor
import com.numero.storm.ui.viewmodel.NameAnalysisViewModel

@Composable
fun NameAnalysisScreen(
    profileId: Long,
    onNavigateBack: () -> Unit,
    viewModel: NameAnalysisViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(profileId) {
        viewModel.loadNameAnalysis(profileId)
    }

    Scaffold(
        topBar = {
            NumeroTopBar(
                title = stringResource(R.string.name_analysis),
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
            uiState.analysis != null -> {
                NameAnalysisContent(
                    analysis = uiState.analysis!!,
                    profileName = uiState.profileName,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
private fun NameAnalysisContent(
    analysis: FullNameAnalysis,
    profileName: String,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header with name
        item {
            NameHeaderCard(name = profileName, totalLetters = analysis.totalLetters)
        }

        // Letter Breakdown Section
        item {
            LetterBreakdownCard(letters = analysis.letterAnalyses)
        }

        // Special Letters Section
        item {
            SpecialLettersCard(
                cornerstone = analysis.cornerstone,
                capstone = analysis.capstone,
                firstVowel = analysis.firstVowel
            )
        }

        // Inclusion Chart Section
        item {
            InclusionChartCard(inclusionChart = analysis.inclusionChart)
        }

        // Planes of Expression Section
        item {
            PlanesOfExpressionCard(planes = analysis.planeAnalyses)
        }

        // Hidden Passion and Subconscious Self
        item {
            HiddenNumbersCard(
                hiddenPassion = analysis.hiddenPassionNumber,
                subconsciousSelf = analysis.subconsciousSelfNumber
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun NameHeaderCard(name: String, totalLetters: Int) {
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
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = name.uppercase(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 4.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$totalLetters letters analyzed",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LetterBreakdownCard(letters: List<LetterAnalysis>) {
    var isExpanded by remember { mutableStateOf(true) }
    var selectedLetter by remember { mutableStateOf<LetterAnalysis?>(null) }

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
            // Header
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
                        imageVector = Icons.Outlined.School,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.letter_breakdown),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    // Letter tiles
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        letters.forEach { letter ->
                            LetterTile(
                                letter = letter,
                                isSelected = selectedLetter == letter,
                                onClick = {
                                    selectedLetter = if (selectedLetter == letter) null else letter
                                }
                            )
                        }
                    }

                    // Selected letter interpretation
                    AnimatedVisibility(visible = selectedLetter != null) {
                        selectedLetter?.let { letter ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Letter ${letter.letter}",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = stringResource(R.string.letter_value, letter.value),
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Surface(
                                            color = if (letter.isVowel)
                                                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
                                            else
                                                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                text = if (letter.isVowel)
                                                    stringResource(R.string.vowel_indicator)
                                                else
                                                    stringResource(R.string.consonant_indicator),
                                                style = MaterialTheme.typography.labelSmall,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                            )
                                        }

                                        Surface(
                                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                text = "${letter.plane.displayName} Plane",
                                                style = MaterialTheme.typography.labelSmall,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = letter.interpretation,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun LetterTile(
    letter: LetterAnalysis,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val numberColor = getNumberColor(letter.value)

    Surface(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        color = if (isSelected) numberColor.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(8.dp),
        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, numberColor) else null
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = letter.letter.toString(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) numberColor else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = letter.value.toString(),
                style = MaterialTheme.typography.labelSmall,
                color = if (letter.isVowel) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun SpecialLettersCard(
    cornerstone: SpecialLetterAnalysis?,
    capstone: SpecialLetterAnalysis?,
    firstVowel: SpecialLetterAnalysis?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Special Letters",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            cornerstone?.let { SpecialLetterItem(it, stringResource(R.string.cornerstone_explanation)) }
            capstone?.let {
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(12.dp))
                SpecialLetterItem(it, stringResource(R.string.capstone_explanation))
            }
            firstVowel?.let {
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(12.dp))
                SpecialLetterItem(it, stringResource(R.string.first_vowel_explanation))
            }
        }
    }
}

@Composable
private fun SpecialLetterItem(letter: SpecialLetterAnalysis, subtitle: String) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(getNumberColor(letter.value).copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = letter.letter.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = getNumberColor(letter.value)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = letter.type,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "= ${letter.value}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        AnimatedVisibility(visible = isExpanded) {
            Text(
                text = letter.meaning,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp, start = 52.dp)
            )
        }
    }
}

@Composable
private fun InclusionChartCard(inclusionChart: List<InclusionNumber>) {
    var expandedNumber by remember { mutableStateOf<Int?>(null) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.inclusion_chart),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Shows how many times each number appears in your name",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Number grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                inclusionChart.forEach { inclusion ->
                    InclusionNumberItem(
                        inclusion = inclusion,
                        isExpanded = expandedNumber == inclusion.number,
                        onClick = {
                            expandedNumber = if (expandedNumber == inclusion.number) null else inclusion.number
                        }
                    )
                }
            }

            // Expanded interpretation
            AnimatedVisibility(visible = expandedNumber != null) {
                val selected = inclusionChart.find { it.number == expandedNumber }
                selected?.let { inclusion ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        color = when (inclusion.status) {
                            InclusionStatus.MISSING -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
                            InclusionStatus.ABUNDANT -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Number ${inclusion.number}",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Surface(
                                    color = when (inclusion.status) {
                                        InclusionStatus.MISSING -> MaterialTheme.colorScheme.error.copy(alpha = 0.2f)
                                        InclusionStatus.ABUNDANT -> MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                                    },
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = when (inclusion.status) {
                                            InclusionStatus.MISSING -> stringResource(R.string.missing_number)
                                            InclusionStatus.ABUNDANT -> stringResource(R.string.abundant_number)
                                            else -> stringResource(R.string.present_number)
                                        },
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = inclusion.interpretation,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InclusionNumberItem(
    inclusion: InclusionNumber,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when (inclusion.status) {
        InclusionStatus.MISSING -> MaterialTheme.colorScheme.error.copy(alpha = 0.2f)
        InclusionStatus.ABUNDANT -> MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(backgroundColor)
                .then(
                    if (isExpanded) Modifier.border(
                        2.dp,
                        MaterialTheme.colorScheme.primary,
                        CircleShape
                    ) else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = inclusion.number.toString(),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = if (inclusion.status == InclusionStatus.MISSING)
                    MaterialTheme.colorScheme.error
                else
                    MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "×${inclusion.count}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun PlanesOfExpressionCard(planes: List<PlaneAnalysis>) {
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
                        imageVector = Icons.Outlined.Psychology,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.planes_of_expression),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null
                )
            }

            // Always show visual summary
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                planes.forEach { plane ->
                    PlaneProgressBar(plane = plane)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            // Expanded details
            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    planes.forEach { plane ->
                        PlaneDetailCard(plane = plane)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun PlaneProgressBar(plane: PlaneAnalysis) {
    val color = when (plane.plane) {
        Plane.PHYSICAL -> Color(0xFF4CAF50)
        Plane.MENTAL -> Color(0xFF2196F3)
        Plane.EMOTIONAL -> Color(0xFFE91E63)
        Plane.INTUITIVE -> Color(0xFF9C27B0)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = plane.plane.displayName,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.width(80.dp)
        )
        LinearProgressIndicator(
            progress = { (plane.percentage / 100f).coerceIn(0f, 1f) },
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = color.copy(alpha = 0.2f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "${plane.percentage}%",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.width(36.dp),
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun PlaneDetailCard(plane: PlaneAnalysis) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = plane.plane.displayName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Value: ${plane.reducedValue}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Letters: ${plane.letters.joinToString(", ")}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = plane.interpretation,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun HiddenNumbersCard(hiddenPassion: Int?, subconsciousSelf: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Additional Insights",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Hidden Passion
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.hidden_passion),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Most frequent number in your name",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (hiddenPassion != null) {
                    NumberDisplay(
                        number = hiddenPassion,
                        size = NumberDisplaySize.SMALL
                    )
                } else {
                    Text(
                        text = "None",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(16.dp))

            // Subconscious Self
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Subconscious Self",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Number of different digits in name (1-9)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                NumberDisplay(
                    number = subconsciousSelf,
                    size = NumberDisplaySize.SMALL
                )
            }
        }
    }
}
