package com.thejohnsondev.ui.components.text

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.thejohnsondev.common.empty
import com.thejohnsondev.ui.designsystem.Percent100
import com.thejohnsondev.ui.designsystem.Percent60
import com.thejohnsondev.ui.designsystem.Text14
import com.thejohnsondev.ui.designsystem.Text20
import com.thejohnsondev.ui.designsystem.getGlobalFontFamily
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.bounceClick

@Composable
fun PrimaryTextField(
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
) {
    val passwordHiddenState = remember { mutableStateOf(true) }

    val textState = remember { mutableStateOf(value ?: String.empty) }

    LaunchedEffect(value) {
        textState.value = value ?: String.empty
    }

    Column(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight(),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                BasicTextField(
                    modifier = Modifier
                        .weight(Percent100)
                        .wrapContentHeight()
                        .semantics {
                            contentDescription = hint ?: "Text Field"
                        }
                        .applyIf(focusRequester != null) {
                            focusRequester(focusRequester!!)
                        },
                    value = textState.value,
                    onValueChange = {
                        if (!readOnly) {
                            textState.value = maxSymbols?.let { maxSymbols ->
                                it.take(maxSymbols)
                            } ?: it
                            onValueChanged(textState.value)
                        }
                    },
                    textStyle = TextStyle(
                        color = textColor,
                        fontSize = fontSize,
                        fontFamily = getGlobalFontFamily()
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = imeAction
                    ),
                    keyboardActions = onKeyboardAction,
                    maxLines = maxLines,
                    singleLine = maxLines == 1,
                    visualTransformation = if (passwordHiddenState.value && textFieldIconBehavior is TextFieldIconBehavior.HideShow) {
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    },
                    readOnly = readOnly,
                )
                ActionIcon(
                    textFieldIconBehavior = textFieldIconBehavior,
                    passwordVisibleState = passwordHiddenState.value,
                    changeText = { textState.value = it },
                    togglePasswordVisibleState = { passwordHiddenState.value = it }
                )
            }
            TextFieldHint(
                textState = textState.value,
                fontSize = fontSize,
                textColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = Percent60),
                hint = hint,
            )
        }
        ErrorText(errorText, fontSize = Text14, textColor = MaterialTheme.colorScheme.error)
    }
}

@Composable
fun TextFieldHint(
    modifier: Modifier = Modifier,
    textState: String,
    hint: String?,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    fontSize: TextUnit = Text20,
) {
    if (textState.isBlank() && !hint.isNullOrBlank()) {
        Text(
            text = hint,
            modifier = modifier,
            style = TextStyle(
                color = textColor,
                fontSize = fontSize,
                fontFamily = getGlobalFontFamily()
            )
        )

    }
}

@Composable
private fun ActionIcon(
    textFieldIconBehavior: TextFieldIconBehavior,
    passwordVisibleState: Boolean,
    changeText: (String) -> Unit,
    togglePasswordVisibleState: (Boolean) -> Unit,
) {
    when (textFieldIconBehavior) {
        is TextFieldIconBehavior.Clear -> {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .bounceClick()
                    .clickable {
                        changeText(String.empty)
                        textFieldIconBehavior.onClick()
                    },
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        is TextFieldIconBehavior.HideShow -> {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .bounceClick()
                    .clickable {
                        togglePasswordVisibleState(!passwordVisibleState)
                    },
                imageVector = if (passwordVisibleState) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = if (passwordVisibleState) "Hide password" else "Show password",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        is TextFieldIconBehavior.Icon -> {
            if (textFieldIconBehavior.onClick != null) {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .bounceClick()
                        .clickable {
                            textFieldIconBehavior.onClick.invoke()
                        },
                    imageVector = textFieldIconBehavior.icon,
                    contentDescription = textFieldIconBehavior.icon.name,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            } else {
                Icon(
                    imageVector = textFieldIconBehavior.icon,
                    contentDescription = textFieldIconBehavior.icon.name,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        TextFieldIconBehavior.None -> {}
    }
}