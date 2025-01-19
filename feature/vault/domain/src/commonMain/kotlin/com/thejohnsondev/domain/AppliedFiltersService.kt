package com.thejohnsondev.domain

interface AppliedFiltersService {
    suspend fun updateAppliedItemTypeFilters(typeFilters: List<String>)
    suspend fun updateAppliedCategoryFilters(categoryFilters: List<String>)
    suspend fun getAppliedItemTypeFilters(): List<String>
    suspend fun getAppliedItemCategoryFilters(): List<String>
}