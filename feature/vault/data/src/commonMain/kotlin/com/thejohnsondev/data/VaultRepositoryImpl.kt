package com.thejohnsondev.data

import arrow.core.Either
import com.thejohnsondev.datastore.PreferencesDataStore
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.logo.FindLogoResponse
import com.thejohnsondev.network.logo.LogoApi

class VaultRepositoryImpl(
    private val preferencesDataStore: PreferencesDataStore,
    private val logoApi: LogoApi
): VaultRepository {
    override suspend fun updateAppliedItemTypeFilters(typeFilters: List<String>) {
        preferencesDataStore.updateAppliedItemTypeFilters(typeFilters)
    }

    override suspend fun updateAppliedCategoryFilters(categoryFilters: List<String>) {
        preferencesDataStore.updateAppliedCategoryFilters(categoryFilters)
    }

    override suspend fun getAppliedItemTypeFilters(): List<String> {
        return preferencesDataStore.getAppliedItemTypeFilters()
    }

    override suspend fun getAppliedItemCategoryFilters(): List<String> {
        return preferencesDataStore.getAppliedCategoryFilters()
    }

    override suspend fun updateAppliedSortOrder(sortOrder: String) {
        preferencesDataStore.updateAppliedSortOrder(sortOrder)
    }

    override suspend fun getAppliedSortOrder(): String {
        return preferencesDataStore.getAppliedSortOrder()
    }

    override suspend fun updateAppliedShowFavoritesAtTop(showFavoritesAtTop: Boolean) {
        preferencesDataStore.updateAppliedShowFavoritesAtTop(showFavoritesAtTop)
    }

    override suspend fun getAppliedShowFavoritesAtTop(): Boolean {
        return preferencesDataStore.getAppliedShowFavoritesAtTop()
    }

    override suspend fun findLogo(query: String): Either<Error, List<FindLogoResponse>> {
        return logoApi.find(query)
    }

    override suspend fun updateOpenedFilters(opened: Boolean) {
        preferencesDataStore.updateOpenedFilters(opened)
    }

    override suspend fun getIsOpenedFilters(): Boolean {
        return preferencesDataStore.getIsOpenedFilters()
    }
}