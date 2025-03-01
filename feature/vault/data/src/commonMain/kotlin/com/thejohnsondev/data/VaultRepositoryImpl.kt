package com.thejohnsondev.data

import com.thejohnsondev.datastore.PreferencesDataStore
import com.thejohnsondev.platform.utils.ClipboardUtils

class VaultRepositoryImpl(
    private val preferencesDataStore: PreferencesDataStore,
    private val clipboardUtils: ClipboardUtils
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

    override fun copyText(text: String, isSensitive: Boolean) {
        if (isSensitive) {
            clipboardUtils.copyToClipboardSensitive(text)
        } else {
            clipboardUtils.copyToClipboard(text)
        }
    }
}