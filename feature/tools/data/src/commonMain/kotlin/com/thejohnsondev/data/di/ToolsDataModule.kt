package com.thejohnsondev.data.di

import com.thejohnsondev.data.ToolsRepository
import com.thejohnsondev.data.ToolsRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val toolsDataModule = module {
    singleOf(::ToolsRepositoryImpl) { bind<ToolsRepository>()}
}