package com.thejohnsondev.ui.components.text

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import com.thejohnsondev.ui.designsystem.Percent60
import com.thejohnsondev.ui.designsystem.Text20
import com.thejohnsondev.ui.designsystem.getGlobalFontFamily

@Composable
fun HintTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChanged: (String) -> Unit,
    hint: String = "",
    maxLines: Int = 1,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    fontSize: TextUnit = Text20,
    imeAction: ImeAction = ImeAction.Default,
    keyboardType: KeyboardType = KeyboardType.Text,
    passwordVisible: Boolean = true,
    onKeyboardAction: KeyboardActions = KeyboardActions.Default,
) {
    Box {
        BasicTextField(
            modifier = modifier
                .semantics {
                    contentDescription = value
                },
            value = value,
            onValueChange = {
                onValueChanged(it)
            },
            textStyle = TextStyle(
                color = textColor,
                fontFamily = getGlobalFontFamily(),
                fontSize = fontSize
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = onKeyboardAction,
            maxLines = maxLines, // For unlimited lines, use Int.MAX_VALUE
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        )
        if (value.isEmpty()) {
            Text(
                text = hint,
                modifier = modifier,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = Percent60),
                    fontSize = fontSize,
                    fontFamily = getGlobalFontFamily()
                )
            )
        }
    }
}