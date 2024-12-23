package com.thejohnsondev.presentation.previews

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.presentation.additem.AddVaultItemContent
import com.thejohnsondev.presentation.additem.AddVaultItemViewModel
import com.thejohnsondev.ui.designsystem.colorscheme.VaultDefaultTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AddVaultItemEmptyPreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        val sheetState = SheetState(
            skipPartiallyExpanded = true,
            density = Density(1f),
            initialValue = SheetValue.Expanded
        )
        AddVaultItemContent(
            state = AddVaultItemViewModel.State(
                screenState = ScreenState.ShowContent
            ),
            sheetState = sheetState,
            windowSizeClass = WindowWidthSizeClass.Compact,
            paddingValues = PaddingValues(0.dp),
            onDismissRequest = {},
            onAction = {},
            vaultItem = null
        )
        LaunchedEffect(true) {
            sheetState.show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AddVaultItemEditPreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        val sheetState = SheetState(
            skipPartiallyExpanded = true,
            density = Density(1f),
            initialValue = SheetValue.Expanded
        )
        AddVaultItemContent(
            state = AddVaultItemViewModel.State(
                screenState = ScreenState.ShowContent,
                isEdit = true,
                password = "examplePassword123",
                title = "Example Title 1",
                organization = "Example Organization 1",
                isValid = true,
                additionalFields = listOf(
                    AdditionalFieldDto(
                        id = "1",
                        title = "exampleField1",
                        value = "exampleValue1"
                    ),
                    AdditionalFieldDto(
                        id = "2", title = "exampleField2", value = "exampleValue2"
                    )
                )
            ),
            sheetState = sheetState,
            windowSizeClass = WindowWidthSizeClass.Compact,
            paddingValues = PaddingValues(0.dp),
            onDismissRequest = {},
            onAction = {},
            vaultItem = null
        )
    }
}