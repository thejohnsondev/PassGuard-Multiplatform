package com.thejohnsondev.domain.di

import com.thejohnsondev.data.CommonPasswords
import com.thejohnsondev.domain.CopyTextUseCase
import com.thejohnsondev.domain.EvaluatePasswordStrengthUseCase
import com.thejohnsondev.domain.GeneratePasswordUseCase
import com.thejohnsondev.domain.GenerateVaultHealthReportUseCases
import com.thejohnsondev.domain.GetPasswordGeneratorConfigUseCase
import com.thejohnsondev.domain.UpdatePasswordGeneratorConfigUseCase
import com.thejohnsondev.domain.passwordgenerator.PasswordGenerator
import com.thejohnsondev.domain.passwordgenerator.PasswordGeneratorImpl
import com.thejohnsondev.domain.vaulthealth.VaultHealthUtils
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val toolsDomainModule = module {
    single { CommonPasswords.list }
    singleOf(::PasswordGeneratorImpl) { bind<PasswordGenerator>() }
    single { VaultHealthUtils(get()) }
    single { CopyTextUseCase(get()) }
    single { EvaluatePasswordStrengthUseCase(get()) }
    single { GeneratePasswordUseCase(get()) }
    single { GenerateVaultHealthReportUseCases(get()) }
    single { GetPasswordGeneratorConfigUseCase(get()) }
    single { UpdatePasswordGeneratorConfigUseCase(get()) }
}