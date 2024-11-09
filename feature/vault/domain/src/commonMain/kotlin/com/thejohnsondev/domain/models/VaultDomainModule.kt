package com.thejohnsondev.domain.models

import com.thejohnsondev.domain.ToggleOpenedItemUseCase
import com.thejohnsondev.domain.ToggleOpenedItemUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val vaultDomainModule = module {
    singleOf(::ToggleOpenedItemUseCaseImpl) { bind<ToggleOpenedItemUseCase>() }
}