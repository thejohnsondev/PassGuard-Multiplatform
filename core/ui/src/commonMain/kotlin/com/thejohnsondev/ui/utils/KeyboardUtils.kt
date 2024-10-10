package com.thejohnsondev.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

@Composable
expect fun keyboardAsState(): State<Boolean>

expect fun isKeyboardAvailable(): Boolean