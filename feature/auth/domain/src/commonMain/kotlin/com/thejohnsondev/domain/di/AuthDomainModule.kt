package com.thejohnsondev.domain.di

import com.thejohnsondev.domain.AuthUseCases
import com.thejohnsondev.domain.ChangePasswordUseCase
import com.thejohnsondev.domain.DeleteAccountUseCase
import com.thejohnsondev.domain.EmailValidateUseCase
import com.thejohnsondev.domain.GenerateUserKeyUseCase
import com.thejohnsondev.domain.LogoutUseCase
import com.thejohnsondev.domain.PasswordValidationUseCase
import com.thejohnsondev.domain.SaveUserEmailUseCase
import com.thejohnsondev.domain.SaveUserKeyUseCase
import com.thejohnsondev.domain.SaveUserTokenUseCase
import com.thejohnsondev.domain.SignInUseCase
import com.thejohnsondev.domain.SignUpUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authDomainModule = module {
    singleOf(::ChangePasswordUseCase)
    singleOf(::DeleteAccountUseCase)
    singleOf(::EmailValidateUseCase)
    singleOf(::GenerateUserKeyUseCase)
    singleOf(::LogoutUseCase)
    singleOf(::PasswordValidationUseCase)
    singleOf(::SaveUserKeyUseCase)
    singleOf(::SaveUserEmailUseCase)
    singleOf(::SaveUserTokenUseCase)
    singleOf(::SignInUseCase)
    singleOf(::SignUpUseCase)
    singleOf(::AuthUseCases)
}