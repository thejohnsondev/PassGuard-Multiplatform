package com.thejohnsondev.ui.designsystem

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import vaultmultiplatform.core.ui.generated.resources.AlegreyaSans_Black
import vaultmultiplatform.core.ui.generated.resources.AlegreyaSans_Bold
import vaultmultiplatform.core.ui.generated.resources.AlegreyaSans_Italic
import vaultmultiplatform.core.ui.generated.resources.AlegreyaSans_Light
import vaultmultiplatform.core.ui.generated.resources.AlegreyaSans_Medium
import vaultmultiplatform.core.ui.generated.resources.AlegreyaSans_Regular
import vaultmultiplatform.core.ui.generated.resources.AlegreyaSans_Thin
import vaultmultiplatform.core.ui.generated.resources.Outfit_Black
import vaultmultiplatform.core.ui.generated.resources.Outfit_Bold
import vaultmultiplatform.core.ui.generated.resources.Outfit_Light
import vaultmultiplatform.core.ui.generated.resources.Outfit_Medium
import vaultmultiplatform.core.ui.generated.resources.Outfit_Regular
import vaultmultiplatform.core.ui.generated.resources.Outfit_SemiBold
import vaultmultiplatform.core.ui.generated.resources.Outfit_Thin
import vaultmultiplatform.core.ui.generated.resources.Res
import vaultmultiplatform.core.ui.generated.resources.poppins_black
import vaultmultiplatform.core.ui.generated.resources.poppins_bold
import vaultmultiplatform.core.ui.generated.resources.poppins_extra_light
import vaultmultiplatform.core.ui.generated.resources.poppins_italic
import vaultmultiplatform.core.ui.generated.resources.poppins_light
import vaultmultiplatform.core.ui.generated.resources.poppins_medium
import vaultmultiplatform.core.ui.generated.resources.poppins_regular
import vaultmultiplatform.core.ui.generated.resources.poppins_semi_bold
import vaultmultiplatform.core.ui.generated.resources.poppins_thin
import vaultmultiplatform.core.ui.generated.resources.ubuntu_bold
import vaultmultiplatform.core.ui.generated.resources.ubuntu_light
import vaultmultiplatform.core.ui.generated.resources.ubuntu_medium
import vaultmultiplatform.core.ui.generated.resources.ubuntu_regular


@Composable
fun getPoppinsFontFamily() = FontFamily(
    Font(Res.font.poppins_regular),
    Font(Res.font.poppins_medium, weight = FontWeight.Medium),
    Font(Res.font.poppins_semi_bold, FontWeight.SemiBold),
    Font(Res.font.poppins_bold, FontWeight.Bold),
    Font(Res.font.poppins_black, FontWeight.Black),
    Font(Res.font.poppins_light, FontWeight.Light),
    Font(Res.font.poppins_thin, FontWeight.Thin),
    Font(Res.font.poppins_extra_light, FontWeight.ExtraLight),
    Font(Res.font.poppins_italic, FontWeight.Normal, style = FontStyle.Italic),
)

@Composable
fun getUbuntuFontFamily() = FontFamily(
    Font(Res.font.ubuntu_regular),
    Font(Res.font.ubuntu_medium, FontWeight.Medium),
    Font(Res.font.ubuntu_medium, FontWeight.SemiBold),
    Font(Res.font.ubuntu_bold, FontWeight.Bold),
    Font(Res.font.ubuntu_bold, FontWeight.Black),
    Font(Res.font.ubuntu_light, FontWeight.Light),
    Font(Res.font.ubuntu_light, FontWeight.Thin),
    Font(Res.font.ubuntu_regular, FontWeight.Normal, style = FontStyle.Italic),
)

@Composable
fun getAlegreyaFontFamily() = FontFamily(
    Font(Res.font.AlegreyaSans_Regular, FontWeight.Normal),
    Font(Res.font.AlegreyaSans_Medium, FontWeight.Medium),
    Font(Res.font.AlegreyaSans_Bold, FontWeight.SemiBold),
    Font(Res.font.AlegreyaSans_Bold, FontWeight.Bold),
    Font(Res.font.AlegreyaSans_Black, FontWeight.Black),
    Font(Res.font.AlegreyaSans_Light, FontWeight.Light),
    Font(Res.font.AlegreyaSans_Thin, FontWeight.Thin),
    Font(Res.font.AlegreyaSans_Italic, FontWeight.Normal, style = FontStyle.Italic),
)

@Composable
fun getOutfitFontFamily() = FontFamily(
    Font(Res.font.Outfit_Regular),
    Font(Res.font.Outfit_Medium, FontWeight.Medium),
    Font(Res.font.Outfit_SemiBold, FontWeight.SemiBold),
    Font(Res.font.Outfit_Bold, FontWeight.Bold),
    Font(Res.font.Outfit_Black, FontWeight.Black),
    Font(Res.font.Outfit_Light, FontWeight.Light),
    Font(Res.font.Outfit_Thin, FontWeight.Thin),
    Font(Res.font.Outfit_Regular, FontWeight.Normal, style = FontStyle.Italic),
)

@Composable
fun getGlobalFontFamily() = getOutfitFontFamily()

@Composable
fun getTypography() = Typography(
    // Display Large - Montserrat 57/64 . -0.25px
    displayLarge = TextStyle(
        fontFamily = getGlobalFontFamily(),
        fontWeight = FontWeight.W400,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
    ),

    // Display Medium - Montserrat 45/52 . 0px
    displayMedium = TextStyle(
        fontFamily = getGlobalFontFamily(),
        fontWeight = FontWeight.W400,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
    ),

    // Display Small - Montserrat 36/44 . 0px
    displaySmall = TextStyle(
        fontFamily = getGlobalFontFamily(),
        fontWeight = FontWeight.W400,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    ),

    // Headline Large - Montserrat 32/40 . 0px
    headlineLarge = TextStyle(
        fontFamily = getGlobalFontFamily(),
        fontWeight = FontWeight.W400,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
    ),

    // Headline Medium - Montserrat 28/36 . 0px
    headlineMedium = TextStyle(
        fontFamily = getGlobalFontFamily(),
        fontWeight = FontWeight.W400,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
    ),

    // Headline Small - Montserrat 24/32 . 0px
    headlineSmall = TextStyle(
        fontFamily = getGlobalFontFamily(),
        fontWeight = FontWeight.W400,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
    ),

    // Title Large - Montserrat 22/28 . 0px
    titleLarge = TextStyle(
        fontFamily = getGlobalFontFamily(),
        fontWeight = FontWeight.W400,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),

    // Title Medium - Montserrat 16/24 . 0.15px
    titleMedium = TextStyle(
        fontFamily = getGlobalFontFamily(),
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
    ),

    // Title Small - Montserrat 14/20 . 0.1px
    titleSmall = TextStyle(
        fontFamily = getGlobalFontFamily(),
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),

    // Label Large - Montserrat 14/20 . 0.1px
    labelLarge = TextStyle(
        fontFamily = getGlobalFontFamily(),
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),

    // Label Medium - Montserrat 12/16 . 0.5px
    labelMedium = TextStyle(
        fontFamily = getGlobalFontFamily(),
        fontWeight = FontWeight.W500,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),

    // Label Small - Montserrat 11/16 . 0.5px
    labelSmall = TextStyle(
        fontFamily = getGlobalFontFamily(),
        fontWeight = FontWeight.W500,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),

    // Body Large - Montserrat 16/24 . 0.5px
    bodyLarge = TextStyle(
        fontFamily = getGlobalFontFamily(),
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),

    // Body Medium - Montserrat 14/20 . 0.25px
    bodyMedium = TextStyle(
        fontFamily = getGlobalFontFamily(),
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    // Body Small - Montserrat 12/16 . 0.4px
    bodySmall = TextStyle(
        fontFamily = getGlobalFontFamily(),
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
)