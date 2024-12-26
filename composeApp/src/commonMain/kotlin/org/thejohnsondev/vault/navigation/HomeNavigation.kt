package org.thejohnsondev.vault.navigation

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.presentation.navigation.navigateToWelcome
import com.thejohnsondev.presentation.navigation.settingsScreen
import com.thejohnsondev.presentation.navigation.toolsScreen
import com.thejohnsondev.presentation.navigation.vaultScreen
import com.thejohnsondev.ui.designsystem.SizeDefault
import com.thejohnsondev.ui.model.ScaffoldConfig
import com.thejohnsondev.ui.model.message.MessageContent
import com.thejohnsondev.ui.scaffold.HomeScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNavigation(
    windowSizeClass: WindowWidthSizeClass,
    rootNavController: NavHostController
) {
    val navController = rememberNavController()
    val bottomBarState = rememberSaveable {
        (mutableStateOf(true))
    }
    val scaffoldState = remember {
        mutableStateOf(ScaffoldConfig())
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val showMessageState = remember {
        mutableStateOf<MessageContent?>(null)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        HomeScaffold(
            windowSize = windowSizeClass,
            scaffoldState = scaffoldState,
            navController = navController,
            bottomBarState = bottomBarState,
            scrollBehavior = scrollBehavior,
            showMessageState = showMessageState
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Routes.VaultRoute,
                modifier = Modifier.padding(
                    top = SizeDefault,
                    start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                    end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                    bottom = SizeDefault
                )
            ) {
                vaultScreen(
                    windowSize = windowSizeClass,
                    paddingValues = paddingValues,
                    setScaffoldConfig = {
                        scaffoldState.value = it
                    },
                    updateIsEmptyVault = {
                        scaffoldState.value = scaffoldState.value.copy(isEmptyVaultScreen = it)
                    },
                    onShowMessage = {
                        showMessageState.value = it
                    }
                )
                toolsScreen(
                    windowSize = windowSizeClass,
                    setScaffoldConfig = {
                        scaffoldState.value = it
                    }
                )
                settingsScreen(
                    windowSize = windowSizeClass,
                    setScaffoldConfig = {
                        scaffoldState.value = it
                    },
                    onLogoutClick = {
                        rootNavController.navigateToWelcome()
                    }
                )
            }
        }
    }
}