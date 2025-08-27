package com.thejohnsondev.domain.di

import com.thejohnsondev.domain.CheckInstallIDUseCase
import com.thejohnsondev.domain.EmailValidateUseCase
import com.thejohnsondev.domain.GetBiometricAvailabilityUseCase
import com.thejohnsondev.domain.GetFirstScreenRouteUseCase
import com.thejohnsondev.domain.PasswordValidationUseCase
import com.thejohnsondev.domain.ShowBiometricPromptUseCase
import org.koin.dsl.module

val authDomainModule = module {
    single { EmailValidateUseCase() }
    single { GetBiometricAvailabilityUseCase(get()) }
    single { GetFirstScreenRouteUseCase(get()) }
    single { PasswordValidationUseCase() }
    single { ShowBiometricPromptUseCase(get()) }
    single { CheckInstallIDUseCase(get()) }
}