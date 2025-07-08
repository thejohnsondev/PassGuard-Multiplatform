package com.thejohnsondev.domain.di

import com.thejohnsondev.domain.AuthService
import com.thejohnsondev.domain.AuthServiceImpl
import com.thejohnsondev.domain.EmailValidateUseCase
import com.thejohnsondev.domain.EmailValidateUseCaseImpl
import com.thejohnsondev.domain.GetBiometricAvailabilityUseCase
import com.thejohnsondev.domain.GetBiometricAvailabilityUseCaseImpl
import com.thejohnsondev.domain.GetFirstScreenRouteUseCase
import com.thejohnsondev.domain.GetFirstScreenRouteUseCaseImpl
import com.thejohnsondev.domain.PasswordValidationUseCase
import com.thejohnsondev.domain.PasswordValidationUseCaseImpl
import com.thejohnsondev.domain.ShowBiometricPromptUseCase
import com.thejohnsondev.domain.ShowBiometricPromptUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authDomainModule = module {
    singleOf(::EmailValidateUseCaseImpl) { bind<EmailValidateUseCase>() }
    singleOf(::PasswordValidationUseCaseImpl) { bind<PasswordValidationUseCase>() }
    singleOf(::AuthServiceImpl) { bind<AuthService>() }
    singleOf(::GetFirstScreenRouteUseCaseImpl) { bind<GetFirstScreenRouteUseCase>() }
    singleOf(::ShowBiometricPromptUseCaseImpl) { bind<ShowBiometricPromptUseCase>() }
    singleOf(::GetBiometricAvailabilityUseCaseImpl) { bind<GetBiometricAvailabilityUseCase>() }
}