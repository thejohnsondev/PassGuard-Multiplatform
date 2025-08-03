package com.thejohnsondev.domain.di

import com.thejohnsondev.domain.AuthService
import com.thejohnsondev.domain.AuthServiceImpl
import com.thejohnsondev.domain.EmailValidateUseCase
import com.thejohnsondev.domain.GetBiometricAvailabilityUseCase
import com.thejohnsondev.domain.GetFirstScreenRouteUseCase
import com.thejohnsondev.domain.PasswordValidationUseCase
import com.thejohnsondev.domain.ShowBiometricPromptUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authDomainModule = module {
    single { EmailValidateUseCase() }
    single { GetBiometricAvailabilityUseCase(get()) }
    single { GetFirstScreenRouteUseCase(get()) }
    single { PasswordValidationUseCase() }
    single { ShowBiometricPromptUseCase(get()) }
    singleOf(::AuthServiceImpl) { bind<AuthService>() }
}