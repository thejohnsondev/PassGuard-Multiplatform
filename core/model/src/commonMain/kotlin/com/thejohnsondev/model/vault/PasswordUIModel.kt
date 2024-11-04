package com.thejohnsondev.model.vault

data class PasswordUIModel(
    val id: String?,
    val organization: String,
    val organizationLogo: String? = null,
    val title: String,
    val password: String,
    val additionalFields: List<AdditionalFieldModel> = emptyList(),
    val createdTime: String,
    val modifiedTime: String,
    val isFavorite: Boolean,
    val category: CategoryModel,
)