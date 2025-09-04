package com.thejohnsondev.presentation.vault

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.thejohnsondev.analytics.Analytics
import com.thejohnsondev.common.PASSWORD_IDLE_ITEM_HEIGHT
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.presentation.additem.AddVaultItemScreen
import com.thejohnsondev.ui.components.animation.ShimmerEffect
import com.thejohnsondev.ui.components.button.ToggleButton
import com.thejohnsondev.ui.components.dialog.ConfirmAlertDialog
import com.thejohnsondev.ui.components.filter.Chip
import com.thejohnsondev.ui.components.filter.FilterGroup
import com.thejohnsondev.ui.components.text.SearchBar
import com.thejohnsondev.ui.components.vault.passworditem.PasswordItem
import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel
import com.thejohnsondev.ui.designsystem.EquallyRounded
import com.thejohnsondev.ui.designsystem.Percent50
import com.thejohnsondev.ui.designsystem.Percent50i
import com.thejohnsondev.ui.designsystem.Size10
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size22
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size56
import com.thejohnsondev.ui.designsystem.Size68
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.Size80
import com.thejohnsondev.ui.designsystem.colorscheme.getAppLogo
import com.thejohnsondev.ui.designsystem.getGlobalFontFamily
import com.thejohnsondev.ui.displaymessage.getAsText
import com.thejohnsondev.ui.model.ScaffoldConfig
import com.thejohnsondev.ui.model.message.MessageContent
import com.thejohnsondev.ui.model.message.MessageType
import com.thejohnsondev.ui.scaffold.BottomNavItem
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.bounceClick
import com.thejohnsondev.ui.utils.isCompact
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import vaultmultiplatform.core.ui.generated.resources.add
import vaultmultiplatform.core.ui.generated.resources.cancel
import vaultmultiplatform.core.ui.generated.resources.delete
import vaultmultiplatform.core.ui.generated.resources.delete_password
import vaultmultiplatform.core.ui.generated.resources.delete_password_message
import vaultmultiplatform.core.ui.generated.resources.empty_vault
import vaultmultiplatform.core.ui.generated.resources.empty_vault_get_started
import vaultmultiplatform.core.ui.generated.resources.filters
import vaultmultiplatform.core.ui.generated.resources.nothing_found
import vaultmultiplatform.core.ui.generated.resources.sort_by
import vaultmultiplatform.core.ui.generated.resources.vault

private const val SHIMMER_PASSWORDS_COUNT = 10

@Composable
internal fun VaultScreen(
    windowSizeClass: WindowWidthSizeClass,
    vaultViewModel: VaultViewModel,
    paddingValues: PaddingValues,
    isFromLogin: Boolean,
    setScaffoldConfig: (ScaffoldConfig) -> Unit,
    updateIsEmptyVault: (Boolean) -> Unit,
    updateIsFabExpanded: (Boolean) -> Unit,
    showMessage: (MessageContent) -> Unit,
) {
    val state = vaultViewModel.state.collectAsState(VaultViewModel.State())
    val successSnackBarHostState = remember {
        SnackbarHostState()
    }
    val errorSnackBarHostState = remember {
        SnackbarHostState()
    }
    val lazyListState = rememberLazyListState()

    LaunchedEffect(lazyListState.firstVisibleItemIndex == 0) {
        updateIsFabExpanded(lazyListState.firstVisibleItemIndex == 0)
    }
    val appLogo = vectorResource(getAppLogo())

    val isCompact = windowSizeClass.isCompact()

    LaunchedEffect(Unit) {
        Analytics.trackScreen("Vault Screen")
    }

    LaunchedEffect(isCompact) {
        vaultViewModel.perform(VaultViewModel.Action.UpdateIsScreenCompact(isCompact))
    }

    LaunchedEffect(true) {
        vaultViewModel.getEventFlow().collect {
            when (it) {
                is OneTimeEvent.InfoMessage -> {
                    val message = MessageContent(
                        message = it.message.getAsText(),
                        type = MessageType.INFO,
                        imageVector = Icons.Filled.Info
                    )
                    showMessage(message)
                }
            }
        }
    }

    LaunchedEffect(true) {
        vaultViewModel.perform(VaultViewModel.Action.FetchVault(isFromLogin = isFromLogin))
        setScaffoldConfig(
            ScaffoldConfig(
                topAppBarTitle = getString(ResString.vault),
                topAppBarIcon = appLogo,
                isFabVisible = true,
                fabTitle = getString(ResString.add),
                fabIcon = Icons.Default.Add,
                onFabClick = {
                    vaultViewModel.perform(VaultViewModel.Action.OnAddClick)
                },
                isFabExpanded = true,
                successSnackBarHostState = successSnackBarHostState,
                errorSnackBarHostState = errorSnackBarHostState,
                bottomBarItemIndex = BottomNavItem.Vault.index
            )
        )
    }

    LaunchedEffect(state.value.isVaultEmpty) {
        updateIsEmptyVault(state.value.isVaultEmpty)
    }

    VaultScreenContent(
        state = state.value,
        windowSizeClass = windowSizeClass,
        paddingValues = paddingValues,
        lazyListState = lazyListState,
        onAction = vaultViewModel::perform
    )

    Dialogs(
        state = state.value,
        onAction = vaultViewModel::perform,
        windowSizeClass = windowSizeClass,
        paddingValues = paddingValues,
        showMessage = showMessage
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Dialogs(
    state: VaultViewModel.State,
    onAction: (VaultViewModel.Action) -> Unit,
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    showMessage: (MessageContent) -> Unit,
) {
    val addVaultItemSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { it != SheetValue.Hidden })

    if (state.editVaultItemContainer.first) {
        AddVaultItemScreen(
            windowSizeClass = windowSizeClass,
            paddingValues = paddingValues,
            sheetState = addVaultItemSheetState,
            vaultItem = state.editVaultItemContainer.second,
            onDismissRequest = {
                Analytics.trackScreen("Vault Screen")
                onAction(VaultViewModel.Action.OnAddClose)
            },
            showSuccessMessage = {
                val message = MessageContent(
                    message = it, type = MessageType.SUCCESS, imageVector = Icons.Filled.Done
                )
                showMessage(message)
            })
    }

    if (state.deletePasswordPair.first) {
        ConfirmAlertDialog(
            windowWidthSizeClass = windowSizeClass,
            title = stringResource(ResString.delete_password),
            message = stringResource(ResString.delete_password_message),
            confirmButtonText = stringResource(ResString.delete),
            cancelButtonText = stringResource(ResString.cancel),
            onConfirm = {
                state.deletePasswordPair.second?.let {
                    onAction(VaultViewModel.Action.ShowHideConfirmDelete(Pair(false, null)))
                    onAction(
                        VaultViewModel.Action.OnDeletePasswordClick(
                            passwordId = it
                        )
                    )
                }
            },
            onCancel = {
                onAction(VaultViewModel.Action.ShowHideConfirmDelete(Pair(false, null)))
            })
    }
}

@Composable
internal fun VaultScreenContent(
    state: VaultViewModel.State,
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    lazyListState: LazyListState,
    onAction: (VaultViewModel.Action) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
    ) {
        when (state.screenState) {
            is ScreenState.Loading,
            ScreenState.None,
                -> {
                VaultLoading(
                    windowSizeClass = windowSizeClass, paddingValues = paddingValues
                )
            }

            is ScreenState.ShowContent -> {
                VaultItemsList(
                    windowSizeClass = windowSizeClass,
                    paddingValues = paddingValues,
                    lazyListState = lazyListState,
                    state = state,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
private fun VaultLoading(
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
) {
    val topPadding = paddingValues.calculateTopPadding()
    Column(
        modifier = Modifier.fillMaxSize().padding(top = topPadding),
    ) {
        ShimmerSearchBar()
        repeat(SHIMMER_PASSWORDS_COUNT) {
            ShimmerPasswordItem(windowSizeClass)
        }
    }
}

@Composable
private fun ShimmerSearchBar() {
    ShimmerEffect(
        modifier = Modifier.fillMaxWidth().height(Size80)
            .padding(start = Size10, end = Size10, top = Size8, bottom = Size16)
            .clip(EquallyRounded.large)
    )
}

@Composable
private fun ShimmerPasswordItem(
    windowSizeClass: WindowWidthSizeClass,
) {
    Row(
        modifier = Modifier.fillMaxWidth().height(PASSWORD_IDLE_ITEM_HEIGHT.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        if (windowSizeClass.isCompact()) {
            ShimmerEffect(
                modifier = Modifier.fillMaxWidth().height(PASSWORD_IDLE_ITEM_HEIGHT.dp)
                    .padding(start = Size8, bottom = Size8, end = Size8)
                    .clip(EquallyRounded.medium),
            )
        } else {
            repeat(2) {
                ShimmerEffect(
                    modifier = Modifier.weight(Percent50).height(PASSWORD_IDLE_ITEM_HEIGHT.dp)
                        .padding(start = Size10, bottom = Size8, end = Size10)
                        .clip(EquallyRounded.medium),
                )
            }
        }
    }
}

@Composable
fun VaultItemsList(
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    lazyListState: LazyListState,
    state: VaultViewModel.State,
    onAction: (VaultViewModel.Action) -> Unit,
) {
    val topPadding = paddingValues.calculateTopPadding()
    val bottomPadding = paddingValues.calculateBottomPadding().plus(Size68)
    if (state.isVaultEmpty) {
        EmptyVaultPlaceholder()
    } else {
        if (windowSizeClass.isCompact()) {
            CompactScreenList(
                state = state,
                lazyListState = lazyListState,
                topPadding = topPadding,
                bottomPadding = bottomPadding,
                onAction = onAction
            )
        } else {
            LargeScreenList(
                state = state,
                lazyListState = lazyListState,
                topPadding = topPadding,
                bottomPadding = bottomPadding,
                onAction = onAction
            )
        }
    }
}

@Composable
private fun CompactScreenList(
    state: VaultViewModel.State,
    lazyListState: LazyListState,
    topPadding: Dp,
    bottomPadding: Dp,
    onAction: (VaultViewModel.Action) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = Size4), state = lazyListState
    ) {
        item {
            Spacer(modifier = Modifier.height(topPadding))
        }
        item {
            SearchBarRow(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = Size8, end = Size8, top = Size8, bottom = Size16),
                state = state,
                isDeepSearchEnabled = state.isDeepSearchEnabled,
                onAction = onAction
            )
        }
        item {
            Sorting(state, onAction)
        }
        item {
            Filters(state, onAction)
        }
        if (state.passwordsList.isNotEmpty()) {
            items(
                items = state.passwordsList.first(), key = {
                    it.id
                }) { passwordModel ->
                BindPasswordItem(
                    modifier = Modifier.animateItem(),
                    passwordModel = passwordModel,
                    onAction = onAction
                )
            }
        } else {
            item {
                NothingFoundPlaceholder()
            }
        }
        item {
            Spacer(modifier = Modifier.height(bottomPadding))
        }
    }
}

@Composable
private fun LargeScreenList(
    state: VaultViewModel.State,
    lazyListState: LazyListState,
    topPadding: Dp,
    bottomPadding: Dp,
    onAction: (VaultViewModel.Action) -> Unit,
) {
    val finalListHeight = state.listHeight.dp.plus(bottomPadding)
    LazyColumn(
        modifier = Modifier.fillMaxSize(), state = lazyListState
    ) {
        item {
            SearchBarRow(
                modifier = Modifier.fillMaxWidth().padding(
                        start = Size16, end = Size16, top = topPadding.plus(Size8), bottom = Size16
                    ),
                state = state,
                isDeepSearchEnabled = state.isDeepSearchEnabled,
                onAction = onAction
            )
        }
        item {
            Sorting(state, onAction)
        }
        item {
            Filters(state, onAction)
        }
        item {
            if (state.passwordsList.isNotEmpty()) {
                Row {
                    LazyColumn(
                        modifier = Modifier.weight(Percent50).height(finalListHeight)
                            .padding(bottom = bottomPadding, start = Size4),
                        userScrollEnabled = false
                    ) {
                        items(
                            items = state.passwordsList.first(), key = {
                                it.id
                            }) { passwordModel ->
                            BindPasswordItem(
                                modifier = Modifier.animateItem(placementSpec = spring(stiffness = Spring.StiffnessLow)),
                                passwordModel = passwordModel,
                                onAction = onAction
                            )
                        }
                    }

                    LazyColumn(
                        modifier = Modifier.weight(Percent50).height(finalListHeight)
                            .padding(bottom = bottomPadding, end = Size4), userScrollEnabled = false
                    ) {
                        items(
                            items = state.passwordsList.last(), key = {
                                it.id
                            }) { passwordModel ->
                            BindPasswordItem(
                                modifier = Modifier.animateItem(placementSpec = spring(stiffness = Spring.StiffnessLow)),
                                passwordModel = passwordModel,
                                onAction = onAction
                            )
                        }
                    }
                }
            } else {
                NothingFoundPlaceholder()
            }
        }
    }
}

@Composable
private fun BindPasswordItem(
    modifier: Modifier = Modifier,
    passwordModel: PasswordUIModel,
    onAction: (VaultViewModel.Action) -> Unit,
) {
    PasswordItem(
        modifier = modifier, item = passwordModel, onClick = {
        onAction(
            VaultViewModel.Action.ToggleOpenItem(
                passwordModel.id
            )
        )
    }, isExpanded = passwordModel.isExpanded, onDeleteClick = {
        onAction(VaultViewModel.Action.ShowHideConfirmDelete(Pair(true, passwordModel.id)))
    }, onCopy = { text ->
        onAction(VaultViewModel.Action.OnCopyClick(text))
    }, onEditClick = {
        onAction(VaultViewModel.Action.OnEditClick(passwordModel))
    }, onCopySensitive = { text ->
        onAction(VaultViewModel.Action.OnCopySensitiveClick(text))
    }, onFavoriteClick = {
        onAction(
            VaultViewModel.Action.OnMarkAsFavoriteClick(
                passwordModel.id, !passwordModel.isFavorite
            )
        )
    }, isFavorite = passwordModel.isFavorite
    )
}


@Composable
fun SearchBarRow(
    modifier: Modifier = Modifier,
    state: VaultViewModel.State,
    isDeepSearchEnabled: Boolean,
    onAction: (VaultViewModel.Action) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchBar(
            modifier = Modifier.padding(end = Size8).weight(1f, fill = true),
            onQueryEntered = { query ->
                onAction(
                    VaultViewModel.Action.Search(
                        query, isDeepSearchEnabled
                    )
                )
            },
            onQueryClear = {
                onAction(VaultViewModel.Action.StopSearching)
            })

        AnimatedVisibility(!state.isSearching) {
            ToggleButton(
                modifier = Modifier.padding(end = Size8).size(Size56).bounceClick()
                    .clip(RoundedCornerShape(percent = Percent50i)),
                isSelected = state.isSortingOpened,
                icon = Icons.Default.SwapVert,
                iconSize = Size22,
                onToggleClick = {
                    onAction(VaultViewModel.Action.ToggleSortingOpened)
                },
            )
        }

        AnimatedVisibility(!state.isSearching) {
            ToggleButton(
                modifier = Modifier.size(Size56).bounceClick()
                    .clip(RoundedCornerShape(percent = Percent50i)),
                isSelected = state.isFiltersOpened,
                icon = Icons.Default.FilterList,
                iconSize = Size22,
                showAccentDot = state.isAnyFiltersApplied,
                onToggleClick = {
                    onAction(VaultViewModel.Action.ToggleIsFiltersOpened)
                },
            )
        }
    }
}

@Composable
fun Filters(
    state: VaultViewModel.State,
    onAction: (VaultViewModel.Action) -> Unit,
) {
    AnimatedVisibility(visible = state.isFiltersOpened && !state.isSearching) {
        Column(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            /*FilterGroup(  // TODO commented before implementation of other filters
                modifier = Modifier
                    .wrapContentWidth(),
                filters = state.itemTypeFilters,
                onFilterClick = { filter, isSelected ->
                    onAction(VaultViewModel.Action.OnFilterTypeClick(filter, isSelected))
                }
            )*/
            FilterGroup(
                modifier = Modifier.wrapContentWidth().padding(bottom = Size16),
                filters = state.itemCategoryFilters,
                onFilterClick = { filter, isSelected ->
                    onAction(VaultViewModel.Action.OnFilterCategoryClick(filter, isSelected))
                })
        }
    }
}

@Composable
fun Sorting(
    state: VaultViewModel.State,
    onAction: (VaultViewModel.Action) -> Unit,
) {
    AnimatedVisibility(visible = state.isSortingOpened && !state.isSearching) {
        Column(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(start = Size16, bottom = Size8),
                text = stringResource(ResString.sort_by),
                style = MaterialTheme.typography.titleLarge,
                fontFamily = getGlobalFontFamily(),
                color = MaterialTheme.colorScheme.onSurface
            )
            FilterGroup(
                modifier = Modifier.wrapContentWidth().padding(bottom = Size4),
                filters = state.sortOrderFilters,
                onFilterClick = { filter, _ ->
                    onAction(VaultViewModel.Action.OnFilterSortByClick(filter))
                })
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Chip(
                    modifier = Modifier.padding(
                            start = Size16, end = Size4, bottom = Size16, top = Size4
                        ), filter = state.showFavoritesAtTopFilter
                ) { isSelected ->
                    onAction(VaultViewModel.Action.OnShowFavoritesAtTopClick(isSelected))
                }
            }
        }
    }
}

@Composable
fun EmptyVaultPlaceholder() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(ResString.empty_vault),
            style = MaterialTheme.typography.headlineSmall,
            fontFamily = getGlobalFontFamily(),
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(top = Size8),
            text = stringResource(ResString.empty_vault_get_started),
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = getGlobalFontFamily(),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun NothingFoundPlaceholder() {
    Box(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = Size16),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(ResString.nothing_found),
            style = MaterialTheme.typography.headlineSmall,
            fontFamily = getGlobalFontFamily(),
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
    }
}