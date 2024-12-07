package com.thejohnsondev.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.thejohnsondev.ui.model.ScaffoldConfig
import com.thejohnsondev.ui.scaffold.BottomNavItem
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.vectorResource
import vaultmultiplatform.feature.tools.presentation.generated.resources.Res
import vaultmultiplatform.feature.tools.presentation.generated.resources.ic_tools
import vaultmultiplatform.feature.tools.presentation.generated.resources.tools

@Composable
fun ToolsScreen(
    windowSizeClass: WindowWidthSizeClass,
    setScaffoldConfig: (ScaffoldConfig) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        val snackBarHostState = remember {
            SnackbarHostState()
        }
        val toolsIcon = vectorResource(Res.drawable.ic_tools)
        LaunchedEffect(true) {
            setScaffoldConfig(
                ScaffoldConfig(
                    topAppBarTitle = getString(Res.string.tools),
                    topAppBarIcon = toolsIcon,
                    snackBarHostState = snackBarHostState,
                    bottomBarItemIndex = BottomNavItem.Tools.index
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = "Tools Screen")

        }

    }

}