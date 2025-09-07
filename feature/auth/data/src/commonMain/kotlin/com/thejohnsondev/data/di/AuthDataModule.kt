package com.thejohnsondev.data.di

import com.thejohnsondev.common.AppType
import com.thejohnsondev.common.utils.BuildKonfigProvider
import com.thejohnsondev.data.AuthRepositoryImpl
import com.thejohnsondev.data.AuthServiceImpl
import com.thejohnsondev.data.DemoEncryptionRepository
import com.thejohnsondev.data.EncryptionRepositoryImpl
import com.thejohnsondev.data.GenerateKeyRepositoryImpl
import com.thejohnsondev.domain.repo.AuthRepository
import com.thejohnsondev.domain.repo.AuthService
import com.thejohnsondev.domain.repo.EncryptionRepository
import com.thejohnsondev.domain.repo.GenerateKeyRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authDataModule = module {
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    singleOf(::GenerateKeyRepositoryImpl) { bind<GenerateKeyRepository>() }
    singleOf(::AuthServiceImpl) { bind<AuthService>() }

    when (AppType.from(BuildKonfigProvider.getAppType())) {
        AppType.DEMO -> {
            singleOf(::DemoEncryptionRepository) { bind<EncryptionRepository>() }
        }

        AppType.DEV,
        AppType.PROD -> {
            singleOf(::EncryptionRepositoryImpl) { bind<EncryptionRepository>() }
        }
    }
}