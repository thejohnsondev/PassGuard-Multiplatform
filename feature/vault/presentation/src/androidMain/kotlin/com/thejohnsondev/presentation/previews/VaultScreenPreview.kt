package com.thejohnsondev.presentation.previews

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.presentation.vault.VaultScreenContent
import com.thejohnsondev.presentation.vault.VaultViewModel
import com.thejohnsondev.ui.designsystem.colorscheme.VaultDefaultTheme
import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel
import com.thejohnsondev.ui.model.filterlists.FiltersProvider


@Preview
@Composable
private fun VaultPasswordsCompactPreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        VaultScreenContent(
            state = VaultViewModel.State(
                screenState = ScreenState.ShowContent,
                passwordsList = listOf(PasswordUIModel.testPasswordItems)
            ),
            windowSizeClass = WindowWidthSizeClass.Compact,
            paddingValues = PaddingValues(0.dp),
            lazyListState = rememberLazyListState(),
            onAction = {}
        )
    }
}

@Preview(device = "spec:width=1080dp,height=720dp,dpi=160")
@Composable
private fun VaultPasswordsLargePreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        VaultScreenContent(
            state = VaultViewModel.State(
                screenState = ScreenState.ShowContent,
                passwordsList = listOf(
                    PasswordUIModel.testPasswordItems,
                    PasswordUIModel.testPasswordItems
                ),
                listHeight = 1000
            ),
            windowSizeClass = WindowWidthSizeClass.Expanded,
            paddingValues = PaddingValues(0.dp),
            lazyListState = rememberLazyListState(),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun VaultPasswordsFiltersOpenedPreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        VaultScreenContent(
            state = VaultViewModel.State(
                screenState = ScreenState.ShowContent,
                passwordsList = listOf(PasswordUIModel.testPasswordItems),
                itemTypeFilters = FiltersProvider.ItemType.getVaultItemTypeFilters(),
                itemCategoryFilters = FiltersProvider.Category.getVaultCategoryFilters(),
                isFiltersOpened = true
            ),
            windowSizeClass = WindowWidthSizeClass.Compact,
            paddingValues = PaddingValues(0.dp),
            lazyListState = rememberLazyListState(),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun VaultLoadingPreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        VaultScreenContent(
            state = VaultViewModel.State(
                screenState = ScreenState.Loading
            ),
            windowSizeClass = WindowWidthSizeClass.Compact,
            paddingValues = PaddingValues(0.dp),
            lazyListState = rememberLazyListState(),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun VaultEmptyPreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        VaultScreenContent(
            state = VaultViewModel.State(
                screenState = ScreenState.ShowContent,
                isVaultEmpty = true
            ),
            windowSizeClass = WindowWidthSizeClass.Compact,
            paddingValues = PaddingValues(0.dp),
            lazyListState = rememberLazyListState(),
            onAction = {}
        )
    }
}