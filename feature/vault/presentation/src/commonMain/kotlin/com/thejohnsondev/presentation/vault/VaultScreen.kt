package com.thejohnsondev.presentation.vault

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.unit.dp
import com.thejohnsondev.domain.models.PasswordUIModel
import com.thejohnsondev.presentation.component.PasswordItem
import com.thejohnsondev.ui.designsystem.Percent50
import com.thejohnsondev.ui.designsystem.Size68
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

    val expandedFab by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0
        }
    }
    val appLogo = vectorResource(getAppLogo())

    LaunchedEffect(true) {
        viewModel.perform(VaultViewModel.Action.FetchVault(isCompact = windowSizeClass.isCompact()))
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
            passwordsList = state.value.passwordsList,
            listHeight = state.value.listHeight,
            onAction = viewModel::perform
        )
    }
}

@Composable
fun VaultItemsList(
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    lazyListState: LazyListState,
    passwordsList: List<List<PasswordUIModel>>,
    listHeight: Int,
    onAction: (VaultViewModel.Action) -> Unit
) {
    val topPadding = paddingValues.calculateTopPadding()
    val bottomPadding = paddingValues.calculateBottomPadding().plus(Size68)
    if (windowSizeClass.isCompact()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = lazyListState
        ) {
            item {
                Spacer(modifier = Modifier.height(topPadding))
            }
            items(passwordsList.first()) { passwordModel ->
                PasswordItem(
                    modifier = Modifier
                        .animateItem(),
                    item = passwordModel,
                    onClick = {
                        onAction(
                            VaultViewModel.Action.ToggleOpenItem(
                                windowSizeClass.isCompact(),
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
                Spacer(modifier = Modifier.height(bottomPadding))
            }
        }
    } else {

        val finalListHeight = listHeight.dp.plus(topPadding).plus(bottomPadding)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            state = lazyListState
        ) {
            item {
                Row {
                    LazyColumn(
                        modifier = Modifier
                            .weight(Percent50)
                            .height(finalListHeight)
                            .padding(top = topPadding, bottom = bottomPadding),
                        userScrollEnabled = false
                    ) {
                        items(passwordsList.first()) { passwordModel ->
                            PasswordItem(
                                modifier = Modifier
                                    .animateItem(),
                                item = passwordModel,
                                onClick = {
                                    onAction(
                                        VaultViewModel.Action.ToggleOpenItem(
                                            windowSizeClass.isCompact(),
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
                    }

                    LazyColumn(
                        modifier = Modifier
                            .weight(Percent50)
                            .height(finalListHeight)
                            .padding(top = topPadding, bottom = bottomPadding),
                        userScrollEnabled = false
                    ) {
                        items(passwordsList.last()) { passwordModel ->
                            PasswordItem(
                                modifier = Modifier
                                    .animateItem(),
                                item = passwordModel,
                                onClick = {
                                    onAction(
                                        VaultViewModel.Action.ToggleOpenItem(
                                            windowSizeClass.isCompact(),
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
                    }
                }
            }
        }
    }
}