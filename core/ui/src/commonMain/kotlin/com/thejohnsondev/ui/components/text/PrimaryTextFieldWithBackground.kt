package com.thejohnsondev.ui.components.text

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import com.thejohnsondev.ui.components.container.RoundedContainer
import com.thejohnsondev.ui.designsystem.EquallyRounded
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Text20

@Composable
fun PrimaryTextFieldWithBackground(
    modifier: Modifier = Modifier,
    value: String? = null,
    onValueChanged: (String) -> Unit,
    hint: String? = null,
    errorText: String? = null,
    maxLines: Int = 1,
    maxSymbols: Int? = null,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    fontSize: TextUnit = Text20,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    onKeyboardAction: KeyboardActions = KeyboardActions.Default,
    readOnly: Boolean = false,
    textFieldIconBehavior: TextFieldIconBehavior = TextFieldIconBehavior.None,
    focusRequester: FocusRequester? = null,
    backgroundShape: Shape = EquallyRounded.medium,
    backgroundShapeColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh
) {
    RoundedContainer(
        modifier = modifier,
        color = backgroundShapeColor,
        shape = backgroundShape
    ) {
        PrimaryTextField(
            modifier = Modifier
                .padding(horizontal = Size12, vertical = Size16),
            value = value,
            onValueChanged = onValueChanged,
            hint = hint,
            errorText = errorText,
            maxLines = maxLines,
            maxSymbols = maxSymbols,
            textColor = textColor,
            fontSize = fontSize,
            imeAction = imeAction,
            keyboardType = keyboardType,
            onKeyboardAction = onKeyboardAction,
            readOnly = readOnly,
            textFieldIconBehavior = textFieldIconBehavior,
            focusRequester = focusRequester,
        )
    }
}