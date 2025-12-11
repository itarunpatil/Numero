package com.numero.storm.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Surface
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.numero.storm.R
import com.numero.storm.data.model.Profile
import com.numero.storm.ui.components.LoadingIndicator
import com.numero.storm.ui.components.NumberCard
import com.numero.storm.ui.components.NumberDisplay
import com.numero.storm.ui.components.NumberDisplaySize
import com.numero.storm.ui.components.NumeroTopBar
import com.numero.storm.ui.components.SectionHeader
import com.numero.storm.ui.theme.MasterNumberGold
import com.numero.storm.ui.viewmodel.HomeUiState
import com.numero.storm.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToProfile: (Long) -> Unit,
    onNavigateToProfiles: () -> Unit,
    onNavigateToCompatibility: () -> Unit,
    onNavigateToDaily: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToCreateProfile: () -> Unit,
    onNavigateToNameAnalysis: ((Long) -> Unit)? = null,
    onNavigateToYearlyForecast: ((Long) -> Unit)? = null,
    onNavigateToLuckyDays: ((Long) -> Unit)? = null,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            NumeroTopBar(
                title = stringResource(R.string.app_name),
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = stringResource(R.string.settings))
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCreateProfile,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_profile))
            }
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicator()
            }
        } else {
            HomeContent(
                uiState = uiState,
                modifier = Modifier.padding(padding),
                onNavigateToProfile = onNavigateToProfile,
                onNavigateToProfiles = onNavigateToProfiles,
                onNavigateToCompatibility = onNavigateToCompatibility,
                onNavigateToDaily = onNavigateToDaily,
                onNavigateToCreateProfile = onNavigateToCreateProfile,
                onNavigateToNameAnalysis = onNavigateToNameAnalysis,
                onNavigateToYearlyForecast = onNavigateToYearlyForecast,
                onNavigateToLuckyDays = onNavigateToLuckyDays
            )
        }
    }
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
    onNavigateToProfile: (Long) -> Unit,
    onNavigateToProfiles: () -> Unit,
    onNavigateToCompatibility: () -> Unit,
    onNavigateToDaily: () -> Unit,
    onNavigateToCreateProfile: () -> Unit,
    onNavigateToNameAnalysis: ((Long) -> Unit)? = null,
    onNavigateToYearlyForecast: ((Long) -> Unit)? = null,
    onNavigateToLuckyDays: ((Long) -> Unit)? = null
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 88.dp)
    ) {
        // Today's Numbers Section
        item {
            TodaySection(
                universalDay = uiState.universalDay,
                personalDay = uiState.personalDay,
                personalMonth = uiState.personalMonth,
                personalYear = uiState.personalYear,
                dailyInsight = uiState.dailyInsight,
                onNavigateToDaily = onNavigateToDaily
            )
        }

        // Primary Profile Section
        item {
            if (uiState.primaryProfile != null && uiState.primaryAnalysis != null) {
                SectionHeader(title = stringResource(R.string.your_numbers))
                PrimaryProfileCard(
                    profile = uiState.primaryProfile,
                    lifePathNumber = uiState.primaryAnalysis.lifePathNumber,
                    expressionNumber = uiState.primaryAnalysis.expressionNumber,
                    soulUrgeNumber = uiState.primaryAnalysis.soulUrgeNumber,
                    isMasterLifePath = uiState.primaryAnalysis.lifePathMasterNumber,
                    onClick = { onNavigateToProfile(uiState.primaryProfile.id) }
                )
            } else if (uiState.profiles.isEmpty()) {
                EmptyProfilesCard(onCreateProfile = onNavigateToCreateProfile)
            }
        }

        // Quick Actions
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(title = stringResource(R.string.quick_actions))
            QuickActionsRow(
                onCompatibility = onNavigateToCompatibility,
                onDaily = onNavigateToDaily,
                onProfiles = onNavigateToProfiles
            )
        }

        // Advanced Features Section (only if primary profile exists)
        if (uiState.primaryProfile != null) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                SectionHeader(title = stringResource(R.string.discover_more))
                AdvancedFeaturesRow(
                    profileId = uiState.primaryProfile.id,
                    onNavigateToNameAnalysis = onNavigateToNameAnalysis,
                    onNavigateToYearlyForecast = onNavigateToYearlyForecast,
                    onNavigateToLuckyDays = onNavigateToLuckyDays
                )
            }
        }

        // All Profiles
        if (uiState.profiles.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                SectionHeader(
                    title = stringResource(R.string.profiles),
                    action = {
                        Text(
                            text = stringResource(R.string.view_all),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable(onClick = onNavigateToProfiles)
                        )
                    }
                )
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.profiles.take(5)) { profile ->
                        ProfileChip(
                            profile = profile,
                            isPrimary = profile.isPrimary,
                            onClick = { onNavigateToProfile(profile.id) }
                        )
                    }
                }
            }
        }

        // Educational Section for New Users
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(title = stringResource(R.string.learn_numerology))
            LearnNumerologyCard()
        }

        // Daily Tip
        item {
            Spacer(modifier = Modifier.height(16.dp))
            DailyTipCard()
        }
    }
}

@Composable
private fun TodaySection(
    universalDay: Int,
    personalDay: Int?,
    personalMonth: Int?,
    personalYear: Int?,
    dailyInsight: String,
    onNavigateToDaily: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(tween(400)) + slideInVertically(
            initialOffsetY = { -40 },
            animationSpec = tween(400, easing = FastOutSlowInEasing)
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(24.dp),
                    ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                )
                .clickable(onClick = onNavigateToDaily),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f)
                            )
                        )
                    )
            ) {
                // Decorative element
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.today),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        NumberColumn(
                            label = stringResource(R.string.universal_day),
                            number = universalDay,
                            animated = true
                        )

                        if (personalDay != null) {
                            NumberColumn(
                                label = stringResource(R.string.personal_day),
                                number = personalDay,
                                animated = true
                            )
                        }

                        if (personalYear != null) {
                            NumberColumn(
                                label = stringResource(R.string.personal_year),
                                number = personalYear,
                                animated = true
                            )
                        }
                    }

                    if (dailyInsight.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Info,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = dailyInsight.take(150) + if (dailyInsight.length > 150) "..." else "",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PrimaryProfileCard(
    profile: Profile,
    lifePathNumber: Int,
    expressionNumber: Int,
    soulUrgeNumber: Int,
    isMasterLifePath: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = profile.firstName.first().toString(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = profile.displayName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = stringResource(R.string.primary_profile),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                NumberColumn(
                    label = stringResource(R.string.life_path),
                    number = lifePathNumber,
                    isMaster = isMasterLifePath
                )
                NumberColumn(
                    label = stringResource(R.string.expression),
                    number = expressionNumber
                )
                NumberColumn(
                    label = stringResource(R.string.soul_urge),
                    number = soulUrgeNumber
                )
            }
        }
    }
}

@Composable
private fun NumberColumn(
    label: String,
    number: Int,
    isMaster: Boolean = false,
    animated: Boolean = false
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        NumberDisplay(
            number = number,
            size = NumberDisplaySize.MEDIUM,
            isMasterNumber = isMaster,
            animated = animated || isMaster
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun QuickActionsRow(
    onCompatibility: () -> Unit,
    onDaily: () -> Unit,
    onProfiles: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        QuickActionCard(
            icon = Icons.Default.Favorite,
            label = stringResource(R.string.compatibility),
            modifier = Modifier.weight(1f),
            accentColor = MaterialTheme.colorScheme.tertiary,
            onClick = onCompatibility
        )
        QuickActionCard(
            icon = Icons.Default.Today,
            label = stringResource(R.string.daily),
            modifier = Modifier.weight(1f),
            accentColor = MaterialTheme.colorScheme.secondary,
            onClick = onDaily
        )
        QuickActionCard(
            icon = Icons.Default.Person,
            label = stringResource(R.string.profiles),
            modifier = Modifier.weight(1f),
            accentColor = MaterialTheme.colorScheme.primary,
            onClick = onProfiles
        )
    }
}

@Composable
private fun QuickActionCard(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier,
    accentColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(bounded = true),
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = accentColor
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun ProfileChip(
    profile: Profile,
    isPrimary: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isPrimary) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(
                        if (isPrimary) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.secondary
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = profile.firstName.first().toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = if (isPrimary) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.onSecondary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = profile.firstName,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun EmptyProfilesCard(onCreateProfile: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onCreateProfile),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.no_profiles_yet),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.create_first_profile),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun AdvancedFeaturesRow(
    profileId: Long,
    onNavigateToNameAnalysis: ((Long) -> Unit)?,
    onNavigateToYearlyForecast: ((Long) -> Unit)?,
    onNavigateToLuckyDays: ((Long) -> Unit)?
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (onNavigateToNameAnalysis != null) {
            item {
                AdvancedFeatureCard(
                    icon = Icons.Default.TextFields,
                    title = stringResource(R.string.name_analysis),
                    description = stringResource(R.string.name_analysis_desc),
                    accentColor = MaterialTheme.colorScheme.primary,
                    onClick = { onNavigateToNameAnalysis(profileId) }
                )
            }
        }

        if (onNavigateToYearlyForecast != null) {
            item {
                AdvancedFeatureCard(
                    icon = Icons.Default.TrendingUp,
                    title = stringResource(R.string.yearly_forecast),
                    description = stringResource(R.string.yearly_forecast_desc),
                    accentColor = MaterialTheme.colorScheme.tertiary,
                    onClick = { onNavigateToYearlyForecast(profileId) }
                )
            }
        }

        if (onNavigateToLuckyDays != null) {
            item {
                AdvancedFeatureCard(
                    icon = Icons.Default.Diamond,
                    title = stringResource(R.string.lucky_days),
                    description = stringResource(R.string.lucky_days_desc),
                    accentColor = MaterialTheme.colorScheme.secondary,
                    onClick = { onNavigateToLuckyDays(profileId) }
                )
            }
        }
    }
}

@Composable
private fun AdvancedFeatureCard(
    icon: ImageVector,
    title: String,
    description: String,
    accentColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(accentColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = accentColor
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun LearnNumerologyCard() {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.School,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.what_is_numerology),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Icon(
                    imageVector = Icons.Outlined.HelpOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Text(
                        text = stringResource(R.string.numerology_intro),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Core Numbers Quick Guide
                    CoreNumberGuideItem(
                        number = "Life Path",
                        description = stringResource(R.string.life_path_quick_desc)
                    )
                    CoreNumberGuideItem(
                        number = "Expression",
                        description = stringResource(R.string.expression_quick_desc)
                    )
                    CoreNumberGuideItem(
                        number = "Soul Urge",
                        description = stringResource(R.string.soul_urge_quick_desc)
                    )
                }
            }

            if (!expanded) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.tap_to_learn),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun CoreNumberGuideItem(number: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = number,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun DailyTipCard() {
    val tips = listOf(
        R.string.daily_tip_1,
        R.string.daily_tip_2,
        R.string.daily_tip_3,
        R.string.daily_tip_4,
        R.string.daily_tip_5
    )
    val today = java.time.LocalDate.now().dayOfYear
    val tipIndex = today % tips.size

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Default.Lightbulb,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.daily_tip),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(tips[tipIndex]),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
