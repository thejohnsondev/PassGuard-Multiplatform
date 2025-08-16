package com.thejohnsondev.data.di

import com.thejohnsondev.domain.repo.PasswordsRepository
import com.thejohnsondev.data.PasswordsRepositoryImpl
import com.thejohnsondev.domain.repo.VaultRepository
import com.thejohnsondev.data.VaultRepositoryImpl
import org.koin.dsl.module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf

val vaultDataModule = module {
    singleOf(::PasswordsRepositoryImpl) { bind<PasswordsRepository>() }
    singleOf(::VaultRepositoryImpl) { bind<VaultRepository>() }
}