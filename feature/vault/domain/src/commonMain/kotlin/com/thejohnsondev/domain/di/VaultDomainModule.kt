package com.thejohnsondev.domain.di

import com.thejohnsondev.domain.AddAdditionalFieldUseCase
import com.thejohnsondev.domain.AddAdditionalFieldUseCaseImpl
import com.thejohnsondev.domain.AppliedFiltersService
import com.thejohnsondev.domain.AppliedFiltersServiceImpl
import com.thejohnsondev.domain.CalculateListSizeUseCase
import com.thejohnsondev.domain.CalculateListSizeUseCaseImpl
import com.thejohnsondev.domain.CheckFiltersAppliedUseCase
import com.thejohnsondev.domain.CheckFiltersAppliedUseCaseImpl
import com.thejohnsondev.domain.CopyTextUseCase
import com.thejohnsondev.domain.CopyTextUseCaseImpl
import com.thejohnsondev.domain.DecryptPasswordsListUseCase
import com.thejohnsondev.domain.DecryptPasswordsListUseCaseImpl
import com.thejohnsondev.domain.EncryptPasswordModelUseCase
import com.thejohnsondev.domain.EncryptPasswordModelUseCaseImpl
import com.thejohnsondev.domain.EnterAdditionalFieldTitleUseCase
import com.thejohnsondev.domain.EnterAdditionalFieldTitleUseCaseImpl
import com.thejohnsondev.domain.EnterAdditionalFieldValueUseCase
import com.thejohnsondev.domain.EnterAdditionalFieldValueUseCaseImpl
import com.thejohnsondev.domain.FilterItemsUseCase
import com.thejohnsondev.domain.FilterItemsUseCaseImpl
import com.thejohnsondev.domain.FindLogoUseCase
import com.thejohnsondev.domain.FindLogoUseCaseImpl
import com.thejohnsondev.domain.GeneratePasswordModelUseCase
import com.thejohnsondev.domain.GeneratePasswordModelUseCaseImpl
import com.thejohnsondev.domain.GetSelectedFiltersIDsUseCase
import com.thejohnsondev.domain.GetSelectedFiltersIDsUseCaseImpl
import com.thejohnsondev.domain.ItemFilterChangeUseCase
import com.thejohnsondev.domain.ItemFilterChangeUseCaseImpl
import com.thejohnsondev.domain.PasswordsMapToUiModelsUseCase
import com.thejohnsondev.domain.PasswordsMapToUiModelsUseCaseImpl
import com.thejohnsondev.domain.PasswordsService
import com.thejohnsondev.domain.PasswordsServiceImpl
import com.thejohnsondev.domain.RemoveAdditionalFieldUseCase
import com.thejohnsondev.domain.RemoveAdditionalFieldUseCaseImpl
import com.thejohnsondev.domain.SearchItemsUseCase
import com.thejohnsondev.domain.SearchItemsUseCaseImpl
import com.thejohnsondev.domain.SortOrderChangeUseCase
import com.thejohnsondev.domain.SortOrderChangeUseCaseImpl
import com.thejohnsondev.domain.SortVaultItemsUseCase
import com.thejohnsondev.domain.SortVaultItemsUseCaseImpl
import com.thejohnsondev.domain.SplitItemsListUseCase
import com.thejohnsondev.domain.SplitItemsListUseCaseImpl
import com.thejohnsondev.domain.StopModifiedItemAnimUseCase
import com.thejohnsondev.domain.StopModifiedItemAnimUseCaseImpl
import com.thejohnsondev.domain.ToggleOpenedItemUseCase
import com.thejohnsondev.domain.ToggleOpenedItemUseCaseImpl
import com.thejohnsondev.domain.UpdateSelectedFiltersUseCase
import com.thejohnsondev.domain.UpdateSelectedFiltersUseCaseImpl
import com.thejohnsondev.domain.ValidatePasswordModelUseCase
import com.thejohnsondev.domain.ValidatePasswordModelUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val vaultDomainModule = module {
    singleOf(::PasswordsServiceImpl) { bind<PasswordsService>() }
    singleOf(::ToggleOpenedItemUseCaseImpl) { bind<ToggleOpenedItemUseCase>() }
    singleOf(::CalculateListSizeUseCaseImpl) { bind<CalculateListSizeUseCase>() }
    singleOf(::SplitItemsListUseCaseImpl) { bind<SplitItemsListUseCase>() }
    singleOf(::SearchItemsUseCaseImpl) { bind<SearchItemsUseCase>() }
    singleOf(::ItemFilterChangeUseCaseImpl) { bind<ItemFilterChangeUseCase>() }
    singleOf(::CheckFiltersAppliedUseCaseImpl) { bind<CheckFiltersAppliedUseCase>() }
    singleOf(::AddAdditionalFieldUseCaseImpl) { bind<AddAdditionalFieldUseCase>() }
    singleOf(::EnterAdditionalFieldTitleUseCaseImpl) { bind<EnterAdditionalFieldTitleUseCase>() }
    singleOf(::EnterAdditionalFieldValueUseCaseImpl) { bind<EnterAdditionalFieldValueUseCase>() }
    singleOf(::RemoveAdditionalFieldUseCaseImpl) { bind<RemoveAdditionalFieldUseCase>() }
    singleOf(::GeneratePasswordModelUseCaseImpl) { bind<GeneratePasswordModelUseCase>() }
    singleOf(::EncryptPasswordModelUseCaseImpl) { bind<EncryptPasswordModelUseCase>() }
    singleOf(::DecryptPasswordsListUseCaseImpl) { bind<DecryptPasswordsListUseCase>() }
    singleOf(::PasswordsMapToUiModelsUseCaseImpl) { bind<PasswordsMapToUiModelsUseCase>() }
    singleOf(::ValidatePasswordModelUseCaseImpl) { bind<ValidatePasswordModelUseCase>() }
    singleOf(::FilterItemsUseCaseImpl) { bind<FilterItemsUseCase>() }
    singleOf(::AppliedFiltersServiceImpl) { bind<AppliedFiltersService>() }
    singleOf(::UpdateSelectedFiltersUseCaseImpl) { bind<UpdateSelectedFiltersUseCase>() }
    singleOf(::GetSelectedFiltersIDsUseCaseImpl) { bind<GetSelectedFiltersIDsUseCase>() }
    singleOf(::SortVaultItemsUseCaseImpl) { bind<SortVaultItemsUseCase>() }
    singleOf(::SortOrderChangeUseCaseImpl) { bind<SortOrderChangeUseCase>() }
    singleOf(::StopModifiedItemAnimUseCaseImpl) { bind<StopModifiedItemAnimUseCase>() }
    singleOf(::CopyTextUseCaseImpl) { bind<CopyTextUseCase>() }
    singleOf(::FindLogoUseCaseImpl) { bind<FindLogoUseCase>() }
}