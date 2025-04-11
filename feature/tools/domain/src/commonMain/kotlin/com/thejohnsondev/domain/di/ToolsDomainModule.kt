package com.thejohnsondev.domain.di

import com.thejohnsondev.domain.CopyTextUseCase
import com.thejohnsondev.domain.CopyTextUseCaseImpl
import com.thejohnsondev.domain.GeneratePasswordUseCase
import com.thejohnsondev.domain.GeneratePasswordUseCaseImpl
import com.thejohnsondev.domain.GetPasswordGeneratorConfigUseCase
import com.thejohnsondev.domain.GetPasswordGeneratorConfigUseCaseImpl
import com.thejohnsondev.domain.UpdatePasswordGeneratorConfigUseCase
import com.thejohnsondev.domain.UpdatePasswordGeneratorConfigUseCaseImpl
import com.thejohnsondev.domain.utils.PasswordGenerator
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val toolsDomainModule = module {
    single { PasswordGenerator(emptySet()) }
    singleOf(::UpdatePasswordGeneratorConfigUseCaseImpl) { bind<UpdatePasswordGeneratorConfigUseCase>() }
    singleOf(::GetPasswordGeneratorConfigUseCaseImpl) { bind<GetPasswordGeneratorConfigUseCase>() }
    singleOf(::GeneratePasswordUseCaseImpl) { bind<GeneratePasswordUseCase>() }
    singleOf(::CopyTextUseCaseImpl) { bind<CopyTextUseCase>() }
}