package com.thejohnsondev.ui.designsystem.colorscheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.model.settings.ThemeBrand
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import com.thejohnsondev.ui.designsystem.getTypography
import vaultmultiplatform.core.ui.generated.resources.Res
import vaultmultiplatform.core.ui.generated.resources.ic_vault_108_gradient

fun getLightScheme(customTheme: ThemeBrand? = null) = lightColorScheme(
    primary = when(customTheme) {
        ThemeBrand.DEFAULT -> primaryLight
        ThemeBrand.TEAL -> primaryLight_teal
        ThemeBrand.DEEP_FOREST -> primaryLight_deepForest
        ThemeBrand.RED_ALGAE -> primaryLight_redAlgae
        ThemeBrand.SUNNY -> primaryLight_sunny
        ThemeBrand.VIOLET -> primaryLight_violet
        else -> primaryLight
    },
    onPrimary = when(customTheme) {
        ThemeBrand.DEFAULT -> onPrimaryLight
        ThemeBrand.TEAL -> onPrimaryLight_teal
        ThemeBrand.DEEP_FOREST -> onPrimaryLight_deepForest
        ThemeBrand.RED_ALGAE -> onPrimaryLight_redAlgae
        ThemeBrand.SUNNY -> onPrimaryLight_sunny
        ThemeBrand.VIOLET -> onPrimaryLight_violet
        else -> onPrimaryLight
    },
    primaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> primaryContainerLight
        ThemeBrand.TEAL -> primaryContainerLight_teal
        ThemeBrand.DEEP_FOREST -> primaryContainerLight_deepForest
        ThemeBrand.RED_ALGAE -> primaryContainerLight_redAlgae
        ThemeBrand.SUNNY -> primaryContainerLight_sunny
        ThemeBrand.VIOLET -> primaryContainerLight_violet
        else -> primaryContainerLight
    },
    onPrimaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> onPrimaryContainerLight
        ThemeBrand.TEAL -> onPrimaryContainerLight_teal
        ThemeBrand.DEEP_FOREST -> onPrimaryContainerLight_deepForest
        ThemeBrand.RED_ALGAE -> onPrimaryContainerLight_redAlgae
        ThemeBrand.SUNNY -> onPrimaryContainerLight_sunny
        ThemeBrand.VIOLET -> onPrimaryContainerLight_violet
        else -> onPrimaryContainerLight
    },
    secondary = when(customTheme) {
        ThemeBrand.DEFAULT -> secondaryLight
        ThemeBrand.TEAL -> secondaryLight_teal
        ThemeBrand.DEEP_FOREST -> secondaryLight_deepForest
        ThemeBrand.RED_ALGAE -> secondaryLight_redAlgae
        ThemeBrand.SUNNY -> secondaryLight_sunny
        ThemeBrand.VIOLET -> secondaryLight_violet
        else -> secondaryLight
    },
    onSecondary = when(customTheme) {
        ThemeBrand.DEFAULT -> onSecondaryLight
        ThemeBrand.TEAL -> onSecondaryLight_teal
        ThemeBrand.DEEP_FOREST -> onSecondaryLight_deepForest
        ThemeBrand.RED_ALGAE -> onSecondaryLight_redAlgae
        ThemeBrand.SUNNY -> onSecondaryLight_sunny
        ThemeBrand.VIOLET -> onSecondaryLight_violet
        else -> onSecondaryLight
    },
    secondaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> secondaryContainerLight
        ThemeBrand.TEAL -> secondaryContainerLight_teal
        ThemeBrand.DEEP_FOREST -> secondaryContainerLight_deepForest
        ThemeBrand.RED_ALGAE -> secondaryContainerLight_redAlgae
        ThemeBrand.SUNNY -> secondaryContainerLight_sunny
        ThemeBrand.VIOLET -> secondaryContainerLight_violet
        else -> secondaryContainerLight
    },
    onSecondaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> onSecondaryContainerLight
        ThemeBrand.TEAL -> onSecondaryContainerLight_teal
        ThemeBrand.DEEP_FOREST -> onSecondaryContainerLight_deepForest
        ThemeBrand.RED_ALGAE -> onSecondaryContainerLight_redAlgae
        ThemeBrand.SUNNY -> onSecondaryContainerLight_sunny
        ThemeBrand.VIOLET -> onSecondaryContainerLight_violet
        else -> onSecondaryContainerLight
    },
    tertiary = when(customTheme) {
        ThemeBrand.DEFAULT -> tertiaryLight
        ThemeBrand.TEAL -> tertiaryLight_teal
        ThemeBrand.DEEP_FOREST -> tertiaryLight_deepForest
        ThemeBrand.RED_ALGAE -> tertiaryLight_redAlgae
        ThemeBrand.SUNNY -> tertiaryLight_sunny
        ThemeBrand.VIOLET -> tertiaryLight_violet
        else -> tertiaryLight
    },
    onTertiary = when(customTheme) {
        ThemeBrand.DEFAULT -> onTertiaryLight
        ThemeBrand.TEAL -> onTertiaryLight_teal
        ThemeBrand.DEEP_FOREST -> onTertiaryLight_deepForest
        ThemeBrand.RED_ALGAE -> onTertiaryLight_redAlgae
        ThemeBrand.SUNNY -> onTertiaryLight_sunny
        ThemeBrand.VIOLET -> onTertiaryLight_violet
        else -> onTertiaryLight
    },
    tertiaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> tertiaryContainerLight
        ThemeBrand.TEAL -> tertiaryContainerLight_teal
        ThemeBrand.DEEP_FOREST -> tertiaryContainerLight_deepForest
        ThemeBrand.RED_ALGAE -> tertiaryContainerLight_redAlgae
        ThemeBrand.SUNNY -> tertiaryContainerLight_sunny
        ThemeBrand.VIOLET -> tertiaryContainerLight_violet
        else -> tertiaryContainerLight
    },
    onTertiaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> onTertiaryContainerLight
        ThemeBrand.TEAL -> onTertiaryContainerLight_teal
        ThemeBrand.DEEP_FOREST -> onTertiaryContainerLight_deepForest
        ThemeBrand.RED_ALGAE -> onTertiaryContainerLight_redAlgae
        ThemeBrand.SUNNY -> onTertiaryContainerLight_sunny
        ThemeBrand.VIOLET -> onTertiaryContainerLight_violet
        else -> onTertiaryContainerLight
    },
    error = when(customTheme) {
        ThemeBrand.DEFAULT -> errorLight
        ThemeBrand.TEAL -> errorLight_teal
        ThemeBrand.DEEP_FOREST -> errorLight_deepForest
        ThemeBrand.RED_ALGAE -> errorLight_redAlgae
        ThemeBrand.SUNNY -> errorLight_sunny
        ThemeBrand.VIOLET -> errorLight_violet
        else -> errorLight
    },
    onError = when(customTheme) {
        ThemeBrand.DEFAULT -> onErrorLight
        ThemeBrand.TEAL -> onErrorLight_teal
        ThemeBrand.DEEP_FOREST -> onErrorLight_deepForest
        ThemeBrand.RED_ALGAE -> onErrorLight_redAlgae
        ThemeBrand.SUNNY -> onErrorLight_sunny
        ThemeBrand.VIOLET -> onErrorLight_violet
        else -> onErrorLight
    },
    errorContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> errorContainerLight
        ThemeBrand.TEAL -> errorContainerLight_teal
        ThemeBrand.DEEP_FOREST -> errorContainerLight_deepForest
        ThemeBrand.RED_ALGAE -> errorContainerLight_redAlgae
        ThemeBrand.SUNNY -> errorContainerLight_sunny
        ThemeBrand.VIOLET -> errorContainerLight_violet
        else -> errorContainerLight
    },
    onErrorContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> onErrorContainerLight
        ThemeBrand.TEAL -> onErrorContainerLight_teal
        ThemeBrand.DEEP_FOREST -> onErrorContainerLight_deepForest
        ThemeBrand.RED_ALGAE -> onErrorContainerLight_redAlgae
        ThemeBrand.SUNNY -> onErrorContainerLight_sunny
        ThemeBrand.VIOLET -> onErrorContainerLight_violet
        else -> onErrorContainerLight
    },
    background = when(customTheme) {
        ThemeBrand.DEFAULT -> backgroundLight
        ThemeBrand.TEAL -> backgroundLight_teal
        ThemeBrand.DEEP_FOREST -> backgroundLight_deepForest
        ThemeBrand.RED_ALGAE -> backgroundLight_redAlgae
        ThemeBrand.SUNNY -> backgroundLight_sunny
        ThemeBrand.VIOLET -> backgroundLight_violet
        else -> backgroundLight
    },
    onBackground = when(customTheme) {
        ThemeBrand.DEFAULT -> onBackgroundLight
        ThemeBrand.TEAL -> onBackgroundLight_teal
        ThemeBrand.DEEP_FOREST -> onBackgroundLight_deepForest
        ThemeBrand.RED_ALGAE -> onBackgroundLight_redAlgae
        ThemeBrand.SUNNY -> onBackgroundLight_sunny
        ThemeBrand.VIOLET -> onBackgroundLight_violet
        else -> onBackgroundLight
    },
    surface = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceLight
        ThemeBrand.TEAL -> surfaceLight_teal
        ThemeBrand.DEEP_FOREST -> surfaceLight_deepForest
        ThemeBrand.RED_ALGAE -> surfaceLight_redAlgae
        ThemeBrand.SUNNY -> surfaceLight_sunny
        ThemeBrand.VIOLET -> surfaceLight_violet
        else -> surfaceLight
    },
    onSurface = when(customTheme) {
        ThemeBrand.DEFAULT -> onSurfaceLight
        ThemeBrand.TEAL -> onSurfaceLight_teal
        ThemeBrand.DEEP_FOREST -> onSurfaceLight_deepForest
        ThemeBrand.RED_ALGAE -> onSurfaceLight_redAlgae
        ThemeBrand.SUNNY -> onSurfaceLight_sunny
        ThemeBrand.VIOLET -> onSurfaceLight_violet
        else -> onSurfaceLight
    },
    surfaceVariant = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceVariantLight
        ThemeBrand.TEAL -> surfaceVariantLight_teal
        ThemeBrand.DEEP_FOREST -> surfaceVariantLight_deepForest
        ThemeBrand.RED_ALGAE -> surfaceVariantLight_redAlgae
        ThemeBrand.SUNNY -> surfaceVariantLight_sunny
        ThemeBrand.VIOLET -> surfaceVariantLight_violet
        else -> surfaceVariantLight
    },
    onSurfaceVariant = when(customTheme) {
        ThemeBrand.DEFAULT -> onSurfaceVariantLight
        ThemeBrand.TEAL -> onSurfaceVariantLight_teal
        ThemeBrand.DEEP_FOREST -> onSurfaceVariantLight_deepForest
        ThemeBrand.RED_ALGAE -> onSurfaceVariantLight_redAlgae
        ThemeBrand.SUNNY -> onSurfaceVariantLight_sunny
        ThemeBrand.VIOLET -> onSurfaceVariantLight_violet
        else -> onSurfaceVariantLight
    },
    outline = when(customTheme) {
        ThemeBrand.DEFAULT -> outlineLight
        ThemeBrand.TEAL -> outlineLight_teal
        ThemeBrand.DEEP_FOREST -> outlineLight_deepForest
        ThemeBrand.RED_ALGAE -> outlineLight_redAlgae
        ThemeBrand.SUNNY -> outlineLight_sunny
        ThemeBrand.VIOLET -> outlineLight_violet
        else -> outlineLight
    },
    outlineVariant = when(customTheme) {
        ThemeBrand.DEFAULT -> outlineVariantLight
        ThemeBrand.TEAL -> outlineVariantLight_teal
        ThemeBrand.DEEP_FOREST -> outlineVariantLight_deepForest
        ThemeBrand.RED_ALGAE -> outlineVariantLight_redAlgae
        ThemeBrand.SUNNY -> outlineVariantLight_sunny
        ThemeBrand.VIOLET -> outlineVariantLight_violet
        else -> outlineVariantLight
    },
    scrim = when(customTheme) {
        ThemeBrand.DEFAULT -> scrimLight
        ThemeBrand.TEAL -> scrimLight_teal
        ThemeBrand.DEEP_FOREST -> scrimLight_deepForest
        ThemeBrand.RED_ALGAE -> scrimLight_redAlgae
        ThemeBrand.SUNNY -> scrimLight_sunny
        ThemeBrand.VIOLET -> scrimLight_violet
        else -> scrimLight
    },
    inverseSurface = when(customTheme) {
        ThemeBrand.DEFAULT -> inverseSurfaceLight
        ThemeBrand.TEAL -> inverseSurfaceLight_teal
        ThemeBrand.DEEP_FOREST -> inverseSurfaceLight_deepForest
        ThemeBrand.RED_ALGAE -> inverseSurfaceLight_redAlgae
        ThemeBrand.SUNNY -> inverseSurfaceLight_sunny
        ThemeBrand.VIOLET -> inverseSurfaceLight_violet
        else -> inverseSurfaceLight
    },
    inverseOnSurface = when(customTheme) {
        ThemeBrand.DEFAULT -> inverseOnSurfaceLight
        ThemeBrand.TEAL -> inverseOnSurfaceLight_teal
        ThemeBrand.DEEP_FOREST -> inverseOnSurfaceLight_deepForest
        ThemeBrand.RED_ALGAE -> inverseOnSurfaceLight_redAlgae
        ThemeBrand.SUNNY -> inverseOnSurfaceLight_sunny
        ThemeBrand.VIOLET -> inverseOnSurfaceLight_violet
        else -> inverseOnSurfaceLight
    },
    inversePrimary = when(customTheme) {
        ThemeBrand.DEFAULT -> inversePrimaryLight
        ThemeBrand.TEAL -> inversePrimaryLight_teal
        ThemeBrand.DEEP_FOREST -> inversePrimaryLight_deepForest
        ThemeBrand.RED_ALGAE -> inversePrimaryLight_redAlgae
        ThemeBrand.SUNNY -> inversePrimaryLight_sunny
        ThemeBrand.VIOLET -> inversePrimaryLight_violet
        else -> inversePrimaryLight
    },
    surfaceDim = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceDimLight
        ThemeBrand.TEAL -> surfaceDimLight_teal
        ThemeBrand.DEEP_FOREST -> surfaceDimLight_deepForest
        ThemeBrand.RED_ALGAE -> surfaceDimLight_redAlgae
        ThemeBrand.SUNNY -> surfaceDimLight_sunny
        ThemeBrand.VIOLET -> surfaceDimLight_violet
        else -> surfaceDimLight
    },
    surfaceBright = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceBrightLight
        ThemeBrand.TEAL -> surfaceBrightLight_teal
        ThemeBrand.DEEP_FOREST -> surfaceBrightLight_deepForest
        ThemeBrand.RED_ALGAE -> surfaceBrightLight_redAlgae
        ThemeBrand.SUNNY -> surfaceBrightLight_sunny
        ThemeBrand.VIOLET -> surfaceBrightLight_violet
        else -> surfaceBrightLight
    },
    surfaceContainerLowest = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceContainerLowestLight
        ThemeBrand.TEAL -> surfaceContainerLowestLight_teal
        ThemeBrand.DEEP_FOREST -> surfaceContainerLowestLight_deepForest
        ThemeBrand.RED_ALGAE -> surfaceContainerLowestLight_redAlgae
        ThemeBrand.SUNNY -> surfaceContainerLowestLight_sunny
        ThemeBrand.VIOLET -> surfaceContainerLowestLight_violet
        else -> surfaceContainerLowestLight
    },
    surfaceContainerLow = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceContainerLowLight
        ThemeBrand.TEAL -> surfaceContainerLowLight_teal
        ThemeBrand.DEEP_FOREST -> surfaceContainerLowLight_deepForest
        ThemeBrand.RED_ALGAE -> surfaceContainerLowLight_redAlgae
        ThemeBrand.SUNNY -> surfaceContainerLowLight_sunny
        ThemeBrand.VIOLET -> surfaceContainerLowLight_violet
        else -> surfaceContainerLowLight
    },
    surfaceContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceContainerLight
        ThemeBrand.TEAL -> surfaceContainerLight_teal
        ThemeBrand.DEEP_FOREST -> surfaceContainerLight_deepForest
        ThemeBrand.RED_ALGAE -> surfaceContainerLight_redAlgae
        ThemeBrand.SUNNY -> surfaceContainerLight_sunny
        ThemeBrand.VIOLET -> surfaceContainerLight_violet
        else -> surfaceContainerLight
    },
    surfaceContainerHigh = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceContainerHighLight
        ThemeBrand.TEAL -> surfaceContainerHighLight_teal
        ThemeBrand.DEEP_FOREST -> surfaceContainerHighLight_deepForest
        ThemeBrand.RED_ALGAE -> surfaceContainerHighLight_redAlgae
        ThemeBrand.SUNNY -> surfaceContainerHighLight_sunny
        ThemeBrand.VIOLET -> surfaceContainerHighLight_violet
        else -> surfaceContainerHighLight
    },
    surfaceContainerHighest = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceContainerHighestLight
        ThemeBrand.TEAL -> surfaceContainerHighestLight_teal
        ThemeBrand.DEEP_FOREST -> surfaceContainerHighestLight_deepForest
        ThemeBrand.RED_ALGAE -> surfaceContainerHighestLight_redAlgae
        ThemeBrand.SUNNY -> surfaceContainerHighestLight_sunny
        ThemeBrand.VIOLET -> surfaceContainerHighestLight_violet
        else -> surfaceContainerHighestLight
    }
)

fun getDarkScheme(customTheme: ThemeBrand? = null) = darkColorScheme(
    primary = when(customTheme) {
        ThemeBrand.DEFAULT -> primaryDark
        ThemeBrand.TEAL -> primaryDark_teal
        ThemeBrand.DEEP_FOREST -> primaryDark_deepForest
        ThemeBrand.RED_ALGAE -> primaryDark_redAlgae
        ThemeBrand.SUNNY -> primaryDark_sunny
        ThemeBrand.VIOLET -> primaryDark_violet
        else -> primaryDark
    },
    onPrimary = when(customTheme) {
        ThemeBrand.DEFAULT -> onPrimaryDark
        ThemeBrand.TEAL -> onPrimaryDark_teal
        ThemeBrand.DEEP_FOREST -> onPrimaryDark_deepForest
        ThemeBrand.RED_ALGAE -> onPrimaryDark_redAlgae
        ThemeBrand.SUNNY -> onPrimaryDark_sunny
        ThemeBrand.VIOLET -> onPrimaryDark_violet
        else -> onPrimaryDark
    },
    primaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> primaryContainerDark
        ThemeBrand.TEAL -> primaryContainerDark_teal
        ThemeBrand.DEEP_FOREST -> primaryContainerDark_deepForest
        ThemeBrand.RED_ALGAE -> primaryContainerDark_redAlgae
        ThemeBrand.SUNNY -> primaryContainerDark_sunny
        ThemeBrand.VIOLET -> primaryContainerDark_violet
        else -> primaryContainerDark
    },
    onPrimaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> onPrimaryContainerDark
        ThemeBrand.TEAL -> onPrimaryContainerDark_teal
        ThemeBrand.DEEP_FOREST -> onPrimaryContainerDark_deepForest
        ThemeBrand.RED_ALGAE -> onPrimaryContainerDark_redAlgae
        ThemeBrand.SUNNY -> onPrimaryContainerDark_sunny
        ThemeBrand.VIOLET -> onPrimaryContainerDark_violet
        else -> onPrimaryContainerDark
    },
    secondary = when(customTheme) {
        ThemeBrand.DEFAULT -> secondaryDark
        ThemeBrand.TEAL -> secondaryDark_teal
        ThemeBrand.DEEP_FOREST -> secondaryDark_deepForest
        ThemeBrand.RED_ALGAE -> secondaryDark_redAlgae
        ThemeBrand.SUNNY -> secondaryDark_sunny
        ThemeBrand.VIOLET -> secondaryDark_violet
        else -> secondaryDark
    },
    onSecondary = when(customTheme) {
        ThemeBrand.DEFAULT -> onSecondaryDark
        ThemeBrand.TEAL -> onSecondaryDark_teal
        ThemeBrand.DEEP_FOREST -> onSecondaryDark_deepForest
        ThemeBrand.RED_ALGAE -> onSecondaryDark_redAlgae
        ThemeBrand.SUNNY -> onSecondaryDark_sunny
        ThemeBrand.VIOLET -> onSecondaryDark_violet
        else -> onSecondaryDark
    },
    secondaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> secondaryContainerDark
        ThemeBrand.TEAL -> secondaryContainerDark_teal
        ThemeBrand.DEEP_FOREST -> secondaryContainerDark_deepForest
        ThemeBrand.RED_ALGAE -> secondaryContainerDark_redAlgae
        ThemeBrand.SUNNY -> secondaryContainerDark_sunny
        ThemeBrand.VIOLET -> secondaryContainerDark_violet
        else -> secondaryContainerDark
    },
    onSecondaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> onSecondaryContainerDark
        ThemeBrand.TEAL -> onSecondaryContainerDark_teal
        ThemeBrand.DEEP_FOREST -> onSecondaryContainerDark_deepForest
        ThemeBrand.RED_ALGAE -> onSecondaryContainerDark_redAlgae
        ThemeBrand.SUNNY -> onSecondaryContainerDark_sunny
        ThemeBrand.VIOLET -> onSecondaryContainerDark_violet
        else -> onSecondaryContainerDark
    },
    tertiary = when(customTheme) {
        ThemeBrand.DEFAULT -> tertiaryDark
        ThemeBrand.TEAL -> tertiaryDark_teal
        ThemeBrand.DEEP_FOREST -> tertiaryDark_deepForest
        ThemeBrand.RED_ALGAE -> tertiaryDark_redAlgae
        ThemeBrand.SUNNY -> tertiaryDark_sunny
        ThemeBrand.VIOLET -> tertiaryDark_violet
        else -> tertiaryDark
    },
    onTertiary = when(customTheme) {
        ThemeBrand.DEFAULT -> onTertiaryDark
        ThemeBrand.TEAL -> onTertiaryDark_teal
        ThemeBrand.DEEP_FOREST -> onTertiaryDark_deepForest
        ThemeBrand.RED_ALGAE -> onTertiaryDark_redAlgae
        ThemeBrand.SUNNY -> onTertiaryDark_sunny
        ThemeBrand.VIOLET -> onTertiaryDark_violet
        else -> onTertiaryDark
    },
    tertiaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> tertiaryContainerDark
        ThemeBrand.TEAL -> tertiaryContainerDark_teal
        ThemeBrand.DEEP_FOREST -> tertiaryContainerDark_deepForest
        ThemeBrand.RED_ALGAE -> tertiaryContainerDark_redAlgae
        ThemeBrand.SUNNY -> tertiaryContainerDark_sunny
        ThemeBrand.VIOLET -> tertiaryContainerDark_violet
        else -> tertiaryContainerDark
    },
    onTertiaryContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> onTertiaryContainerDark
        ThemeBrand.TEAL -> onTertiaryContainerDark_teal
        ThemeBrand.DEEP_FOREST -> onTertiaryContainerDark_deepForest
        ThemeBrand.RED_ALGAE -> onTertiaryContainerDark_redAlgae
        ThemeBrand.SUNNY -> onTertiaryContainerDark_sunny
        ThemeBrand.VIOLET -> onTertiaryContainerDark_violet
        else -> onTertiaryContainerDark
    },
    error = when(customTheme) {
        ThemeBrand.DEFAULT -> errorDark
        ThemeBrand.TEAL -> errorDark_teal
        ThemeBrand.DEEP_FOREST -> errorDark_deepForest
        ThemeBrand.RED_ALGAE -> errorDark_redAlgae
        ThemeBrand.SUNNY -> errorDark_sunny
        ThemeBrand.VIOLET -> errorDark_violet
        else -> errorDark
    },
    onError = when(customTheme) {
        ThemeBrand.DEFAULT -> onErrorDark
        ThemeBrand.TEAL -> onErrorDark_teal
        ThemeBrand.DEEP_FOREST -> onErrorDark_deepForest
        ThemeBrand.RED_ALGAE -> onErrorDark_redAlgae
        ThemeBrand.SUNNY -> onErrorDark_sunny
        ThemeBrand.VIOLET -> onErrorDark_violet
        else -> onErrorDark
    },
    errorContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> errorContainerDark
        ThemeBrand.TEAL -> errorContainerDark_teal
        ThemeBrand.DEEP_FOREST -> errorContainerDark_deepForest
        ThemeBrand.RED_ALGAE -> errorContainerDark_redAlgae
        ThemeBrand.SUNNY -> errorContainerDark_sunny
        ThemeBrand.VIOLET -> errorContainerDark_violet
        else -> errorContainerDark
    },
    onErrorContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> onErrorContainerDark
        ThemeBrand.TEAL -> onErrorContainerDark_teal
        ThemeBrand.DEEP_FOREST -> onErrorContainerDark_deepForest
        ThemeBrand.RED_ALGAE -> onErrorContainerDark_redAlgae
        ThemeBrand.SUNNY -> onErrorContainerDark_sunny
        ThemeBrand.VIOLET -> onErrorContainerDark_violet
        else -> onErrorContainerDark
    },
    background = when(customTheme) {
        ThemeBrand.DEFAULT -> backgroundDark
        ThemeBrand.TEAL -> backgroundDark_teal
        ThemeBrand.DEEP_FOREST -> backgroundDark_deepForest
        ThemeBrand.RED_ALGAE -> backgroundDark_redAlgae
        ThemeBrand.SUNNY -> backgroundDark_sunny
        ThemeBrand.VIOLET -> backgroundDark_violet
        else -> backgroundDark
    },
    onBackground = when(customTheme) {
        ThemeBrand.DEFAULT -> onBackgroundDark
        ThemeBrand.TEAL -> onBackgroundDark_teal
        ThemeBrand.DEEP_FOREST -> onBackgroundDark_deepForest
        ThemeBrand.RED_ALGAE -> onBackgroundDark_redAlgae
        ThemeBrand.SUNNY -> onBackgroundDark_sunny
        ThemeBrand.VIOLET -> onBackgroundDark_violet
        else -> onBackgroundDark
    },
    surface = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceDark
        ThemeBrand.TEAL -> surfaceDark_teal
        ThemeBrand.DEEP_FOREST -> surfaceDark_deepForest
        ThemeBrand.RED_ALGAE -> surfaceDark_redAlgae
        ThemeBrand.SUNNY -> surfaceDark_sunny
        ThemeBrand.VIOLET -> surfaceDark_violet
        else -> surfaceDark
    },
    onSurface = when(customTheme) {
        ThemeBrand.DEFAULT -> onSurfaceDark
        ThemeBrand.TEAL -> onSurfaceDark_teal
        ThemeBrand.DEEP_FOREST -> onSurfaceDark_deepForest
        ThemeBrand.RED_ALGAE -> onSurfaceDark_redAlgae
        ThemeBrand.SUNNY -> onSurfaceDark_sunny
        ThemeBrand.VIOLET -> onSurfaceDark_violet
        else -> onSurfaceDark
    },
    surfaceVariant = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceVariantDark
        ThemeBrand.TEAL -> surfaceVariantDark_teal
        ThemeBrand.DEEP_FOREST -> surfaceVariantDark_deepForest
        ThemeBrand.RED_ALGAE -> surfaceVariantDark_redAlgae
        ThemeBrand.SUNNY -> surfaceVariantDark_sunny
        ThemeBrand.VIOLET -> surfaceVariantDark_violet
        else -> surfaceVariantDark
    },
    onSurfaceVariant = when(customTheme) {
        ThemeBrand.DEFAULT -> onSurfaceVariantDark
        ThemeBrand.TEAL -> onSurfaceVariantDark_teal
        ThemeBrand.DEEP_FOREST -> onSurfaceVariantDark_deepForest
        ThemeBrand.RED_ALGAE -> onSurfaceVariantDark_redAlgae
        ThemeBrand.SUNNY -> onSurfaceVariantDark_sunny
        ThemeBrand.VIOLET -> onSurfaceVariantDark_violet
        else -> onSurfaceVariantDark
    },
    outline = when(customTheme) {
        ThemeBrand.DEFAULT -> outlineDark
        ThemeBrand.TEAL -> outlineDark_teal
        ThemeBrand.DEEP_FOREST -> outlineDark_deepForest
        ThemeBrand.RED_ALGAE -> outlineDark_redAlgae
        ThemeBrand.SUNNY -> outlineDark_sunny
        ThemeBrand.VIOLET -> outlineDark_violet
        else -> outlineDark
    },
    outlineVariant = when(customTheme) {
        ThemeBrand.DEFAULT -> outlineVariantDark
        ThemeBrand.TEAL -> outlineVariantDark_teal
        ThemeBrand.DEEP_FOREST -> outlineVariantDark_deepForest
        ThemeBrand.RED_ALGAE -> outlineVariantDark_redAlgae
        ThemeBrand.SUNNY -> outlineVariantDark_sunny
        ThemeBrand.VIOLET -> outlineVariantDark_violet
        else -> outlineVariantDark
    },
    scrim = when(customTheme) {
        ThemeBrand.DEFAULT -> scrimDark
        ThemeBrand.TEAL -> scrimDark_teal
        ThemeBrand.DEEP_FOREST -> scrimDark_deepForest
        ThemeBrand.RED_ALGAE -> scrimDark_redAlgae
        ThemeBrand.SUNNY -> scrimDark_sunny
        ThemeBrand.VIOLET -> scrimDark_violet
        else -> scrimDark
    },
    inverseSurface = when(customTheme) {
        ThemeBrand.DEFAULT -> inverseSurfaceDark
        ThemeBrand.TEAL -> inverseSurfaceDark_teal
        ThemeBrand.DEEP_FOREST -> inverseSurfaceDark_deepForest
        ThemeBrand.RED_ALGAE -> inverseSurfaceDark_redAlgae
        ThemeBrand.SUNNY -> inverseSurfaceDark_sunny
        ThemeBrand.VIOLET -> inverseSurfaceDark_violet
        else -> inverseSurfaceDark
    },
    inverseOnSurface = when(customTheme) {
        ThemeBrand.DEFAULT -> inverseOnSurfaceDark
        ThemeBrand.TEAL -> inverseOnSurfaceDark_teal
        ThemeBrand.DEEP_FOREST -> inverseOnSurfaceDark_deepForest
        ThemeBrand.RED_ALGAE -> inverseOnSurfaceDark_redAlgae
        ThemeBrand.SUNNY -> inverseOnSurfaceDark_sunny
        ThemeBrand.VIOLET -> inverseOnSurfaceDark_violet
        else -> inverseOnSurfaceDark
    },
    inversePrimary = when(customTheme) {
        ThemeBrand.DEFAULT -> inversePrimaryDark
        ThemeBrand.TEAL -> inversePrimaryDark_teal
        ThemeBrand.DEEP_FOREST -> inversePrimaryDark_deepForest
        ThemeBrand.RED_ALGAE -> inversePrimaryDark_redAlgae
        ThemeBrand.SUNNY -> inversePrimaryDark_sunny
        ThemeBrand.VIOLET -> inversePrimaryDark_violet
        else -> inversePrimaryDark
    },
    surfaceDim = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceDimDark
        ThemeBrand.TEAL -> surfaceDimDark_teal
        ThemeBrand.DEEP_FOREST -> surfaceDimDark_deepForest
        ThemeBrand.RED_ALGAE -> surfaceDimDark_redAlgae
        ThemeBrand.SUNNY -> surfaceDimDark_sunny
        ThemeBrand.VIOLET -> surfaceDimDark_violet
        else -> surfaceDimDark
    },
    surfaceBright = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceBrightDark
        ThemeBrand.TEAL -> surfaceBrightDark_teal
        ThemeBrand.DEEP_FOREST -> surfaceBrightDark_deepForest
        ThemeBrand.RED_ALGAE -> surfaceBrightDark_redAlgae
        ThemeBrand.SUNNY -> surfaceBrightDark_sunny
        ThemeBrand.VIOLET -> surfaceBrightDark_violet
        else -> surfaceBrightDark
    },
    surfaceContainerLowest = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceContainerLowestDark
        ThemeBrand.TEAL -> surfaceContainerLowestDark_teal
        ThemeBrand.DEEP_FOREST -> surfaceContainerLowestDark_deepForest
        ThemeBrand.RED_ALGAE -> surfaceContainerLowestDark_redAlgae
        ThemeBrand.SUNNY -> surfaceContainerLowestDark_sunny
        ThemeBrand.VIOLET -> surfaceContainerLowestDark_violet
        else -> surfaceContainerLowestDark
    },
    surfaceContainerLow = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceContainerLowDark
        ThemeBrand.TEAL -> surfaceContainerLowDark_teal
        ThemeBrand.DEEP_FOREST -> surfaceContainerLowDark_deepForest
        ThemeBrand.RED_ALGAE -> surfaceContainerLowDark_redAlgae
        ThemeBrand.SUNNY -> surfaceContainerLowDark_sunny
        ThemeBrand.VIOLET -> surfaceContainerLowDark_violet
        else -> surfaceContainerLowDark
    },
    surfaceContainer = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceContainerDark
        ThemeBrand.TEAL -> surfaceContainerDark_teal
        ThemeBrand.DEEP_FOREST -> surfaceContainerDark_deepForest
        ThemeBrand.RED_ALGAE -> surfaceContainerDark_redAlgae
        ThemeBrand.SUNNY -> surfaceContainerDark_sunny
        ThemeBrand.VIOLET -> surfaceContainerDark_violet
        else -> surfaceContainerDark
    },
    surfaceContainerHigh = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceContainerHighDark
        ThemeBrand.TEAL -> surfaceContainerHighDark_teal
        ThemeBrand.DEEP_FOREST -> surfaceContainerHighDark_deepForest
        ThemeBrand.RED_ALGAE -> surfaceContainerHighDark_redAlgae
        ThemeBrand.SUNNY -> surfaceContainerHighDark_sunny
        ThemeBrand.VIOLET -> surfaceContainerHighDark_violet
        else -> surfaceContainerHighDark
    },
    surfaceContainerHighest = when(customTheme) {
        ThemeBrand.DEFAULT -> surfaceContainerHighestDark
        ThemeBrand.TEAL -> surfaceContainerHighestDark_teal
        ThemeBrand.DEEP_FOREST -> surfaceContainerHighestDark_deepForest
        ThemeBrand.RED_ALGAE -> surfaceContainerHighestDark_redAlgae
        ThemeBrand.SUNNY -> surfaceContainerHighestDark_sunny
        ThemeBrand.VIOLET -> surfaceContainerHighestDark_violet
        else -> surfaceContainerHighestDark
    }
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
