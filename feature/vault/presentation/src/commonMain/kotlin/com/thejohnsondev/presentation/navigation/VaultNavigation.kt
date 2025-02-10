package com.thejohnsondev.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.presentation.vault.VaultScreen
import com.thejohnsondev.presentation.vault.VaultViewModel
import com.thejohnsondev.ui.model.ScaffoldConfig
import com.thejohnsondev.ui.model.message.MessageContent
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.vaultScreen(
    windowSize: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    setScaffoldConfig: (ScaffoldConfig) -> Unit,
    updateIsEmptyVault: (Boolean) -> Unit,
    updateIsFabExpanded: (Boolean) -> Unit,
    onShowMessage: (MessageContent) -> Unit
) {
    composable<Routes.VaultRoute> {
        val vaultViewModel = koinViewModel<VaultViewModel>()
        VaultScreen(
            windowSize,
            vaultViewModel,
            paddingValues,
            setScaffoldConfig,
            updateIsEmptyVault,
            updateIsFabExpanded,
            onShowMessage
        )
    }
}