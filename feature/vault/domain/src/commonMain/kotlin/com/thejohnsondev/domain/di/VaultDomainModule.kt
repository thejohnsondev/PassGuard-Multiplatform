package com.thejohnsondev.domain.di

import com.thejohnsondev.domain.CalculateListSizeUseCase
import com.thejohnsondev.domain.CalculateListSizeUseCaseImpl
import com.thejohnsondev.domain.CheckFiltersAppliedUseCase
import com.thejohnsondev.domain.CheckFiltersAppliedUseCaseImpl
import com.thejohnsondev.domain.ItemTypeFilterChangeUseCase
import com.thejohnsondev.domain.ItemTypeFilterChangeUseCaseImpl
import com.thejohnsondev.domain.PasswordsService
import com.thejohnsondev.domain.PasswordsServiceImpl
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
    singleOf(::PasswordsServiceImpl) { bind<PasswordsService>() }
    singleOf(::ToggleOpenedItemUseCaseImpl) { bind<ToggleOpenedItemUseCase>() }
    singleOf(::CalculateListSizeUseCaseImpl) { bind<CalculateListSizeUseCase>() }
    singleOf(::SplitItemsListUseCaseImpl) { bind<SplitItemsListUseCase>() }
    singleOf(::SearchItemsUseCaseImpl) { bind<SearchItemsUseCase>() }
    singleOf(::ItemTypeFilterChangeUseCaseImpl) { bind<ItemTypeFilterChangeUseCase>() }
    singleOf(::CheckFiltersAppliedUseCaseImpl) { bind<CheckFiltersAppliedUseCase>() }
}