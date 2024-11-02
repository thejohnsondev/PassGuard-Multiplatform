package org.thejohnsondev.vault.navigation

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.thejohnsondev.common.navigation.Screens
import com.thejohnsondev.presentation.navigation.navigateToWelcome
import com.thejohnsondev.presentation.navigation.vaultScreen
import com.thejohnsondev.ui.model.ScaffoldConfig
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
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screens.VaultScreen.name,
                modifier = Modifier.padding(paddingValues)
            ) {
                vaultScreen(
                    windowSize = windowSizeClass,
                    onClick = {
                        rootNavController.navigateToWelcome()
                    },
                    setScaffoldConfig = {
                        scaffoldState.value = it
                    }
                )
            }
        }
    }
}