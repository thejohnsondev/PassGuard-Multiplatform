package com.thejohnsondev.model.vault


data class PasswordDto(
    val title: String,
    val organizationLogo: String? = null,
    val domain: String? = null,
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
) : VaultItemDto {

    companion object {
        val demo1 = PasswordDto(
            title = "Demo Password",
            organizationLogo = null,
            userName = "Username",
            password = "Password",
            additionalFields = listOf(
                AdditionalFieldDto(
                    id = "1",
                    title = "Field 1",
                    value = "Value 1"
                ),
                AdditionalFieldDto(
                    id = "2",
                    title = "Field 2",
                    value = "Value 2"
                )
            ),
            id = "1",
            createdTimeStamp = "2023-10-01T12:00:00Z",
            modifiedTimeStamp = "2023-10-01T12:00:00Z",
            syncedTimeStamp = "2023-10-01T12:00:00Z",
            syncStatus = "synced",
            isFavorite = false,
            categoryId = "item_category_personal"
        )
    }

}
