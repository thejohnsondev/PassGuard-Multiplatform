package com.thejohnsondev.domain.di

import com.thejohnsondev.domain.GetUserEmailUseCase
import com.thejohnsondev.domain.GetUserEmailUseCaseImpl
import com.thejohnsondev.domain.UpdateSettingsUseCase
import com.thejohnsondev.domain.UpdateSettingsUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val settingsDomainModule = module {
    singleOf(::GetUserEmailUseCaseImpl) { bind<GetUserEmailUseCase>() }
    singleOf(::UpdateSettingsUseCaseImpl) { bind<UpdateSettingsUseCase>() }
}