package com.thejohnsondev.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.thejohnsondev.common.EMPTY
import com.thejohnsondev.ui.designsystem.Size16

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    textState: MutableState<String>,
    onTextChanged: (String) -> Unit,
    label: String = EMPTY,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    singleLine: Boolean = true,
    onKeyboardAction: KeyboardActions = KeyboardActions.Default,
    errorText: String? = null,
    isError: Boolean = false
) {
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    val visualTransformation =
        if (!isPasswordVisible && keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = textState.value,
            onValueChange = {
                onTextChanged(it)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            label = {
                Text(text = label)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            singleLine = singleLine,
            keyboardActions = onKeyboardAction,
            isError = isError && errorText != null,
            visualTransformation = visualTransformation,
            trailingIcon = {
                if (keyboardType == KeyboardType.Password) {
                    val image = if (isPasswordVisible)
                        Icons.Default.Visibility
                    else Icons.Default.VisibilityOff
                    val description = if (isPasswordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            }
        )
        if (isError && errorText != null) {
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = Size16)
            )
        }

    }
}