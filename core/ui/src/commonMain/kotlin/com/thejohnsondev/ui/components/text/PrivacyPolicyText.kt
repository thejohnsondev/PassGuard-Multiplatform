package com.thejohnsondev.ui.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.thejohnsondev.ui.designsystem.getGlobalFontFamily
import com.thejohnsondev.ui.utils.ResString
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.core.ui.generated.resources.accept_and_agree
import vaultmultiplatform.core.ui.generated.resources.and
import vaultmultiplatform.core.ui.generated.resources.privacy_policy
import vaultmultiplatform.core.ui.generated.resources.terms_of_use

@Composable
fun PrivacyPolicyAcceptText(): AnnotatedString {
    val text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Normal,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontFamily = getGlobalFontFamily()
            ),
        ) {
            append(stringResource(ResString.accept_and_agree))
            append(" ")
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                textDecoration = TextDecoration.Underline,
                fontFamily = getGlobalFontFamily()
            )
        ) {
            pushStringAnnotation(tag = TERMS_OF_USE_TAG, annotation = "click")
            append(stringResource(ResString.terms_of_use))
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Normal,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontFamily = getGlobalFontFamily()
            ),
        ) {
            append(" ")
            append(stringResource(ResString.and))
            append(" ")
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                textDecoration = TextDecoration.Underline,
                fontFamily = getGlobalFontFamily()
            )
        ) {
            pushStringAnnotation(tag = PRIVACY_POLICY_TAG, annotation = "click")
            append(stringResource(ResString.privacy_policy))
        }
    }
    return text
}

const val PRIVACY_POLICY_TAG = "privacy-policy"
const val TERMS_OF_USE_TAG = "terms-of-use"