package com.thejohnsondev.model.vault

import com.thejohnsondev.model.vault.CategoryModel.Companion.DEFAULT_CATEGORY_ID

data class PasswordDto(
    val organization: String,
    val organizationLogo: String? = null,
    val title: String,
    val password: String,
    val additionalFields: List<AdditionalFieldModel> = emptyList(),
    override val id: String?,
    override val createdTimeStamp: String? = null,
    override val modifiedTimeStamp: String? = null,
    override val isFavorite: Boolean = false, // Ignore on the server side
    override val categoryId: String = DEFAULT_CATEGORY_ID,
) : VaultItem
