package com.thejohnsondev.domain.di

import com.thejohnsondev.domain.GetSettingsFlowUseCase
import com.thejohnsondev.domain.GetSettingsFlowUseCaseImpl
import com.thejohnsondev.domain.GetUserEmailUseCase
import com.thejohnsondev.domain.GetUserEmailUseCaseImpl
import com.thejohnsondev.domain.IsBiometricsAvailableUseCase
import com.thejohnsondev.domain.UpdateSettingsUseCase
import com.thejohnsondev.domain.UpdateSettingsUseCaseImpl
import com.thejohnsondev.domain.IsBiometricsAvailableUseCaseImpl
import com.thejohnsondev.domain.IsDynamicThemeAvailableUseCase
import com.thejohnsondev.domain.IsDynamicThemeAvailableUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val settingsDomainModule = module {
    singleOf(::GetUserEmailUseCaseImpl) { bind<GetUserEmailUseCase>() }
    singleOf(::UpdateSettingsUseCaseImpl) { bind<UpdateSettingsUseCase>() }
    singleOf(::IsBiometricsAvailableUseCaseImpl) { bind<IsBiometricsAvailableUseCase>() }
    singleOf(::GetSettingsFlowUseCaseImpl) { bind<GetSettingsFlowUseCase>() }
    singleOf(::IsDynamicThemeAvailableUseCaseImpl) { bind<IsDynamicThemeAvailableUseCase>() }
}