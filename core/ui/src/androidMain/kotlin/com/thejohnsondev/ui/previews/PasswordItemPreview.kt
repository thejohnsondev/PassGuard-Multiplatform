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
fun VaultScreenPreviewWithPasswords() {
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
            onEditClick = {})
    }
}