package com.thejohnsondev.domain

import com.thejohnsondev.data.VaultRepository

class AppliedFiltersServiceImpl(
    private val vaultRepository: VaultRepository,
) : AppliedFiltersService {

    override suspend fun updateAppliedItemTypeFilters(typeFilters: List<String>) {
        vaultRepository.updateAppliedItemTypeFilters(typeFilters)
    }

    override suspend fun updateAppliedCategoryFilters(categoryFilters: List<String>) {
        vaultRepository.updateAppliedCategoryFilters(categoryFilters)
    }

    override suspend fun getAppliedItemTypeFilters(): List<String> {
        return vaultRepository.getAppliedItemTypeFilters()
    }

    override suspend fun getAppliedItemCategoryFilters(): List<String> {
        return vaultRepository.getAppliedItemCategoryFilters()
    }

    override suspend fun updateAppliedSortOrder(sortOrder: String) {
        vaultRepository.updateAppliedSortOrder(sortOrder)
    }

    override suspend fun getAppliedSortOrder(): String {
        return vaultRepository.getAppliedSortOrder()
    }

    override suspend fun updateAppliedShowFavoritesAtTop(showFavoritesAtTop: Boolean) {
        vaultRepository.updateAppliedShowFavoritesAtTop(showFavoritesAtTop)
    }

    override suspend fun getAppliedShowFavoritesAtTop(): Boolean {
        return vaultRepository.getAppliedShowFavoritesAtTop()
    }

}