package com.thejohnsondev.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
    maxLines: Int? = null,
    focusRequester: FocusRequester = FocusRequester(),
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    fontSize: TextUnit = Text20,
    imeAction: ImeAction = ImeAction.Default,
    keyboardType: KeyboardType = KeyboardType.Text,
    passwordVisible: Boolean = true,
    onKeyboardAction: () -> Unit = {}
) {
    Box {
        BasicTextField(
            modifier = modifier
                .focusRequester(focusRequester)
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
            keyboardActions = KeyboardActions {
                onKeyboardAction()
            },
            maxLines = maxLines ?: Int.MAX_VALUE,
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