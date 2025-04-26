package com.thejohnsondev.domain.di

import com.thejohnsondev.data.CommonPasswords
import com.thejohnsondev.domain.CopyTextUseCase
import com.thejohnsondev.domain.CopyTextUseCaseImpl
import com.thejohnsondev.domain.EvaluatePasswordStrengthUseCase
import com.thejohnsondev.domain.EvaluatePasswordStrengthUseCaseImpl
import com.thejohnsondev.domain.GeneratePasswordUseCase
import com.thejohnsondev.domain.GeneratePasswordUseCaseImpl
import com.thejohnsondev.domain.GenerateVaultHealthReportUseCases
import com.thejohnsondev.domain.GenerateVaultHealthReportUseCasesImpl
import com.thejohnsondev.domain.GetPasswordGeneratorConfigUseCase
import com.thejohnsondev.domain.GetPasswordGeneratorConfigUseCaseImpl
import com.thejohnsondev.domain.UpdatePasswordGeneratorConfigUseCase
import com.thejohnsondev.domain.UpdatePasswordGeneratorConfigUseCaseImpl
import com.thejohnsondev.domain.passwordgenerator.PasswordGenerator
import com.thejohnsondev.domain.vaulthealth.VaultHealthUtils
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val toolsDomainModule = module {
    single { PasswordGenerator(CommonPasswords.list) }
    single { VaultHealthUtils(get()) }
    singleOf(::UpdatePasswordGeneratorConfigUseCaseImpl) { bind<UpdatePasswordGeneratorConfigUseCase>() }
    singleOf(::GetPasswordGeneratorConfigUseCaseImpl) { bind<GetPasswordGeneratorConfigUseCase>() }
    singleOf(::GeneratePasswordUseCaseImpl) { bind<GeneratePasswordUseCase>() }
    singleOf(::CopyTextUseCaseImpl) { bind<CopyTextUseCase>() }
    singleOf(::EvaluatePasswordStrengthUseCaseImpl) { bind<EvaluatePasswordStrengthUseCase>() }
    singleOf(::GenerateVaultHealthReportUseCasesImpl) { bind<GenerateVaultHealthReportUseCases>() }
}