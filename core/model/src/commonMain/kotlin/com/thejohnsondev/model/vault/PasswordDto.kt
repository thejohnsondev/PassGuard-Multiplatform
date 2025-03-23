package com.thejohnsondev.model.vault


data class PasswordDto(
    val title: String,
    val organizationLogo: String? = null,
    val userName: String,
    val password: String,
    val additionalFields: List<AdditionalFieldDto> = emptyList(),
    override val id: String,
    override val createdTimeStamp: String? = null,
    override val modifiedTimeStamp: String? = null,
    override val syncedTimeStamp: String? = null,
    override val syncStatus: String? = null,
    override val isFavorite: Boolean = false, // Ignore on the server side
    override val categoryId: String,
) : VaultItemDto
