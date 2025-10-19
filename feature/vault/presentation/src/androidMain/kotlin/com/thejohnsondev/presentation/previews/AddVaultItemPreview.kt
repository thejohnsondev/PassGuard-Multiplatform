package com.thejohnsondev.presentation.previews

import android.annotation.SuppressLint
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
import com.thejohnsondev.common.model.ScreenState
import com.thejohnsondev.presentation.additem.AddVaultItemContent
import com.thejohnsondev.presentation.additem.AddVaultItemViewModel
import com.thejohnsondev.presentation.component.DomainSuggestion
import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel
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
            enteredTitle = mutableStateOf(String.empty),
            enteredDomain = mutableStateOf(String.empty),
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

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AddVaultItemNotEmptyPreview() {
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
                suggestion = DomainSuggestion(
                    url = "https://www.github.com",
                    logoUrl = "https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png"
                )
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
            enteredDomain = mutableStateOf(String.empty),
            vaultItem = PasswordUIModel.testPasswordUIModel,
        )
        LaunchedEffect(true) {
            sheetState.show()
        }
    }
}