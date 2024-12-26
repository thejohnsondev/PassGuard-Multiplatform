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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.thejohnsondev.common.PASSWORD_IDLE_ITEM_HEIGHT
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.presentation.additem.AddVaultItemScreen
import com.thejohnsondev.presentation.component.PasswordItem
import com.thejohnsondev.ui.components.SearchBar
import com.thejohnsondev.ui.components.ShimmerEffect
import com.thejohnsondev.ui.components.ToggleButton
import com.thejohnsondev.ui.components.filter.FilterGroup
import com.thejohnsondev.ui.designsystem.EqualRounded
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
import com.thejohnsondev.ui.model.PasswordUIModel
import com.thejohnsondev.ui.model.ScaffoldConfig
import com.thejohnsondev.ui.model.message.MessageContent
import com.thejohnsondev.ui.model.message.MessageType
import com.thejohnsondev.ui.scaffold.BottomNavItem
import com.thejohnsondev.ui.utils.bounceClick
import com.thejohnsondev.ui.utils.isCompact
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import vaultmultiplatform.feature.vault.presentation.generated.resources.Res
import vaultmultiplatform.feature.vault.presentation.generated.resources.add
import vaultmultiplatform.feature.vault.presentation.generated.resources.empty_vault
import vaultmultiplatform.feature.vault.presentation.generated.resources.empty_vault_get_started
import vaultmultiplatform.feature.vault.presentation.generated.resources.vault

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VaultScreen(
    windowSizeClass: WindowWidthSizeClass,
    vaultViewModel: VaultViewModel,
    paddingValues: PaddingValues,
    setScaffoldConfig: (ScaffoldConfig) -> Unit,
    updateIsEmptyVault: (Boolean) -> Unit,
    onShowMessage: (MessageContent) -> Unit
) {
    val state = vaultViewModel.state.collectAsState(VaultViewModel.State())
    val successSnackBarHostState = remember {
        SnackbarHostState()
    }
    val errorSnackBarHostState = remember {
        SnackbarHostState()
    }
    val lazyListState = rememberLazyListState()

    val expandedFab by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0
        }
    }
    val appLogo = vectorResource(getAppLogo())
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        vaultViewModel.perform(VaultViewModel.Action.FetchVault(isCompact = windowSizeClass.isCompact()))
        setScaffoldConfig(
            ScaffoldConfig(
                topAppBarTitle = getString(Res.string.vault),
                topAppBarIcon = appLogo,
                isFabVisible = true,
                fabTitle = getString(Res.string.add),
                fabIcon = Icons.Default.Add,
                onFabClick = {
                    vaultViewModel.perform(VaultViewModel.Action.OnAddClick)
                },
                isFabExpanded = expandedFab, // TODO hiding fab on scroll doesn't work
                successSnackBarHostState = successSnackBarHostState,
                errorSnackBarHostState = errorSnackBarHostState,
                bottomBarItemIndex = BottomNavItem.Vault.index
            )
        )
    }
    LaunchedEffect(state.value.isVaultEmpty) {
        updateIsEmptyVault(state.value.isVaultEmpty)
    }

    if (state.value.editVaultItemContainer.first) {
        AddVaultItemScreen(
            windowSizeClass = windowSizeClass,
            paddingValues = paddingValues,
            sheetState = sheetState,
            vaultItem = state.value.editVaultItemContainer.second,
            onDismissRequest = {
                vaultViewModel.perform(VaultViewModel.Action.OnAddClose)
            },
            showSuccessMessage = {
                val message = MessageContent(
                    message = it,
                    type = MessageType.SUCCESS,
                    imageVector = Icons.Filled.Done
                )
                onShowMessage(message)
            }
        )
    }

    VaultScreenContent(
        state = state.value,
        windowSizeClass = windowSizeClass,
        paddingValues = paddingValues,
        lazyListState = lazyListState,
        onAction = vaultViewModel::perform
    )
}

@Composable
internal fun VaultScreenContent(
    state: VaultViewModel.State,
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    lazyListState: LazyListState,
    onAction: (VaultViewModel.Action) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
    ) {
        when (state.screenState) {
            is ScreenState.Loading,
            ScreenState.None -> {
                VaultLoading(
                    windowSizeClass = windowSizeClass,
                    paddingValues = paddingValues
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
    paddingValues: PaddingValues
) {
    val topPadding = paddingValues.calculateTopPadding()
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(top = topPadding),
    ) {
        ShimmerSearchBar()
        repeat(10) {
            ShimmerPasswordItem(windowSizeClass)
        }
    }
}

@Composable
private fun ShimmerSearchBar() {
    ShimmerEffect(
        modifier = Modifier
            .fillMaxWidth()
            .height(Size80)
            .padding(start = Size10, end = Size10, top = Size8, bottom = Size16)
            .clip(EqualRounded.large)
    )
}

@Composable
private fun ShimmerPasswordItem(
    windowSizeClass: WindowWidthSizeClass
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(PASSWORD_IDLE_ITEM_HEIGHT.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        if (windowSizeClass.isCompact()) {
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(PASSWORD_IDLE_ITEM_HEIGHT.dp)
                    .padding(start = Size8, bottom = Size8, end = Size8)
                    .clip(EqualRounded.medium),
            )
        } else {
            repeat(2) {
                ShimmerEffect(
                    modifier = Modifier
                        .weight(Percent50)
                        .height(PASSWORD_IDLE_ITEM_HEIGHT.dp)
                        .padding(start = Size10, bottom = Size8, end = Size10)
                        .clip(EqualRounded.medium),
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
    onAction: (VaultViewModel.Action) -> Unit
) {
    val topPadding = paddingValues.calculateTopPadding()
    val bottomPadding = paddingValues.calculateBottomPadding().plus(Size68)
    if (state.isVaultEmpty) {
        EmptyListPlaceholder()
    } else {
        if (windowSizeClass.isCompact()) {
            CompactScreenList(
                state = state,
                lazyListState = lazyListState,
                windowSizeClass = windowSizeClass,
                topPadding = topPadding,
                bottomPadding = bottomPadding,
                onAction = onAction
            )
        } else {
            LargeScreenList(
                state = state,
                lazyListState = lazyListState,
                windowSizeClass = windowSizeClass,
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
    windowSizeClass: WindowWidthSizeClass,
    topPadding: Dp,
    bottomPadding: Dp,
    onAction: (VaultViewModel.Action) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Size4),
        state = lazyListState
    ) {
        item {
            Spacer(modifier = Modifier.height(topPadding))
        }
        item {
            SearchBarRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Size8, end = Size8, top = Size8, bottom = Size16),
                state = state,
                windowSizeClass = windowSizeClass,
                isDeepSearchEnabled = state.isDeepSearchEnabled,
                onAction = onAction
            )
        }
        item {
            Filters(state, onAction)
        }
        if (state.passwordsList.isNotEmpty()) {
            items(state.passwordsList.first()) { passwordModel ->
                BindPasswordItem(
                    modifier = Modifier
                        .animateItem(),
                    passwordModel = passwordModel,
                    onAction = onAction
                )
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
    windowSizeClass: WindowWidthSizeClass,
    topPadding: Dp,
    bottomPadding: Dp,
    onAction: (VaultViewModel.Action) -> Unit,
) {
    val finalListHeight = state.listHeight.dp.plus(bottomPadding)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyListState
    ) {
        item {
            SearchBarRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Size16,
                        end = Size16,
                        top = topPadding.plus(Size8),
                        bottom = Size16
                    ),
                state = state,
                windowSizeClass = windowSizeClass,
                isDeepSearchEnabled = state.isDeepSearchEnabled,
                onAction = onAction
            )
        }
        item {
            Filters(state, onAction)
        }
        item {
            Row {
                LazyColumn(
                    modifier = Modifier
                        .weight(Percent50)
                        .height(finalListHeight)
                        .padding(bottom = bottomPadding, start = Size4),
                    userScrollEnabled = false
                ) {
                    if (state.passwordsList.isNotEmpty()) {
                        items(state.passwordsList.first()) { passwordModel ->
                            BindPasswordItem(
                                modifier = Modifier
                                    .animateItem(placementSpec = spring(stiffness = Spring.StiffnessLow)),
                                passwordModel = passwordModel,
                                onAction = onAction
                            )
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .weight(Percent50)
                        .height(finalListHeight)
                        .padding(bottom = bottomPadding, end = Size4),
                    userScrollEnabled = false
                ) {
                    if (state.passwordsList.isNotEmpty()) {
                        items(state.passwordsList.last()) { passwordModel ->
                            BindPasswordItem(
                                modifier = Modifier
                                    .animateItem(placementSpec = spring(stiffness = Spring.StiffnessLow)),
                                passwordModel = passwordModel,
                                onAction = onAction
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BindPasswordItem(
    modifier: Modifier = Modifier,
    passwordModel: PasswordUIModel,
    onAction: (VaultViewModel.Action) -> Unit
) {
    PasswordItem(
        modifier = modifier,
        item = passwordModel,
        onClick = {
            onAction(
                VaultViewModel.Action.ToggleOpenItem(
                    passwordModel.id
                )
            )
        },
        isExpanded = passwordModel.isExpanded,
        onDeleteClick = {
            onAction(
                VaultViewModel.Action.OnDeletePasswordClick(
                    passwordModel.id
                )
            )
        },
        onCopyClick = {},
        onEditClick = {
            onAction(VaultViewModel.Action.OnEditClick(passwordModel))
        },
        onCopySensitiveClick = {},
        onFavoriteClick = {},
        isFavorite = passwordModel.isFavorite
    )
}


@Composable
fun SearchBarRow(
    modifier: Modifier = Modifier,
    state: VaultViewModel.State,
    windowSizeClass: WindowWidthSizeClass,
    isDeepSearchEnabled: Boolean,
    onAction: (VaultViewModel.Action) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchBar(
            modifier = Modifier
                .padding(end = Size8)
                .weight(1f, fill = true),
            onQueryEntered = { query ->
                onAction(
                    VaultViewModel.Action.Search(
                        windowSizeClass.isCompact(),
                        query,
                        isDeepSearchEnabled
                    )
                )
            },
            onQueryClear = {
                onAction(VaultViewModel.Action.StopSearching(windowSizeClass.isCompact()))
            })

        AnimatedVisibility(!state.isSearching) {
            ToggleButton(
                modifier = Modifier
                    .size(Size56)
                    .bounceClick()
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
    onAction: (VaultViewModel.Action) -> Unit
) {
    AnimatedVisibility(visible = state.isFiltersOpened && !state.isSearching) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            FilterGroup(
                modifier = Modifier
                    .wrapContentWidth(),
                filters = state.itemTypeFilters,
                onFilterClick = { filter, isSelected ->
                    onAction(VaultViewModel.Action.OnFilterTypeClick(filter, isSelected))
                }
            )
            FilterGroup(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(bottom = Size16),
                filters = state.itemCategoryFilters,
                onFilterClick = { filter, isSelected ->
                    onAction(VaultViewModel.Action.OnFilterCategoryClick(filter, isSelected))
                }
            )
        }
    }
}

@Composable
fun EmptyListPlaceholder() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(Res.string.empty_vault),
            style = MaterialTheme.typography.headlineSmall,
            fontFamily = getGlobalFontFamily(),
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .padding(top = Size8),
            text = stringResource(Res.string.empty_vault_get_started),
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = getGlobalFontFamily(),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}