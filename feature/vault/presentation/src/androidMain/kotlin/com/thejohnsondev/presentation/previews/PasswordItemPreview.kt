package com.thejohnsondev.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.ui.components.vault.PasswordItem
import com.thejohnsondev.ui.designsystem.colorscheme.VaultDefaultTheme
import com.thejohnsondev.ui.model.PasswordUIModel
import com.thejohnsondev.ui.model.filterlists.FiltersProvider
import com.thejohnsondev.ui.model.FilterUIModel.Companion.mapToCategory

@Composable
@Preview(showBackground = true)
fun PasswordItemSimplePreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        PasswordItem(
            item = PasswordUIModel(
                id = "12345",
                title = "Example Organization 1",
                organizationLogo = "https://example.com/logo1.png",
                userName = "Example Title 1",
                password = "examplePassword123",
                additionalFields = listOf(
                    AdditionalFieldDto(
                        id = "1",
                        title = "exampleField1",
                        value = "exampleValue1"
                    ),
                    AdditionalFieldDto(id = "2", title = "exampleField2", value = "exampleValue2")
                ),
                createdTime = "2023-10-01T12:00:00Z",
                modifiedTime = "2023-10-02T12:00:00Z",
                isFavorite = true,
                category = FiltersProvider.Category.getDefaultCategoryFilter().mapToCategory()
            ),
            isReordering = false,
            onClick = {},
            onCopySensitive = {},
            onCopy = {},
            onDeleteClick = {},
            onEditClick = {},
            onFavoriteClick = {})
    }
}

@Composable
@Preview
fun PasswordItemSimpleLongNamesPreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        PasswordItem(
            item = PasswordUIModel(
                id = "12345",
                title = "Example Organization 1 Long Name",
                organizationLogo = "https://example.com/logo1.png",
                userName = "Example Title 1 Long name Long name",
                password = "examplePassword123",
                additionalFields = listOf(
                    AdditionalFieldDto(
                        id = "1",
                        title = "exampleField1",
                        value = "exampleValue1"
                    ),
                    AdditionalFieldDto(id = "2", title = "exampleField2", value = "exampleValue2")
                ),
                createdTime = "2023-10-01T12:00:00Z",
                modifiedTime = "2023-10-02T12:00:00Z",
                isFavorite = true,
                category = FiltersProvider.Category.getDefaultCategoryFilter().mapToCategory()
            ),
            isReordering = false,
            onClick = {},
            onCopySensitive = {},
            onCopy = {},
            onDeleteClick = {},
            onEditClick = {},
            onFavoriteClick = {})
    }
}

@Composable
@Preview
fun PasswordItemFavoritePreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        PasswordItem(
            item = PasswordUIModel(
                id = "12345",
                title = "Example Organization 1",
                organizationLogo = "https://example.com/logo1.png",
                userName = "Example Title 1",
                password = "examplePassword123",
                additionalFields = listOf(
                    AdditionalFieldDto(
                        id = "1",
                        title = "exampleField1",
                        value = "exampleValue1"
                    ),
                    AdditionalFieldDto(id = "2", title = "exampleField2", value = "exampleValue2")
                ),
                createdTime = "2023-10-01T12:00:00Z",
                modifiedTime = "2023-10-02T12:00:00Z",
                isFavorite = true,
                category = FiltersProvider.Category.getDefaultCategoryFilter().mapToCategory()
            ),
            isFavorite = true,
            isReordering = false,
            onClick = {},
            onCopySensitive = {},
            onCopy = {},
            onDeleteClick = {},
            onEditClick = {},
            onFavoriteClick = {})
    }
}

@Composable
@Preview
fun PasswordItemExpandedPreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        PasswordItem(
            item = PasswordUIModel(
                id = "12345",
                title = "Example Organization 1",
                organizationLogo = "https://example.com/logo1.png",
                userName = "Example Title 1",
                password = "examplePassword123",
                additionalFields = listOf(
                    AdditionalFieldDto(
                        id = "1",
                        title = "exampleField1",
                        value = "exampleValue1"
                    ),
                    AdditionalFieldDto(id = "2", title = "exampleField2", value = "exampleValue2")
                ),
                modifiedTime = "November 2 2024 20:01",
                createdTime = "November 1 2024 10:22",
                isFavorite = false,
                category = FiltersProvider.Category.getDefaultCategoryFilter().mapToCategory()
            ),
            isExpanded = true,
            isFavorite = true,
            isReordering = false,
            onClick = {},
            onCopySensitive = {},
            onCopy = {},
            onDeleteClick = {},
            onEditClick = {},
            onFavoriteClick = {})
    }
}

@Composable
@Preview
fun PasswordItemReorderingPreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        PasswordItem(
            item = PasswordUIModel(
                id = "12345",
                title = "Example Organization 1",
                organizationLogo = "https://example.com/logo1.png",
                userName = "Example Title 1",
                password = "examplePassword123",
                additionalFields = listOf(
                    AdditionalFieldDto(
                        id = "1",
                        title = "exampleField1",
                        value = "exampleValue1"
                    ),
                    AdditionalFieldDto(id = "2", title = "exampleField2", value = "exampleValue2")
                ),
                createdTime = "2023-10-01T12:00:00Z",
                modifiedTime = "2023-10-02T12:00:00Z",
                isFavorite = true,
                category = FiltersProvider.Category.getDefaultCategoryFilter().mapToCategory()
            ),
            isExpanded = false,
            isFavorite = false,
            isReordering = true,
            onClick = {},
            onCopySensitive = {},
            onCopy = {},
            onDeleteClick = {},
            onEditClick = {},
            onFavoriteClick = {})
    }
}

@Composable
@Preview
fun PasswordItemDraggingPreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        PasswordItem(
            item = PasswordUIModel(
                id = "12345",
                title = "Example Organization 1",
                organizationLogo = "https://example.com/logo1.png",
                userName = "Example Title 1",
                password = "examplePassword123",
                additionalFields = listOf(
                    AdditionalFieldDto(
                        id = "1",
                        title = "exampleField1",
                        value = "exampleValue1"
                    ),
                    AdditionalFieldDto(id = "2", title = "exampleField2", value = "exampleValue2")
                ),
                createdTime = "2023-10-01T12:00:00Z",
                modifiedTime = "2023-10-02T12:00:00Z",
                isFavorite = true,
                category = FiltersProvider.Category.getDefaultCategoryFilter().mapToCategory()
            ),
            isExpanded = false,
            isFavorite = false,
            isReordering = true,
            isDragging = true,
            onClick = {},
            onCopySensitive = {},
            onCopy = {},
            onDeleteClick = {},
            onEditClick = {},
            onFavoriteClick = {})
    }
}