package com.thejohnsondev.data

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.logo.FindLogoResponse

interface VaultRepository {
    suspend fun updateAppliedItemTypeFilters(typeFilters: List<String>)
    suspend fun updateAppliedCategoryFilters(categoryFilters: List<String>)
    suspend fun getAppliedItemTypeFilters(): List<String>
    suspend fun getAppliedItemCategoryFilters(): List<String>
    suspend fun updateAppliedSortOrder(sortOrder: String)
    suspend fun getAppliedSortOrder(): String
    suspend fun updateAppliedShowFavoritesAtTop(showFavoritesAtTop: Boolean)
    suspend fun getAppliedShowFavoritesAtTop(): Boolean
    suspend fun findLogo(query: String): Either<Error, List<FindLogoResponse>>
}