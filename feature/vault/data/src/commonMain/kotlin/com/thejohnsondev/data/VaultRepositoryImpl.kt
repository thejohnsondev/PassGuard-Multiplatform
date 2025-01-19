package com.thejohnsondev.data

import com.thejohnsondev.datastore.PreferencesDataStore

class VaultRepositoryImpl(
    private val preferencesDataStore: PreferencesDataStore
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
}