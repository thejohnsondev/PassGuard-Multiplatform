package com.thejohnsondev.domain.di

import com.thejohnsondev.domain.AddAdditionalFieldUseCase
import com.thejohnsondev.domain.AddAdditionalFieldUseCaseImpl
import com.thejohnsondev.domain.CalculateListSizeUseCase
import com.thejohnsondev.domain.CalculateListSizeUseCaseImpl
import com.thejohnsondev.domain.CheckFiltersAppliedUseCase
import com.thejohnsondev.domain.CheckFiltersAppliedUseCaseImpl
import com.thejohnsondev.domain.EnterAdditionalFieldTitleUseCase
import com.thejohnsondev.domain.EnterAdditionalFieldTitleUseCaseImpl
import com.thejohnsondev.domain.EnterAdditionalFieldValueUseCase
import com.thejohnsondev.domain.EnterAdditionalFieldValueUseCaseImpl
import com.thejohnsondev.domain.ItemTypeFilterChangeUseCase
import com.thejohnsondev.domain.ItemTypeFilterChangeUseCaseImpl
import com.thejohnsondev.domain.PasswordsService
import com.thejohnsondev.domain.PasswordsServiceImpl
import com.thejohnsondev.domain.RemoveAdditionalFieldUseCase
import com.thejohnsondev.domain.RemoveAdditionalFieldUseCaseImpl
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
    singleOf(::AddAdditionalFieldUseCaseImpl) { bind<AddAdditionalFieldUseCase>() }
    singleOf(::EnterAdditionalFieldTitleUseCaseImpl) { bind<EnterAdditionalFieldTitleUseCase>() }
    singleOf(::EnterAdditionalFieldValueUseCaseImpl) { bind<EnterAdditionalFieldValueUseCase>() }
    singleOf(::RemoveAdditionalFieldUseCaseImpl) { bind<RemoveAdditionalFieldUseCase>() }
}