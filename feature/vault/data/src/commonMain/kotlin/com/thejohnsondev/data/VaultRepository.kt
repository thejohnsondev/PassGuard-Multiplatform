package com.thejohnsondev.data

interface VaultRepository {
    suspend fun updateAppliedItemTypeFilters(typeFilters: List<String>)
    suspend fun updateAppliedCategoryFilters(categoryFilters: List<String>)
    suspend fun getAppliedItemTypeFilters(): List<String>
    suspend fun getAppliedItemCategoryFilters(): List<String>
}