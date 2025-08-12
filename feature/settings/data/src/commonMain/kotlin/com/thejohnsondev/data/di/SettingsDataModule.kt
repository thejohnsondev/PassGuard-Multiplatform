package com.thejohnsondev.data.di

import com.thejohnsondev.data.ExportImportRepositoryImpl
import com.thejohnsondev.data.SettingsRepositoryImpl
import com.thejohnsondev.domain.repo.ExportImportRepository
import com.thejohnsondev.domain.repo.SettingsRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val settingsDataModule = module {
    singleOf(::SettingsRepositoryImpl) { bind<SettingsRepository>() }
    singleOf(::ExportImportRepositoryImpl) { bind<ExportImportRepository>() }
}