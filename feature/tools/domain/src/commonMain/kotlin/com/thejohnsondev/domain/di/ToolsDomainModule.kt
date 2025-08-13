package com.thejohnsondev.domain.di

import com.thejohnsondev.domain.CopyTextUseCase
import com.thejohnsondev.domain.EvaluatePasswordStrengthUseCase
import com.thejohnsondev.domain.GeneratePasswordUseCase
import com.thejohnsondev.domain.GenerateVaultHealthReportUseCases
import com.thejohnsondev.domain.GetPasswordGeneratorConfigUseCase
import com.thejohnsondev.domain.UpdatePasswordGeneratorConfigUseCase
import com.thejohnsondev.domain.vaulthealth.VaultHealthUtils
import org.koin.dsl.module

val toolsDomainModule = module {
    single { VaultHealthUtils(get()) }
    single { CopyTextUseCase(get()) }
    single { EvaluatePasswordStrengthUseCase(get()) }
    single { GeneratePasswordUseCase(get()) }
    single { GenerateVaultHealthReportUseCases(get()) }
    single { GetPasswordGeneratorConfigUseCase(get()) }
    single { UpdatePasswordGeneratorConfigUseCase(get()) }
}