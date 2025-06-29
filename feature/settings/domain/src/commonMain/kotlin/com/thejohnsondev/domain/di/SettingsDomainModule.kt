package com.thejohnsondev.domain.di

import com.thejohnsondev.domain.CheckPassDuplicatesUseCase
import com.thejohnsondev.domain.CheckPassDuplicatesUseCaseImpl
import com.thejohnsondev.domain.ExportVaultUseCase
import com.thejohnsondev.domain.ExportVaultUseCaseImpl
import com.thejohnsondev.domain.GenerateExportCSVUseCase
import com.thejohnsondev.domain.GenerateExportCSVUseCaseImpl
import com.thejohnsondev.domain.SelectCSVUseCase
import com.thejohnsondev.domain.SelectCSVUseCaseImpl
import com.thejohnsondev.domain.GetSettingsFlowUseCase
import com.thejohnsondev.domain.GetSettingsFlowUseCaseImpl
import com.thejohnsondev.domain.GetUserEmailUseCase
import com.thejohnsondev.domain.GetUserEmailUseCaseImpl
import com.thejohnsondev.domain.GetBiometricAvailabilityUseCase
import com.thejohnsondev.domain.UpdateSettingsUseCase
import com.thejohnsondev.domain.UpdateSettingsUseCaseImpl
import com.thejohnsondev.domain.GetBiometricAvailabilityUseCaseImpl
import com.thejohnsondev.domain.IsBlockingScreenshotAvailableUseCase
import com.thejohnsondev.domain.IsBlockingScreenshotAvailableUseCaseImpl
import com.thejohnsondev.domain.IsDynamicThemeAvailableUseCase
import com.thejohnsondev.domain.IsDynamicThemeAvailableUseCaseImpl
import com.thejohnsondev.domain.ParsePasswordsCSVUseCase
import com.thejohnsondev.domain.ParsePasswordsCSVUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val settingsDomainModule = module {
    singleOf(::GetUserEmailUseCaseImpl) { bind<GetUserEmailUseCase>() }
    singleOf(::UpdateSettingsUseCaseImpl) { bind<UpdateSettingsUseCase>() }
    singleOf(::GetBiometricAvailabilityUseCaseImpl) { bind<GetBiometricAvailabilityUseCase>() }
    singleOf(::GetSettingsFlowUseCaseImpl) { bind<GetSettingsFlowUseCase>() }
    singleOf(::IsDynamicThemeAvailableUseCaseImpl) { bind<IsDynamicThemeAvailableUseCase>() }
    singleOf(::IsBlockingScreenshotAvailableUseCaseImpl) { bind<IsBlockingScreenshotAvailableUseCase>() }
    singleOf(::ExportVaultUseCaseImpl) { bind<ExportVaultUseCase>() }
    singleOf(::GenerateExportCSVUseCaseImpl) { bind<GenerateExportCSVUseCase>() }
    singleOf(::SelectCSVUseCaseImpl) { bind<SelectCSVUseCase>() }
    singleOf(::ParsePasswordsCSVUseCaseImpl) { bind<ParsePasswordsCSVUseCase>() }
    singleOf(::CheckPassDuplicatesUseCaseImpl) { bind<CheckPassDuplicatesUseCase>() }
}