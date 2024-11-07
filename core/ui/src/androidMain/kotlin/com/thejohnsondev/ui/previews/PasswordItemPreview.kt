package com.thejohnsondev.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thejohnsondev.model.vault.AdditionalFieldModel
import com.thejohnsondev.model.vault.CategoryModel
import com.thejohnsondev.model.vault.PasswordUIModel
import com.thejohnsondev.ui.components.PasswordItem
import com.thejohnsondev.ui.designsystem.VaultTheme

@Composable
@Preview
fun PasswordItemSimplePreview() {
    VaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        PasswordItem(
            item = PasswordUIModel(
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
                createdTime = "2023-10-01T12:00:00Z",
                modifiedTime = "2023-10-02T12:00:00Z",
                isFavorite = true,
                category = CategoryModel(
                    id = "personal",
                    name = "Personal",
                    iconId = 1,
                    colorHex = "#FFFFFF"
                )
            ),
            isReordering = false,
            onClick = {},
            onCopySensitiveClick = {},
            onCopyClick = {},
            onDeleteClick = {},
            onEditClick = {},
            onFavoriteClick = {})
    }
}

@Composable
@Preview
fun PasswordItemSimpleLongNamesPreview() {
    VaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        PasswordItem(
            item = PasswordUIModel(
                id = "12345",
                organization = "Example Organization 1 Long Name",
                organizationLogo = "https://example.com/logo1.png",
                title = "Example Title 1 Long name Long name",
                password = "examplePassword123",
                additionalFields = listOf(
                    AdditionalFieldModel(
                        id = "1",
                        title = "exampleField1",
                        value = "exampleValue1"
                    ),
                    AdditionalFieldModel(id = "2", title = "exampleField2", value = "exampleValue2")
                ),
                createdTime = "2023-10-01T12:00:00Z",
                modifiedTime = "2023-10-02T12:00:00Z",
                isFavorite = true,
                category = CategoryModel(
                    id = "personal",
                    name = "Personal",
                    iconId = 1,
                    colorHex = "#FFFFFF"
                )
            ),
            isReordering = false,
            onClick = {},
            onCopySensitiveClick = {},
            onCopyClick = {},
            onDeleteClick = {},
            onEditClick = {},
            onFavoriteClick = {})
    }
}

@Composable
@Preview
fun PasswordItemFavoritePreview() {
    VaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        PasswordItem(
            item = PasswordUIModel(
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
                createdTime = "2023-10-01T12:00:00Z",
                modifiedTime = "2023-10-02T12:00:00Z",
                isFavorite = true,
                category = CategoryModel(
                    id = "personal",
                    name = "Personal",
                    iconId = 1,
                    colorHex = "#FFFFFF"
                )
            ),
            isFavorite = true,
            isReordering = false,
            onClick = {},
            onCopySensitiveClick = {},
            onCopyClick = {},
            onDeleteClick = {},
            onEditClick = {},
            onFavoriteClick = {})
    }
}

@Composable
@Preview
fun PasswordItemExpandedPreview() {
    VaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        PasswordItem(
            item = PasswordUIModel(
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
            isExpanded = true,
            isFavorite = true,
            isReordering = false,
            onClick = {},
            onCopySensitiveClick = {},
            onCopyClick = {},
            onDeleteClick = {},
            onEditClick = {},
            onFavoriteClick = {})
    }
}

@Composable
@Preview
fun PasswordItemReorderingPreview() {
    VaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        PasswordItem(
            item = PasswordUIModel(
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
                createdTime = "2023-10-01T12:00:00Z",
                modifiedTime = "2023-10-02T12:00:00Z",
                isFavorite = true,
                category = CategoryModel(
                    id = "personal",
                    name = "Personal",
                    iconId = 1,
                    colorHex = "#FFFFFF"
                )
            ),
            isExpanded = false,
            isFavorite = false,
            isReordering = true,
            onClick = {},
            onCopySensitiveClick = {},
            onCopyClick = {},
            onDeleteClick = {},
            onEditClick = {},
            onFavoriteClick = {})
    }
}

@Composable
@Preview
fun PasswordItemDraggingPreview() {
    VaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        PasswordItem(
            item = PasswordUIModel(
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
                createdTime = "2023-10-01T12:00:00Z",
                modifiedTime = "2023-10-02T12:00:00Z",
                isFavorite = true,
                category = CategoryModel(
                    id = "personal",
                    name = "Personal",
                    iconId = 1,
                    colorHex = "#FFFFFF"
                )
            ),
            isExpanded = false,
            isFavorite = false,
            isReordering = true,
            isDragging = true,
            onClick = {},
            onCopySensitiveClick = {},
            onCopyClick = {},
            onDeleteClick = {},
            onEditClick = {},
            onFavoriteClick = {})
    }
}