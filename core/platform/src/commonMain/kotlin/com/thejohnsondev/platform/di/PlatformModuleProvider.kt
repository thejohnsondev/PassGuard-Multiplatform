package com.thejohnsondev.platform.di

import com.thejohnsondev.platform.KeyGenerator
import org.koin.dsl.module

data class PlatformModuleProvider(
    val platformDependency: PlatformDependency
) {
    fun generatePlatformModule() = module {
        single<KeyGenerator> { platformDependency.getKeyGenerator() }
    }
}