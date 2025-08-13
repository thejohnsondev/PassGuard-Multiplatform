package com.thejohnsondev.data.di

import com.thejohnsondev.data.CommonPasswords
import com.thejohnsondev.data.PasswordGeneratorRepositoryImpl
import com.thejohnsondev.data.ToolsRepositoryImpl
import com.thejohnsondev.domain.repo.PasswordGenerationRepository
import com.thejohnsondev.domain.repo.ToolsRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val toolsDataModule = module {
    single { CommonPasswords.list }
    singleOf(::ToolsRepositoryImpl) { bind<ToolsRepository>() }
    singleOf(::PasswordGeneratorRepositoryImpl) { bind<PasswordGenerationRepository>() }
}