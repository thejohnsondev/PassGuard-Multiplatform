package com.thejohnsondev.data.di

import com.thejohnsondev.data.PasswordsRepository
import com.thejohnsondev.data.PasswordsRepositoryImpl
import com.thejohnsondev.data.VaultRepository
import com.thejohnsondev.data.VaultRepositoryImpl
import org.koin.dsl.module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf

val vaultDataModule = module {
    singleOf(::PasswordsRepositoryImpl) { bind<PasswordsRepository>() }
    singleOf(::VaultRepositoryImpl) { bind<VaultRepository>() }
}