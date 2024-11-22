package com.thejohnsondev.model.vault


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
    override val categoryId: String,
) : VaultItemDto
