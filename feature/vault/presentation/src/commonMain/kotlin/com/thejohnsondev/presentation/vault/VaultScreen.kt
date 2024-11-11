package com.thejohnsondev.presentation.vault

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.thejohnsondev.domain.models.PasswordUIModel
import com.thejohnsondev.presentation.component.PasswordItem
import com.thejohnsondev.ui.designsystem.Size68
import com.thejohnsondev.ui.designsystem.Size8
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
    val state = viewModel.state.collectAsState(VaultViewModel.State())
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val lazyListState = rememberLazyListState()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()
    val expandedFab by remember {
        derivedStateOf {
            if (windowSizeClass.isCompact()) {
                lazyListState.firstVisibleItemIndex == 0
            } else {
                lazyStaggeredGridState.firstVisibleItemIndex == 0
            }
        }
    }
    val appLogo = vectorResource(getAppLogo())

    LaunchedEffect(true) {
        viewModel.perform(VaultViewModel.Action.FetchVault)
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
        VaultItemsList(
            windowSizeClass = windowSizeClass,
            paddingValues = paddingValues,
            lazyListState = lazyListState,
            lazyStaggeredGridState = lazyStaggeredGridState,
            passwordsList = state.value.passwordsList,
            onAction = viewModel::perform
        )
    }
}

@Composable
fun VaultItemsList(
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    lazyListState: LazyListState,
    lazyStaggeredGridState: LazyStaggeredGridState,
    passwordsList: List<PasswordUIModel>,
    onAction: (VaultViewModel.Action) -> Unit
) {
    if (windowSizeClass.isCompact()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = lazyListState
        ) {
            item {
                Spacer(modifier = Modifier.padding(top = paddingValues.calculateTopPadding()))
            }
            items(passwordsList) { passwordModel ->
                PasswordItem(
                    modifier = Modifier
                        .animateItem(),
                    item = passwordModel,
                    onClick = {
                        onAction(
                            VaultViewModel.Action.ToggleOpenItem(
                                passwordModel.id
                            )
                        )
                    },
                    isExpanded = passwordModel.isExpanded,
                    onDeleteClick = {},
                    onCopyClick = {},
                    onEditClick = {},
                    onCopySensitiveClick = {},
                    onFavoriteClick = {},
                    isFavorite = passwordModel.isFavorite
                )
            }
            item {
                Spacer(
                    modifier = Modifier.padding(
                        top = paddingValues.calculateBottomPadding().plus(
                            Size68
                        )
                    )
                )
            }
        }
    } else {
        LazyVerticalStaggeredGrid(
            modifier = Modifier.fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            columns = StaggeredGridCells.Fixed(2),
            state = lazyStaggeredGridState,
            horizontalArrangement = Arrangement.spacedBy(Size8),
            verticalItemSpacing = Size8,
        ) {
            items(passwordsList) { passwordModel ->
                PasswordItem(
                    modifier = Modifier
                        .animateItem(),
                    item = passwordModel,
                    onClick = {
                        onAction(VaultViewModel.Action.ToggleOpenItem(passwordModel.id))
                    },
                    isExpanded = passwordModel.isExpanded,
                    onDeleteClick = {},
                    onCopyClick = {},
                    onEditClick = {},
                    onCopySensitiveClick = {},
                    onFavoriteClick = {},
                    isFavorite = passwordModel.isFavorite
                )
            }
            item {
                Spacer(
                    modifier = Modifier.padding(
                        top = paddingValues.calculateBottomPadding().plus(
                            Size68
                        )
                    )
                )
            }
        }
    }
}