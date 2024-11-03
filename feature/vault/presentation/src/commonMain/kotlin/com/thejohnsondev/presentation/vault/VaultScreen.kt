package com.thejohnsondev.presentation.vault

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.getAppLogo
import com.thejohnsondev.ui.model.ScaffoldConfig
import com.thejohnsondev.ui.scaffold.BottomNavItem
import com.thejohnsondev.ui.utils.isCompact
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.vectorResource
import vaultmultiplatform.feature.vault.presentation.generated.resources.Res
import vaultmultiplatform.feature.vault.presentation.generated.resources.add
import vaultmultiplatform.feature.vault.presentation.generated.resources.vault

@Composable
fun VaultScreen(
    windowSizeClass: WindowWidthSizeClass,
    viewModel: VaultViewModel,
    paddingValues: PaddingValues,
    setScaffoldConfig: (ScaffoldConfig) -> Unit,
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val listState = rememberLazyListState()
    val expandedFab by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0
        }
    }
    val appLogo = vectorResource(getAppLogo())

    LaunchedEffect(true) {
        setScaffoldConfig(
            ScaffoldConfig(
                topAppBarTitle = getString(Res.string.vault),
                topAppBarIcon = appLogo,
                isFabVisible = true,
                fabTitle = getString(Res.string.add),
                fabIcon = Icons.Default.Add,
                onFabClick = {
                    // TODO handle this
                },
                isFabExpanded = expandedFab,
                snackBarHostState = snackBarHostState,
                bottomBarItemIndex = BottomNavItem.Vault.index
            )
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = listState
        ) {
            item {
                Spacer(modifier = Modifier.padding(top = paddingValues.calculateTopPadding()))
            }
            items(List(100) { it + 1 }) {
                Canvas(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(Size16)

                ) {
                    drawRect(
                        color = listOf(Color.Red, Color.Blue, Color.Green, Color.Cyan).random(),
                        size = size
                    )
                }
            }
            if (windowSizeClass.isCompact()) {
                item {
                    Spacer(modifier = Modifier.padding(top = paddingValues.calculateBottomPadding()))
                }
            }
        }
    }
}