package com.thejohnsondev.presentation.di

import com.thejohnsondev.presentation.biometric.BiometricLoginViewModel
import com.thejohnsondev.presentation.login.LoginViewModel
import com.thejohnsondev.presentation.onboarding.OnboardingViewModel
import com.thejohnsondev.presentation.signup.SignUpViewModel
import com.thejohnsondev.presentation.vaulttype.SelectedVaultTypeViewModel
import com.thejohnsondev.presentation.welcome.WelcomeViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

actual val authPresentationModule = module {
    viewModelOf(::WelcomeViewModel)
    viewModelOf(::OnboardingViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::SelectedVaultTypeViewModel)
    viewModelOf(::BiometricLoginViewModel)
}