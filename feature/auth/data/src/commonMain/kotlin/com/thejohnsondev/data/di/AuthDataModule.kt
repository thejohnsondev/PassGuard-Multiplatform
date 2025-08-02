package com.thejohnsondev.data.di

import com.thejohnsondev.common.AppType
import com.thejohnsondev.common.utils.BuildKonfigProvider
import com.thejohnsondev.data.AuthRepository
import com.thejohnsondev.data.AuthRepositoryImpl
import com.thejohnsondev.data.DemoEncryptionRepository
import com.thejohnsondev.data.EncryptionRepository
import com.thejohnsondev.data.EncryptionRepositoryImpl
import com.thejohnsondev.data.GenerateKeyRepository
import com.thejohnsondev.data.GenerateKeyRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authDataModule = module {
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    singleOf(::GenerateKeyRepositoryImpl) { bind<GenerateKeyRepository>() }

    when (AppType.from(BuildKonfigProvider.getAppType())) {
        AppType.DEMO -> {
            singleOf(::DemoEncryptionRepository) { bind<EncryptionRepository>() }
        }

        AppType.REAL -> {
            singleOf(::EncryptionRepositoryImpl) { bind<EncryptionRepository>() }
        }
    }
}