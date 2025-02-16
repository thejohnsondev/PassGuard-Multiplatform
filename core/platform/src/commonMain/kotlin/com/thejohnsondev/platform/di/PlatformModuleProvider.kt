package com.thejohnsondev.platform.di

import com.thejohnsondev.platform.encryption.EncryptionUtils
import com.thejohnsondev.platform.encryption.KeyGenerator
import org.koin.dsl.module

data class PlatformModuleProvider(
    val platformDependency: PlatformDependency
) {
    fun generatePlatformModule() = module {
        single<KeyGenerator> { platformDependency.getKeyGenerator() }
        single<EncryptionUtils> { platformDependency.getEncryptionUtils() }
    }
}