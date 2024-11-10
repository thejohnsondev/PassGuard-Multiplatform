package com.thejohnsondev.domain.models

import com.thejohnsondev.model.vault.AdditionalFieldModel
import com.thejohnsondev.model.vault.CategoryModel

data class PasswordUIModel(
    val id: String?,
    val organization: String,
    val organizationLogo: String? = null,
    val title: String,
    val password: String,
    val additionalFields: List<AdditionalFieldModel> = emptyList(),
    val createdTime: String,
    val modifiedTime: String?,
    val isFavorite: Boolean,
    val isExpanded: Boolean = false,
    val category: CategoryModel,
) {
    companion object {
        val testPasswordItems = listOf(
            PasswordUIModel(
                id = "12345",
                organization = "Example Organization 1",
                organizationLogo = "https://example.com/logo1.png",
                title = "Example Title 1",
                password = "examplePassword123",
                additionalFields = listOf(
                    AdditionalFieldModel(
                        id = "1",
                        title = "exampleField1",
                        value = "exampleValue1"
                    ),
                    AdditionalFieldModel(id = "2", title = "exampleField2", value = "exampleValue2")
                ),
                modifiedTime = "November 2 2024 20:01",
                createdTime = "November 1 2024 10:22",
                isFavorite = false,
                category = CategoryModel(
                    id = "personal",
                    name = "Personal",
                    iconId = 1,
                    colorHex = "#FFFFFF"
                )
            ),
            PasswordUIModel(
                id = "67890",
                organization = "Example Organization 2",
                organizationLogo = null,
                title = "Example Title 2",
                password = "examplePassword456",
                additionalFields = listOf(
                    AdditionalFieldModel(
                        id = "3",
                        title = "exampleField3",
                        value = "exampleValue3"
                    ),
                    AdditionalFieldModel(id = "4", title = "exampleField4", value = "exampleValue4")
                ),
                createdTime = "2023-10-03T12:00:00Z",
                modifiedTime = "2023-10-04T12:00:00Z",
                isFavorite = true,
                category = CategoryModel(
                    id = "work",
                    name = "Work",
                    iconId = 2,
                    colorHex = "#0000FF"
                )
            ),
            PasswordUIModel(
                id = "11223",
                organization = "Example Organization 3",
                organizationLogo = null,
                title = "Example Title 3",
                password = "examplePassword789",
                additionalFields = listOf(
                    AdditionalFieldModel(
                        id = "5",
                        title = "exampleField5",
                        value = "exampleValue5"
                    ),
                    AdditionalFieldModel(id = "6", title = "exampleField6", value = "exampleValue6")
                ),
                createdTime = "2023-10-05T12:00:00Z",
                modifiedTime = "2023-10-06T12:00:00Z",
                isFavorite = true,
                category = CategoryModel(
                    id = "finance",
                    name = "Finance",
                    iconId = 3,
                    colorHex = "#00FF00"
                )
            ),
            PasswordUIModel(
                id = "44556",
                organization = "Example Organization 4",
                organizationLogo = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Google_%22G%22_logo.svg/1200px-Google_%22G%22_logo.svg.png",
                title = "Example Title 4",
                password = "examplePassword012",
                additionalFields = listOf(
                    AdditionalFieldModel(
                        id = "7",
                        title = "exampleField7",
                        value = "exampleValue7"
                    ),
                    AdditionalFieldModel(id = "8", title = "exampleField8", value = "exampleValue8")
                ),
                createdTime = "2023-10-07T12:00:00Z",
                modifiedTime = "2023-10-08T12:00:00Z",
                isFavorite = false,
                category = CategoryModel(
                    id = "social",
                    name = "Social",
                    iconId = 4,
                    colorHex = "#FF0000"
                )
            )
        )
    }
}