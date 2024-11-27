package com.thejohnsondev.di

import com.thejohnsondev.data.PasswordsRepository
import com.thejohnsondev.data.PasswordsRepositoryImpl
import org.koin.dsl.module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf

val vaultDataModule = module {
    singleOf(::PasswordsRepositoryImpl) { bind<PasswordsRepository>() }
}