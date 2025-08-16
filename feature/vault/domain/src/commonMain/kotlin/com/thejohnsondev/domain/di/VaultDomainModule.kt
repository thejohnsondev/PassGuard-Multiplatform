package com.thejohnsondev.domain.di

import com.thejohnsondev.domain.AddAdditionalFieldUseCase
import com.thejohnsondev.domain.service.AppliedFiltersService
import com.thejohnsondev.domain.service.AppliedFiltersServiceImpl
import com.thejohnsondev.domain.CalculateListSizeUseCase
import com.thejohnsondev.domain.CheckFiltersAppliedUseCase
import com.thejohnsondev.domain.DecryptPasswordsListUseCase
import com.thejohnsondev.domain.EncryptPasswordModelUseCase
import com.thejohnsondev.domain.EnterAdditionalFieldTitleUseCase
import com.thejohnsondev.domain.EnterAdditionalFieldValueUseCase
import com.thejohnsondev.domain.ExtractCompanyNameUseCase
import com.thejohnsondev.domain.FilterItemsUseCase
import com.thejohnsondev.domain.FindLogoUseCase
import com.thejohnsondev.domain.GeneratePasswordModelUseCase
import com.thejohnsondev.domain.GetSelectedFiltersIDsUseCase
import com.thejohnsondev.domain.ItemFilterChangeUseCase
import com.thejohnsondev.domain.PasswordsMapToUiModelsUseCase
import com.thejohnsondev.domain.service.PasswordsService
import com.thejohnsondev.domain.service.PasswordsServiceImpl
import com.thejohnsondev.domain.RemoveAdditionalFieldUseCase
import com.thejohnsondev.domain.SearchItemsUseCase
import com.thejohnsondev.domain.SortOrderChangeUseCase
import com.thejohnsondev.domain.SortVaultItemsUseCase
import com.thejohnsondev.domain.SplitItemsListUseCase
import com.thejohnsondev.domain.StopModifiedItemAnimUseCase
import com.thejohnsondev.domain.ToggleOpenedItemUseCase
import com.thejohnsondev.domain.UpdateSelectedFiltersUseCase
import com.thejohnsondev.domain.ValidatePasswordModelUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val vaultDomainModule = module {
    singleOf(::PasswordsServiceImpl) { bind<PasswordsService>() }
    singleOf(::AppliedFiltersServiceImpl) { bind<AppliedFiltersService>() }
    single { ToggleOpenedItemUseCase() }
    single { CalculateListSizeUseCase() }
    single { SplitItemsListUseCase() }
    single { SearchItemsUseCase() }
    single { ItemFilterChangeUseCase() }
    single { CheckFiltersAppliedUseCase() }
    single { AddAdditionalFieldUseCase() }
    single { EnterAdditionalFieldTitleUseCase() }
    single { EnterAdditionalFieldValueUseCase() }
    single { RemoveAdditionalFieldUseCase() }
    single { GeneratePasswordModelUseCase() }
    single { EncryptPasswordModelUseCase(get()) }
    single { DecryptPasswordsListUseCase(get()) }
    single { PasswordsMapToUiModelsUseCase() }
    single { ValidatePasswordModelUseCase() }
    single { FilterItemsUseCase() }
    single { UpdateSelectedFiltersUseCase() }
    single { GetSelectedFiltersIDsUseCase() }
    single { SortVaultItemsUseCase() }
    single { SortOrderChangeUseCase() }
    single { StopModifiedItemAnimUseCase() }
    single { FindLogoUseCase(get()) }
    single { ExtractCompanyNameUseCase() }
}