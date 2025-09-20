package com.thejohnsondev.domain.di

import com.thejohnsondev.domain.CheckPassDuplicatesUseCase
import com.thejohnsondev.domain.ExportVaultUseCase
import com.thejohnsondev.domain.GenerateExportCSVUseCase
import com.thejohnsondev.domain.GetContactInfoUseCase
import com.thejohnsondev.domain.GetSettingsFlowUseCase
import com.thejohnsondev.domain.GetUserEmailUseCase
import com.thejohnsondev.domain.GetVersionInfoUseCase
import com.thejohnsondev.domain.IsBlockingScreenshotAvailableUseCase
import com.thejohnsondev.domain.IsDynamicThemeAvailableUseCase
import com.thejohnsondev.domain.ParsePasswordsCSVUseCase
import com.thejohnsondev.domain.SelectCSVUseCase
import com.thejohnsondev.domain.UpdateSettingsUseCase
import org.koin.dsl.module

val settingsDomainModule = module {
    single { CheckPassDuplicatesUseCase() }
    single { ExportVaultUseCase(get()) }
    single { GenerateExportCSVUseCase() }
    single { GetUserEmailUseCase(get()) }
    single { UpdateSettingsUseCase(get()) }
    single { GetSettingsFlowUseCase(get()) }
    single { IsDynamicThemeAvailableUseCase(get()) }
    single { IsBlockingScreenshotAvailableUseCase(get()) }
    single { SelectCSVUseCase(get()) }
    single { ParsePasswordsCSVUseCase() }
    single { GetVersionInfoUseCase() }
    single { GetContactInfoUseCase() }
}