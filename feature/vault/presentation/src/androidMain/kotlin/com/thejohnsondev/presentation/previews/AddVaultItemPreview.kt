package com.thejohnsondev.presentation.previews

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.thejohnsondev.common.empty
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.model.auth.logo.FindLogoResponse
import com.thejohnsondev.presentation.additem.AddVaultItemContent
import com.thejohnsondev.presentation.additem.AddVaultItemViewModel
import com.thejohnsondev.ui.designsystem.colorscheme.VaultDefaultTheme
import com.thejohnsondev.ui.model.FilterUIModel.Companion.mapToCategory
import com.thejohnsondev.ui.model.PasswordUIModel
import com.thejohnsondev.ui.model.filterlists.FiltersProvider

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
            enteredTitle = mutableStateOf(String.empty),
            enteredUserName = mutableStateOf(String.empty),
            enteredPassword = mutableStateOf("Pass123$"),
            additionalFields = mutableStateOf(listOf()),
            vaultItem = PasswordUIModel.testPasswordUIModel,
        )
        LaunchedEffect(true) {
            sheetState.show()
        }
    }
}