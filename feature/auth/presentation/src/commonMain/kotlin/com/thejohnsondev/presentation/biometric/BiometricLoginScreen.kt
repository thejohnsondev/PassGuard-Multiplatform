package com.thejohnsondev.presentation.biometric

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.thejohnsondev.ui.designsystem.Size128
import com.thejohnsondev.ui.designsystem.Size16

@Composable
fun BiometricLoginScreen(
    viewModel: BiometricLoginViewModel,
    goToHome: () -> Unit
) {
    val biometricPromptDescriptionProvider = remember {
        BiometricPromptDescriptionProvider()
    }
    val promptTitle = biometricPromptDescriptionProvider.getTitle()
    val promptSubtitle = biometricPromptDescriptionProvider.getSubtitle()
    val promptDescription = biometricPromptDescriptionProvider.getDescription()
    LaunchedEffect(Unit) {
        viewModel.perform(
            BiometricLoginViewModel.Action.ShowLoginPrompt(
                title = promptTitle,
                subtitle = promptSubtitle,
                description = promptDescription
            )
        )
    }
    LaunchedEffect(Unit) {
        viewModel.getEventFlow().collect {
            when (it) {
                BiometricLoginViewModel.OnLoginSuccess -> goToHome()
            }
        }
    }
    BiometricScreenContent(
        showPrompt = {
            viewModel.perform(BiometricLoginViewModel.Action.ShowLoginPrompt(
                title = promptTitle,
                subtitle = promptSubtitle,
                description = promptDescription
            ))
        }
    )
}

@Composable
private fun BiometricScreenContent(
    showPrompt: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(start = Size16, end = Size16, top = Size128),
                text = "Biometric Authentication",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                modifier = Modifier.padding(top = Size16),
                text = "Unlock the app using your fingerprint or face recognition."
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                modifier = Modifier.size(Size128),
                onClick = {
                    showPrompt()
                }) {
                Icon(
                    modifier = Modifier.size(Size128),
                    imageVector = Icons.Default.Fingerprint,
                    contentDescription = "Fingerprint",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
