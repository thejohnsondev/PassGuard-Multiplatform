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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.thejohnsondev.ui.components.container.RoundedContainer
import com.thejohnsondev.ui.designsystem.Size128
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.bounceClick
import com.thejohnsondev.ui.utils.padding
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.core.ui.generated.resources.biometric_login_description
import vaultmultiplatform.core.ui.generated.resources.biometric_login_title

@Composable
fun BiometricLoginScreen(
    viewModel: BiometricLoginViewModel,
    goToHome: () -> Unit
) {
    // TODO move biometric login strings to resources
    // TODO use string resources in the BiometricAuthenticator
    // TODO Update screen UI
    // TODO handle error states
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
            viewModel.perform(
                BiometricLoginViewModel.Action.ShowLoginPrompt(
                    title = promptTitle,
                    subtitle = promptSubtitle,
                    description = promptDescription
                )
            )
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
            Modifier.padding(horizontal = Size16, top = Size128),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(ResString.biometric_login_title),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                modifier = Modifier.padding(top = Size16),
                text = stringResource(ResString.biometric_login_description)
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RoundedContainer(
                modifier = Modifier
                    .bounceClick(),
                onClick = {
                    showPrompt()
                },
                color = MaterialTheme.colorScheme.background
            ) {
                Icon(
                    modifier = Modifier
                        .padding(Size16)
                        .size(Size128),
                    imageVector = Icons.Default.Fingerprint,
                    contentDescription = "Fingerprint",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
