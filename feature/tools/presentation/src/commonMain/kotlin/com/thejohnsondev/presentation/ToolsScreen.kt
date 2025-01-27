package com.thejohnsondev.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
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
import com.thejohnsondev.ui.utils.ResDrawable
import com.thejohnsondev.ui.utils.ResString
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import vaultmultiplatform.core.ui.generated.resources.coming_soon
import vaultmultiplatform.core.ui.generated.resources.ic_tools
import vaultmultiplatform.core.ui.generated.resources.tools

@Composable
fun ToolsScreen(
    windowSizeClass: WindowWidthSizeClass,
    setScaffoldConfig: (ScaffoldConfig) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceContainerLowest
    ) {
        val snackBarHostState = remember {
            SnackbarHostState()
        }
        val toolsIcon = vectorResource(ResDrawable.ic_tools)
        LaunchedEffect(true) {
            setScaffoldConfig(
                ScaffoldConfig(
                    topAppBarTitle = getString(ResString.tools),
                    topAppBarIcon = toolsIcon,
                    successSnackBarHostState = snackBarHostState,
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
            Text(text = stringResource(ResString.coming_soon))
        }

    }

}