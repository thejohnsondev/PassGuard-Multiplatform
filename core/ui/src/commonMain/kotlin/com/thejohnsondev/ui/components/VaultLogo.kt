package com.thejohnsondev.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import com.thejohnsondev.ui.designsystem.getGlobalFontFamily
import com.thejohnsondev.ui.utils.ResString
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.core.ui.generated.resources.Res
import vaultmultiplatform.core.ui.generated.resources.guard
import vaultmultiplatform.core.ui.generated.resources.pass

@Composable
fun VaultLogo(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = MaterialTheme.typography.displayLarge.fontSize,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally
) {
    Column(
        horizontalAlignment = horizontalAlignment,
        modifier = modifier
    ) {
        Text(text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = fontSize
                ),
            ) {
                append(stringResource(ResString.pass))
            }
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = fontSize,
                    fontFamily = getGlobalFontFamily()
                )
            ) {
                append(stringResource(ResString.guard))
            }
        })

    }
}