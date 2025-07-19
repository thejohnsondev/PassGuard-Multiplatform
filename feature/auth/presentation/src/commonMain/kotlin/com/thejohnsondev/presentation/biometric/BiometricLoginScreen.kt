package com.thejohnsondev.presentation.biometric

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thejohnsondev.ui.components.container.RoundedContainer
import com.thejohnsondev.ui.designsystem.Size128
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.utils.ResDrawable
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.bounceClick
import com.thejohnsondev.ui.utils.padding
import com.thejosnsondev.biometric.BiometricType
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import vaultmultiplatform.core.ui.generated.resources.biometric_login_description
import vaultmultiplatform.core.ui.generated.resources.biometric_login_title
import vaultmultiplatform.core.ui.generated.resources.ic_face_id
import vaultmultiplatform.core.ui.generated.resources.ic_password

@Composable
fun BiometricLoginScreen(
    viewModel: BiometricLoginViewModel,
    goToHome: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
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
    LaunchedEffect(Unit) {
        viewModel.perform(BiometricLoginViewModel.Action.GetBiometricAvailability)
    }
    BiometricScreenContent(
        state = state.value,
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
    state: BiometricLoginViewModel.State,
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
                AnimatedVisibility(
                    state.biometricType != null,
                    enter = fadeIn()
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(Size16)
                            .size(Size128),
                        imageVector = when (state.biometricType) {
                            BiometricType.FINGERPRINT -> Icons.Default.Fingerprint
                            BiometricType.FACE -> vectorResource(ResDrawable.ic_face_id)
                            BiometricType.DEVICE_PASSWORD_FALLBACK -> vectorResource(ResDrawable.ic_password)
                            else -> vectorResource(ResDrawable.ic_password)
                        },
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
