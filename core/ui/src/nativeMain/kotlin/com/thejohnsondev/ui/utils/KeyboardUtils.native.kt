package com.thejohnsondev.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState

// TODO Implement this later for iOS

@Composable
actual fun keyboardAsState(): State<Boolean> = rememberUpdatedState(false)

actual fun isKeyboardAvailable(): Boolean = false