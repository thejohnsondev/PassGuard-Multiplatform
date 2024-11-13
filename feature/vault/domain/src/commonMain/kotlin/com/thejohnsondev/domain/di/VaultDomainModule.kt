package com.thejohnsondev.domain.di

import com.thejohnsondev.domain.CalculateListSizeUseCase
import com.thejohnsondev.domain.CalculateListSizeUseCaseImpl
import com.thejohnsondev.domain.SearchItemsUseCase
import com.thejohnsondev.domain.SearchItemsUseCaseImpl
import com.thejohnsondev.domain.SplitItemsListUseCase
import com.thejohnsondev.domain.SplitItemsListUseCaseImpl
import com.thejohnsondev.domain.ToggleOpenedItemUseCase
import com.thejohnsondev.domain.ToggleOpenedItemUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val vaultDomainModule = module {
    singleOf(::ToggleOpenedItemUseCaseImpl) { bind<ToggleOpenedItemUseCase>() }
    singleOf(::CalculateListSizeUseCaseImpl) { bind<CalculateListSizeUseCase>() }
    singleOf(::SplitItemsListUseCaseImpl) { bind<SplitItemsListUseCase>() }
    singleOf(::SearchItemsUseCaseImpl) { bind<SearchItemsUseCase>() }
}