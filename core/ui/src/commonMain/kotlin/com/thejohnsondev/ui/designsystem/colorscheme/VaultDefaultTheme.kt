package com.thejohnsondev.ui.designsystem.colorscheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import com.thejohnsondev.model.settings.ThemeBrand
import com.thejohnsondev.ui.designsystem.getTypography
import vaultmultiplatform.core.ui.generated.resources.Res
import vaultmultiplatform.core.ui.generated.resources.ic_vault_108_gradient

fun getLightScheme(customTheme: ThemeBrand? = null) = lightColorScheme(
    primary = when(customTheme) {
        ThemeBrand.DEFAULT -> primaryLight
        ThemeBrand.BLUE_SKY -> primaryLight_blueSky
        ThemeBrand.GREEN_FOREST -> primaryLight_greenForest
        ThemeBrand.RED_ALGAE -> primaryLight_redAlgae
        ThemeBrand.SUNNY -> primaryLight_sunny
        else -> primaryLight
    },
    onPrimary = when(customTheme) {
        ThemeBrand.DEFAULT -> onPrimaryLight
        ThemeBrand.BLUE_SKY -> onPrimaryLight_blueSky
        ThemeBrand.GREEN_FOREST -> onPrimaryLight_greenForest
        ThemeBrand.RED_ALGAE -> onPrimaryLight_redAlgae
        ThemeBrand.SUNNY -> onPrimaryLight_sunny
        else -> onPrimaryLight
    },
    primaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> primaryContainerLight
        ThemeBrand.BLUE_SKY -> primaryContainerLight_blueSky
        ThemeBrand.GREEN_FOREST -> primaryContainerLight_greenForest
        ThemeBrand.RED_ALGAE -> primaryContainerLight_redAlgae
        ThemeBrand.SUNNY -> primaryContainerLight_sunny
        else -> primaryContainerLight
    },
    onPrimaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> onPrimaryContainerLight
        ThemeBrand.BLUE_SKY -> onPrimaryContainerLight_blueSky
        ThemeBrand.GREEN_FOREST -> onPrimaryContainerLight_greenForest
        ThemeBrand.RED_ALGAE -> onPrimaryContainerLight_redAlgae
        ThemeBrand.SUNNY -> onPrimaryContainerLight_sunny
        else -> onPrimaryContainerLight
    },
    secondary = when(customTheme) {
        ThemeBrand.DEFAULT -> secondaryLight
        ThemeBrand.BLUE_SKY -> secondaryLight_blueSky
        ThemeBrand.GREEN_FOREST -> secondaryLight_greenForest
        ThemeBrand.RED_ALGAE -> secondaryLight_redAlgae
        ThemeBrand.SUNNY -> secondaryLight_sunny
        else -> secondaryLight
    },
    onSecondary = when(customTheme) {
        ThemeBrand.DEFAULT -> onSecondaryLight
        ThemeBrand.BLUE_SKY -> onSecondaryLight_blueSky
        ThemeBrand.GREEN_FOREST -> onSecondaryLight_greenForest
        ThemeBrand.RED_ALGAE -> onSecondaryLight_redAlgae
        ThemeBrand.SUNNY -> onSecondaryLight_sunny
        else -> onSecondaryLight
    },
    secondaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> secondaryContainerLight
        ThemeBrand.BLUE_SKY -> secondaryContainerLight_blueSky
        ThemeBrand.GREEN_FOREST -> secondaryContainerLight_greenForest
        ThemeBrand.RED_ALGAE -> secondaryContainerLight_redAlgae
        ThemeBrand.SUNNY -> secondaryContainerLight_sunny
        else -> secondaryContainerLight
    },
    onSecondaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> onSecondaryContainerLight
        ThemeBrand.BLUE_SKY -> onSecondaryContainerLight_blueSky
        ThemeBrand.GREEN_FOREST -> onSecondaryContainerLight_greenForest
        ThemeBrand.RED_ALGAE -> onSecondaryContainerLight_redAlgae
        ThemeBrand.SUNNY -> onSecondaryContainerLight_sunny
        else -> onSecondaryContainerLight
    },
    tertiary = when(customTheme) {
        ThemeBrand.DEFAULT -> tertiaryLight
        ThemeBrand.BLUE_SKY -> tertiaryLight_blueSky
        ThemeBrand.GREEN_FOREST -> tertiaryLight_greenForest
        ThemeBrand.RED_ALGAE -> tertiaryLight_redAlgae
        ThemeBrand.SUNNY -> tertiaryLight_sunny
        else -> tertiaryLight
    },
    onTertiary = when(customTheme) {
        ThemeBrand.DEFAULT -> onTertiaryLight
        ThemeBrand.BLUE_SKY -> onTertiaryLight_blueSky
        ThemeBrand.GREEN_FOREST -> onTertiaryLight_greenForest
        ThemeBrand.RED_ALGAE -> onTertiaryLight_redAlgae
        ThemeBrand.SUNNY -> onTertiaryLight_sunny
        else -> onTertiaryLight
    },
    tertiaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> tertiaryContainerLight
        ThemeBrand.BLUE_SKY -> tertiaryContainerLight_blueSky
        ThemeBrand.GREEN_FOREST -> tertiaryContainerLight_greenForest
        ThemeBrand.RED_ALGAE -> tertiaryContainerLight_redAlgae
        ThemeBrand.SUNNY -> tertiaryContainerLight_sunny
        else -> tertiaryContainerLight
    },
    onTertiaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> onTertiaryContainerLight
        ThemeBrand.BLUE_SKY -> onTertiaryContainerLight_blueSky
        ThemeBrand.GREEN_FOREST -> onTertiaryContainerLight_greenForest
        ThemeBrand.RED_ALGAE -> onTertiaryContainerLight_redAlgae
        ThemeBrand.SUNNY -> onTertiaryContainerLight_sunny
        else -> onTertiaryContainerLight
    },
    error = when(customTheme) {
        ThemeBrand.DEFAULT -> errorLight
        ThemeBrand.BLUE_SKY -> errorLight_blueSky
        ThemeBrand.GREEN_FOREST -> errorLight_greenForest
        ThemeBrand.RED_ALGAE -> errorLight_redAlgae
        ThemeBrand.SUNNY -> errorLight_sunny
        else -> errorLight
    },
    onError = when(customTheme) {
        ThemeBrand.DEFAULT -> onErrorLight
        ThemeBrand.BLUE_SKY -> onErrorLight_blueSky
        ThemeBrand.GREEN_FOREST -> onErrorLight_greenForest
        ThemeBrand.RED_ALGAE -> onErrorLight_redAlgae
        ThemeBrand.SUNNY -> onErrorLight_sunny
        else -> onErrorLight
    },
    errorContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> errorContainerLight
        ThemeBrand.BLUE_SKY -> errorContainerLight_blueSky
        ThemeBrand.GREEN_FOREST -> errorContainerLight_greenForest
        ThemeBrand.RED_ALGAE -> errorContainerLight_redAlgae
        ThemeBrand.SUNNY -> errorContainerLight_sunny
        else -> errorContainerLight
    },
    onErrorContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> onErrorContainerLight
        ThemeBrand.BLUE_SKY -> onErrorContainerLight_blueSky
        ThemeBrand.GREEN_FOREST -> onErrorContainerLight_greenForest
        ThemeBrand.RED_ALGAE -> onErrorContainerLight_redAlgae
        ThemeBrand.SUNNY -> onErrorContainerLight_sunny
        else -> onErrorContainerLight
    },
    background = when(customTheme) {
        ThemeBrand.DEFAULT -> backgroundLight
        ThemeBrand.BLUE_SKY -> backgroundLight_blueSky
        ThemeBrand.GREEN_FOREST -> backgroundLight_greenForest
        ThemeBrand.RED_ALGAE -> backgroundLight_redAlgae
        ThemeBrand.SUNNY -> backgroundLight_sunny
        else -> backgroundLight
    },
    onBackground = when(customTheme) {
        ThemeBrand.DEFAULT -> onBackgroundLight
        ThemeBrand.BLUE_SKY -> onBackgroundLight_blueSky
        ThemeBrand.GREEN_FOREST -> onBackgroundLight_greenForest
        ThemeBrand.RED_ALGAE -> onBackgroundLight_redAlgae
        ThemeBrand.SUNNY -> onBackgroundLight_sunny
        else -> onBackgroundLight
    },
    surface = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceLight
        ThemeBrand.BLUE_SKY -> surfaceLight_blueSky
        ThemeBrand.GREEN_FOREST -> surfaceLight_greenForest
        ThemeBrand.RED_ALGAE -> surfaceLight_redAlgae
        ThemeBrand.SUNNY -> surfaceLight_sunny
        else -> surfaceLight
    },
    onSurface = when(customTheme) {
        ThemeBrand.DEFAULT -> onSurfaceLight
        ThemeBrand.BLUE_SKY -> onSurfaceLight_blueSky
        ThemeBrand.GREEN_FOREST -> onSurfaceLight_greenForest
        ThemeBrand.RED_ALGAE -> onSurfaceLight_redAlgae
        ThemeBrand.SUNNY -> onSurfaceLight_sunny
        else -> onSurfaceLight
    },
    surfaceVariant = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceVariantLight
        ThemeBrand.BLUE_SKY -> surfaceVariantLight_blueSky
        ThemeBrand.GREEN_FOREST -> surfaceVariantLight_greenForest
        ThemeBrand.RED_ALGAE -> surfaceVariantLight_redAlgae
        ThemeBrand.SUNNY -> surfaceVariantLight_sunny
        else -> surfaceVariantLight
    },
    onSurfaceVariant = when(customTheme) {
        ThemeBrand.DEFAULT -> onSurfaceVariantLight
        ThemeBrand.BLUE_SKY -> onSurfaceVariantLight_blueSky
        ThemeBrand.GREEN_FOREST -> onSurfaceVariantLight_greenForest
        ThemeBrand.RED_ALGAE -> onSurfaceVariantLight_redAlgae
        ThemeBrand.SUNNY -> onSurfaceVariantLight_sunny
        else -> onSurfaceVariantLight
    },
    outline = when(customTheme) {
        ThemeBrand.DEFAULT -> outlineLight
        ThemeBrand.BLUE_SKY -> outlineLight_blueSky
        ThemeBrand.GREEN_FOREST -> outlineLight_greenForest
        ThemeBrand.RED_ALGAE -> outlineLight_redAlgae
        ThemeBrand.SUNNY -> outlineLight_sunny
        else -> outlineLight
    },
    outlineVariant = when(customTheme) {
        ThemeBrand.DEFAULT -> outlineVariantLight
        ThemeBrand.BLUE_SKY -> outlineVariantLight_blueSky
        ThemeBrand.GREEN_FOREST -> outlineVariantLight_greenForest
        ThemeBrand.RED_ALGAE -> outlineVariantLight_redAlgae
        ThemeBrand.SUNNY -> outlineVariantLight_sunny
        else -> outlineVariantLight
    },
    scrim = when(customTheme) {
        ThemeBrand.DEFAULT -> scrimLight
        ThemeBrand.BLUE_SKY -> scrimLight_blueSky
        ThemeBrand.GREEN_FOREST -> scrimLight_greenForest
        ThemeBrand.RED_ALGAE -> scrimLight_redAlgae
        ThemeBrand.SUNNY -> scrimLight_sunny
        else -> scrimLight
    },
    inverseSurface = when(customTheme) {
        ThemeBrand.DEFAULT -> inverseSurfaceLight
        ThemeBrand.BLUE_SKY -> inverseSurfaceLight_blueSky
        ThemeBrand.GREEN_FOREST -> inverseSurfaceLight_greenForest
        ThemeBrand.RED_ALGAE -> inverseSurfaceLight_redAlgae
        ThemeBrand.SUNNY -> inverseSurfaceLight_sunny
        else -> inverseSurfaceLight
    },
    inverseOnSurface = when(customTheme) {
        ThemeBrand.DEFAULT -> inverseOnSurfaceLight
        ThemeBrand.BLUE_SKY -> inverseOnSurfaceLight_blueSky
        ThemeBrand.GREEN_FOREST -> inverseOnSurfaceLight_greenForest
        ThemeBrand.RED_ALGAE -> inverseOnSurfaceLight_redAlgae
        ThemeBrand.SUNNY -> inverseOnSurfaceLight_sunny
        else -> inverseOnSurfaceLight
    },
    inversePrimary = when(customTheme) {
        ThemeBrand.DEFAULT -> inversePrimaryLight
        ThemeBrand.BLUE_SKY -> inversePrimaryLight_blueSky
        ThemeBrand.GREEN_FOREST -> inversePrimaryLight_greenForest
        ThemeBrand.RED_ALGAE -> inversePrimaryLight_redAlgae
        ThemeBrand.SUNNY -> inversePrimaryLight_sunny
        else -> inversePrimaryLight
    },
    surfaceDim = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceDimLight
        ThemeBrand.BLUE_SKY -> surfaceDimLight_blueSky
        ThemeBrand.GREEN_FOREST -> surfaceDimLight_greenForest
        ThemeBrand.RED_ALGAE -> surfaceDimLight_redAlgae
        ThemeBrand.SUNNY -> surfaceDimLight_sunny
        else -> surfaceDimLight
    },
    surfaceBright = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceBrightLight
        ThemeBrand.BLUE_SKY -> surfaceBrightLight_blueSky
        ThemeBrand.GREEN_FOREST -> surfaceBrightLight_greenForest
        ThemeBrand.RED_ALGAE -> surfaceBrightLight_redAlgae
        ThemeBrand.SUNNY -> surfaceBrightLight_sunny
        else -> surfaceBrightLight
    },
    surfaceContainerLowest = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceContainerLowestLight
        ThemeBrand.BLUE_SKY -> surfaceContainerLowestLight_blueSky
        ThemeBrand.GREEN_FOREST -> surfaceContainerLowestLight_greenForest
        ThemeBrand.RED_ALGAE -> surfaceContainerLowestLight_redAlgae
        ThemeBrand.SUNNY -> surfaceContainerLowestLight_sunny
        else -> surfaceContainerLowestLight
    },
    surfaceContainerLow = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceContainerLowLight
        ThemeBrand.BLUE_SKY -> surfaceContainerLowLight_blueSky
        ThemeBrand.GREEN_FOREST -> surfaceContainerLowLight_greenForest
        ThemeBrand.RED_ALGAE -> surfaceContainerLowLight_redAlgae
        ThemeBrand.SUNNY -> surfaceContainerLowLight_sunny
        else -> surfaceContainerLowLight
    },
    surfaceContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceContainerLight
        ThemeBrand.BLUE_SKY -> surfaceContainerLight_blueSky
        ThemeBrand.GREEN_FOREST -> surfaceContainerLight_greenForest
        ThemeBrand.RED_ALGAE -> surfaceContainerLight_redAlgae
        ThemeBrand.SUNNY -> surfaceContainerLight_sunny
        else -> surfaceContainerLight
    },
    surfaceContainerHigh = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceContainerHighLight
        ThemeBrand.BLUE_SKY -> surfaceContainerHighLight_blueSky
        ThemeBrand.GREEN_FOREST -> surfaceContainerHighLight_greenForest
        ThemeBrand.RED_ALGAE -> surfaceContainerHighLight_redAlgae
        ThemeBrand.SUNNY -> surfaceContainerHighLight_sunny
        else -> surfaceContainerHighLight
    },
    surfaceContainerHighest = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceContainerHighestLight
        ThemeBrand.BLUE_SKY -> surfaceContainerHighestLight_blueSky
        ThemeBrand.GREEN_FOREST -> surfaceContainerHighestLight_greenForest
        ThemeBrand.RED_ALGAE -> surfaceContainerHighestLight_redAlgae
        ThemeBrand.SUNNY -> surfaceContainerHighestLight_sunny
        else -> surfaceContainerHighestLight
    }
)

fun getDarkScheme(customTheme: ThemeBrand? = null) = darkColorScheme(
    primary = when(customTheme) {
        ThemeBrand.DEFAULT -> primaryDark
        ThemeBrand.BLUE_SKY -> primaryDark_blueSky
        ThemeBrand.GREEN_FOREST -> primaryDark_greenForest
        ThemeBrand.RED_ALGAE -> primaryDark_redAlgae
        ThemeBrand.SUNNY -> primaryDark_sunny
        else -> primaryDark
    },
    onPrimary = when(customTheme) {
        ThemeBrand.DEFAULT -> onPrimaryDark
        ThemeBrand.BLUE_SKY -> onPrimaryDark_blueSky
        ThemeBrand.GREEN_FOREST -> onPrimaryDark_greenForest
        ThemeBrand.RED_ALGAE -> onPrimaryDark_redAlgae
        ThemeBrand.SUNNY -> onPrimaryDark_sunny
        else -> onPrimaryDark
    },
    primaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> primaryContainerDark
        ThemeBrand.BLUE_SKY -> primaryContainerDark_blueSky
        ThemeBrand.GREEN_FOREST -> primaryContainerDark_greenForest
        ThemeBrand.RED_ALGAE -> primaryContainerDark_redAlgae
        ThemeBrand.SUNNY -> primaryContainerDark_sunny
        else -> primaryContainerDark
    },
    onPrimaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> onPrimaryContainerDark
        ThemeBrand.BLUE_SKY -> onPrimaryContainerDark_blueSky
        ThemeBrand.GREEN_FOREST -> onPrimaryContainerDark_greenForest
        ThemeBrand.RED_ALGAE -> onPrimaryContainerDark_redAlgae
        ThemeBrand.SUNNY -> onPrimaryContainerDark_sunny
        else -> onPrimaryContainerDark
    },
    secondary = when(customTheme) {
        ThemeBrand.DEFAULT -> secondaryDark
        ThemeBrand.BLUE_SKY -> secondaryDark_blueSky
        ThemeBrand.GREEN_FOREST -> secondaryDark_greenForest
        ThemeBrand.RED_ALGAE -> secondaryDark_redAlgae
        ThemeBrand.SUNNY -> secondaryDark_sunny
        else -> secondaryDark
    },
    onSecondary = when(customTheme) {
        ThemeBrand.DEFAULT -> onSecondaryDark
        ThemeBrand.BLUE_SKY -> onSecondaryDark_blueSky
        ThemeBrand.GREEN_FOREST -> onSecondaryDark_greenForest
        ThemeBrand.RED_ALGAE -> onSecondaryDark_redAlgae
        ThemeBrand.SUNNY -> onSecondaryDark_sunny
        else -> onSecondaryDark
    },
    secondaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> secondaryContainerDark
        ThemeBrand.BLUE_SKY -> secondaryContainerDark_blueSky
        ThemeBrand.GREEN_FOREST -> secondaryContainerDark_greenForest
        ThemeBrand.RED_ALGAE -> secondaryContainerDark_redAlgae
        ThemeBrand.SUNNY -> secondaryContainerDark_sunny
        else -> secondaryContainerDark
    },
    onSecondaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> onSecondaryContainerDark
        ThemeBrand.BLUE_SKY -> onSecondaryContainerDark_blueSky
        ThemeBrand.GREEN_FOREST -> onSecondaryContainerDark_greenForest
        ThemeBrand.RED_ALGAE -> onSecondaryContainerDark_redAlgae
        ThemeBrand.SUNNY -> onSecondaryContainerDark_sunny
        else -> onSecondaryContainerDark
    },
    tertiary = when(customTheme) {
        ThemeBrand.DEFAULT -> tertiaryDark
        ThemeBrand.BLUE_SKY -> tertiaryDark_blueSky
        ThemeBrand.GREEN_FOREST -> tertiaryDark_greenForest
        ThemeBrand.RED_ALGAE -> tertiaryDark_redAlgae
        ThemeBrand.SUNNY -> tertiaryDark_sunny
        else -> tertiaryDark
    },
    onTertiary = when(customTheme) {
        ThemeBrand.DEFAULT -> onTertiaryDark
        ThemeBrand.BLUE_SKY -> onTertiaryDark_blueSky
        ThemeBrand.GREEN_FOREST -> onTertiaryDark_greenForest
        ThemeBrand.RED_ALGAE -> onTertiaryDark_redAlgae
        ThemeBrand.SUNNY -> onTertiaryDark_sunny
        else -> onTertiaryDark
    },
    tertiaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> tertiaryContainerDark
        ThemeBrand.BLUE_SKY -> tertiaryContainerDark_blueSky
        ThemeBrand.GREEN_FOREST -> tertiaryContainerDark_greenForest
        ThemeBrand.RED_ALGAE -> tertiaryContainerDark_redAlgae
        ThemeBrand.SUNNY -> tertiaryContainerDark_sunny
        else -> tertiaryContainerDark
    },
    onTertiaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> onTertiaryContainerDark
        ThemeBrand.BLUE_SKY -> onTertiaryContainerDark_blueSky
        ThemeBrand.GREEN_FOREST -> onTertiaryContainerDark_greenForest
        ThemeBrand.RED_ALGAE -> onTertiaryContainerDark_redAlgae
        ThemeBrand.SUNNY -> onTertiaryContainerDark_sunny
        else -> onTertiaryContainerDark
    },
    error = when(customTheme) {
        ThemeBrand.DEFAULT -> errorDark
        ThemeBrand.BLUE_SKY -> errorDark_blueSky
        ThemeBrand.GREEN_FOREST -> errorDark_greenForest
        ThemeBrand.RED_ALGAE -> errorDark_redAlgae
        ThemeBrand.SUNNY -> errorDark_sunny
        else -> errorDark
    },
    onError = when(customTheme) {
        ThemeBrand.DEFAULT -> onErrorDark
        ThemeBrand.BLUE_SKY -> onErrorDark_blueSky
        ThemeBrand.GREEN_FOREST -> onErrorDark_greenForest
        ThemeBrand.RED_ALGAE -> onErrorDark_redAlgae
        ThemeBrand.SUNNY -> onErrorDark_sunny
        else -> onErrorDark
    },
    errorContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> errorContainerDark
        ThemeBrand.BLUE_SKY -> errorContainerDark_blueSky
        ThemeBrand.GREEN_FOREST -> errorContainerDark_greenForest
        ThemeBrand.RED_ALGAE -> errorContainerDark_redAlgae
        ThemeBrand.SUNNY -> errorContainerDark_sunny
        else -> errorContainerDark
    },
    onErrorContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> onErrorContainerDark
        ThemeBrand.BLUE_SKY -> onErrorContainerDark_blueSky
        ThemeBrand.GREEN_FOREST -> onErrorContainerDark_greenForest
        ThemeBrand.RED_ALGAE -> onErrorContainerDark_redAlgae
        ThemeBrand.SUNNY -> onErrorContainerDark_sunny
        else -> onErrorContainerDark
    },
    background = when(customTheme) {
        ThemeBrand.DEFAULT -> backgroundDark
        ThemeBrand.BLUE_SKY -> backgroundDark_blueSky
        ThemeBrand.GREEN_FOREST -> backgroundDark_greenForest
        ThemeBrand.RED_ALGAE -> backgroundDark_redAlgae
        ThemeBrand.SUNNY -> backgroundDark_sunny
        else -> backgroundDark
    },
    onBackground = when(customTheme) {
        ThemeBrand.DEFAULT -> onBackgroundDark
        ThemeBrand.BLUE_SKY -> onBackgroundDark_blueSky
        ThemeBrand.GREEN_FOREST -> onBackgroundDark_greenForest
        ThemeBrand.RED_ALGAE -> onBackgroundDark_redAlgae
        ThemeBrand.SUNNY -> onBackgroundDark_sunny
        else -> onBackgroundDark
    },
    surface = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceDark
        ThemeBrand.BLUE_SKY -> surfaceDark_blueSky
        ThemeBrand.GREEN_FOREST -> surfaceDark_greenForest
        ThemeBrand.RED_ALGAE -> surfaceDark_redAlgae
        ThemeBrand.SUNNY -> surfaceDark_sunny
        else -> surfaceDark
    },
    onSurface = when(customTheme) {
        ThemeBrand.DEFAULT -> onSurfaceDark
        ThemeBrand.BLUE_SKY -> onSurfaceDark_blueSky
        ThemeBrand.GREEN_FOREST -> onSurfaceDark_greenForest
        ThemeBrand.RED_ALGAE -> onSurfaceDark_redAlgae
        ThemeBrand.SUNNY -> onSurfaceDark_sunny
        else -> onSurfaceDark
    },
    surfaceVariant = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceVariantDark
        ThemeBrand.BLUE_SKY -> surfaceVariantDark_blueSky
        ThemeBrand.GREEN_FOREST -> surfaceVariantDark_greenForest
        ThemeBrand.RED_ALGAE -> surfaceVariantDark_redAlgae
        ThemeBrand.SUNNY -> surfaceVariantDark_sunny
        else -> surfaceVariantDark
    },
    onSurfaceVariant = when(customTheme) {
        ThemeBrand.DEFAULT -> onSurfaceVariantDark
        ThemeBrand.BLUE_SKY -> onSurfaceVariantDark_blueSky
        ThemeBrand.GREEN_FOREST -> onSurfaceVariantDark_greenForest
        ThemeBrand.RED_ALGAE -> onSurfaceVariantDark_redAlgae
        ThemeBrand.SUNNY -> onSurfaceVariantDark_sunny
        else -> onSurfaceVariantDark
    },
    outline = when(customTheme) {
        ThemeBrand.DEFAULT -> outlineDark
        ThemeBrand.BLUE_SKY -> outlineDark_blueSky
        ThemeBrand.GREEN_FOREST -> outlineDark_greenForest
        ThemeBrand.RED_ALGAE -> outlineDark_redAlgae
        ThemeBrand.SUNNY -> outlineDark_sunny
        else -> outlineDark
    },
    outlineVariant = when(customTheme) {
        ThemeBrand.DEFAULT -> outlineVariantDark
        ThemeBrand.BLUE_SKY -> outlineVariantDark_blueSky
        ThemeBrand.GREEN_FOREST -> outlineVariantDark_greenForest
        ThemeBrand.RED_ALGAE -> outlineVariantDark_redAlgae
        ThemeBrand.SUNNY -> outlineVariantDark_sunny
        else -> outlineVariantDark
    },
    scrim = when(customTheme) {
        ThemeBrand.DEFAULT -> scrimDark
        ThemeBrand.BLUE_SKY -> scrimDark_blueSky
        ThemeBrand.GREEN_FOREST -> scrimDark_greenForest
        ThemeBrand.RED_ALGAE -> scrimDark_redAlgae
        ThemeBrand.SUNNY -> scrimDark_sunny
        else -> scrimDark
    },
    inverseSurface = when(customTheme) {
        ThemeBrand.DEFAULT -> inverseSurfaceDark
        ThemeBrand.BLUE_SKY -> inverseSurfaceDark_blueSky
        ThemeBrand.GREEN_FOREST -> inverseSurfaceDark_greenForest
        ThemeBrand.RED_ALGAE -> inverseSurfaceDark_redAlgae
        ThemeBrand.SUNNY -> inverseSurfaceDark_sunny
        else -> inverseSurfaceDark
    },
    inverseOnSurface = when(customTheme) {
        ThemeBrand.DEFAULT -> inverseOnSurfaceDark
        ThemeBrand.BLUE_SKY -> inverseOnSurfaceDark_blueSky
        ThemeBrand.GREEN_FOREST -> inverseOnSurfaceDark_greenForest
        ThemeBrand.RED_ALGAE -> inverseOnSurfaceDark_redAlgae
        ThemeBrand.SUNNY -> inverseOnSurfaceDark_sunny
        else -> inverseOnSurfaceDark
    },
    inversePrimary = when(customTheme) {
        ThemeBrand.DEFAULT -> inversePrimaryDark
        ThemeBrand.BLUE_SKY -> inversePrimaryDark_blueSky
        ThemeBrand.GREEN_FOREST -> inversePrimaryDark_greenForest
        ThemeBrand.RED_ALGAE -> inversePrimaryDark_redAlgae
        ThemeBrand.SUNNY -> inversePrimaryDark_sunny
        else -> inversePrimaryDark
    },
    surfaceDim = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceDimDark
        ThemeBrand.BLUE_SKY -> surfaceDimDark_blueSky
        ThemeBrand.GREEN_FOREST -> surfaceDimDark_greenForest
        ThemeBrand.RED_ALGAE -> surfaceDimDark_redAlgae
        ThemeBrand.SUNNY -> surfaceDimDark_sunny
        else -> surfaceDimDark
    },
    surfaceBright = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceBrightDark
        ThemeBrand.BLUE_SKY -> surfaceBrightDark_blueSky
        ThemeBrand.GREEN_FOREST -> surfaceBrightDark_greenForest
        ThemeBrand.RED_ALGAE -> surfaceBrightDark_redAlgae
        ThemeBrand.SUNNY -> surfaceBrightDark_sunny
        else -> surfaceBrightDark
    },
    surfaceContainerLowest = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceContainerLowestDark
        ThemeBrand.BLUE_SKY -> surfaceContainerLowestDark_blueSky
        ThemeBrand.GREEN_FOREST -> surfaceContainerLowestDark_greenForest
        ThemeBrand.RED_ALGAE -> surfaceContainerLowestDark_redAlgae
        ThemeBrand.SUNNY -> surfaceContainerLowestDark_sunny
        else -> surfaceContainerLowestDark
    },
    surfaceContainerLow = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceContainerLowDark
        ThemeBrand.BLUE_SKY -> surfaceContainerLowDark_blueSky
        ThemeBrand.GREEN_FOREST -> surfaceContainerLowDark_greenForest
        ThemeBrand.RED_ALGAE -> surfaceContainerLowDark_redAlgae
        ThemeBrand.SUNNY -> surfaceContainerLowDark_sunny
        else -> surfaceContainerLowDark
    },
    surfaceContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceContainerDark
        ThemeBrand.BLUE_SKY -> surfaceContainerDark_blueSky
        ThemeBrand.GREEN_FOREST -> surfaceContainerDark_greenForest
        ThemeBrand.RED_ALGAE -> surfaceContainerDark_redAlgae
        ThemeBrand.SUNNY -> surfaceContainerDark_sunny
        else -> surfaceContainerDark
    },
    surfaceContainerHigh = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceContainerHighDark
        ThemeBrand.BLUE_SKY -> surfaceContainerHighDark_blueSky
        ThemeBrand.GREEN_FOREST -> surfaceContainerHighDark_greenForest
        ThemeBrand.RED_ALGAE -> surfaceContainerHighDark_redAlgae
        ThemeBrand.SUNNY -> surfaceContainerHighDark_sunny
        else -> surfaceContainerHighDark
    },
    surfaceContainerHighest = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceContainerHighestDark
        ThemeBrand.BLUE_SKY -> surfaceContainerHighestDark_blueSky
        ThemeBrand.GREEN_FOREST -> surfaceContainerHighestDark_greenForest
        ThemeBrand.RED_ALGAE -> surfaceContainerHighestDark_redAlgae
        ThemeBrand.SUNNY -> surfaceContainerHighestDark_sunny
        else -> surfaceContainerHighestDark
    }
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun VaultDefaultTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    customTheme: ThemeBrand? = ThemeBrand.DEFAULT, // todo add custom themes,
    deviceThemeConfig: DeviceThemeConfig?,
    content: @Composable () -> Unit
) {
    val colorScheme = deviceThemeConfig?.let {
        when {
            dynamicColor && deviceThemeConfig.supportsDynamicTheming() -> {
                if (darkTheme) deviceThemeConfig.getDynamicDarkColorScheme() else deviceThemeConfig.getDynamicLightColorScheme()
            }

            darkTheme -> getDarkScheme(customTheme)
            else -> getLightScheme(customTheme)
        }
    } ?: if (darkTheme) getDarkScheme(customTheme) else getLightScheme(customTheme)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = getTypography(),
        content = content
    )
}

@Composable
fun getAppLogo() = Res.drawable.ic_vault_108_gradient
