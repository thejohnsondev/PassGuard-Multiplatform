package com.thejohnsondev.ui.scaffold

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.thejohnsondev.ui.components.VaultLogo
import com.thejohnsondev.ui.designsystem.DrawerWidth
import com.thejohnsondev.ui.designsystem.RailWidth
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size24
import com.thejohnsondev.ui.designsystem.Size32
import com.thejohnsondev.ui.designsystem.Size48
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.Size86
import com.thejohnsondev.ui.model.ScaffoldConfig
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.bounceClick
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScaffold(
    windowSize: WindowWidthSizeClass,
    scaffoldState: State<ScaffoldConfig>,
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    scrollBehavior: TopAppBarScrollBehavior,
    content: @Composable (PaddingValues) -> Unit,
) {
    val navigationItems = listOf(
        BottomNavItem.Vault, BottomNavItem.Tools, BottomNavItem.Settings
    )
    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {

        TopAppBar(modifier = Modifier.applyIf(windowSize == WindowWidthSizeClass.Expanded) {
                padding(start = DrawerWidth)
            }.applyIf(windowSize == WindowWidthSizeClass.Medium) {
                padding(start = RailWidth)
            }, title = {
            scaffoldState.value.topAppBarTitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }, navigationIcon = {
            scaffoldState.value.topAppBarIcon?.let {
                Icon(
                    modifier = Modifier.size(Size48).padding(start = Size16).clip(CircleShape)
                        .bounceClick().applyIf(scaffoldState.value.onTopAppBarIconClick != null) {
                            clickable {
                                scaffoldState.value.onTopAppBarIconClick?.invoke()
                            }
                        },
                    imageVector = it,
                    contentDescription = null // TODO add content description
                )
            }
        }, scrollBehavior = scrollBehavior
        )

    }, floatingActionButton = {
        if (scaffoldState.value.isFabVisible) {
            ExtendedFloatingActionButton(
                modifier = Modifier.bounceClick(),
                onClick = {
                    scaffoldState.value.onFabClick()
                },
                expanded = scaffoldState.value.isFabExpanded,
                icon = {
                    scaffoldState.value.fabIcon?.let {
                        Icon(
                            imageVector = it, contentDescription = scaffoldState.value.fabTitle
                        )
                    }
                },
                text = {
                    scaffoldState.value.fabTitle?.let {
                        Text(text = it)
                    }
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }, floatingActionButtonPosition = FabPosition.End, bottomBar = {
        if (windowSize == WindowWidthSizeClass.Compact) {
            AnimatedVisibility(
                visible = bottomBarState.value,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
            ) {
                NavigationBar {
                    navigationItems.forEachIndexed { index, screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    modifier = Modifier.size(Size24),
                                    painter = painterResource(screen.imgResId),
                                    contentDescription = stringResource(screen.titleRes)
                                )
                            },
                            label = { Text(stringResource(screen.titleRes)) },
                            selected = scaffoldState.value.bottomBarItemIndex == index,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(screen.route) {
                                        inclusive = true
                                    }
                                }
                            },
                        )
                    }
                }
            }
        }
    }, snackbarHost = {
        scaffoldState.value.snackBarHostState?.let { snackBarHostState ->
            SnackbarHost(snackBarHostState) { data ->
                Snackbar(
                    modifier = Modifier.padding(
                            vertical = scaffoldState.value.snackBarPaddingVertical ?: Size86,
                            horizontal = scaffoldState.value.snackBarPaddingHorizontal ?: Size16
                        )
                ) {
                    Text(text = data.visuals.message)
                }
            }
        }
    }) {
        when (windowSize) {
            WindowWidthSizeClass.Expanded -> {
                PermanentNavigationDrawer(drawerContent = {
                    PermanentDrawerSheet(
                        modifier = Modifier.width(DrawerWidth),
                        drawerContainerColor = MaterialTheme.colorScheme.surfaceDim,
                        drawerShape = RoundedCornerShape(topEnd = Size32, bottomEnd = Size32),
                    ) {
                        Column(
                            modifier = Modifier.padding(Size8).align(Alignment.CenterHorizontally)
                        ) {
                            VaultLogo(
                                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                            )
                        }
                        navigationItems.forEachIndexed { index, screen ->
                            NavigationDrawerItem(
                                label = { Text(text = stringResource(screen.titleRes)) },
                                selected = scaffoldState.value.bottomBarItemIndex == index,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(screen.route) {
                                            inclusive = true
                                        }
                                    }
                                },
                                icon = {
                                    Icon(
                                        modifier = Modifier.size(Size24),
                                        painter = painterResource(screen.imgResId),
                                        contentDescription = stringResource(screen.titleRes)
                                    )
                                },
                                modifier = Modifier.padding(
                                        horizontal = Size12,
                                        vertical = Size8
                                    )
                            )
                        }

                    }
                }) {
                    content(it)
                }
            }

            WindowWidthSizeClass.Medium -> {
                NavigationRail(
                    modifier = Modifier.width(RailWidth),
                    containerColor = MaterialTheme.colorScheme.surfaceDim
                ) {
                    navigationItems.forEachIndexed { index, screen ->
                        NavigationRailItem(icon = {
                            Icon(
                                modifier = Modifier.size(Size24),
                                painter = painterResource(screen.imgResId),
                                contentDescription = stringResource(screen.titleRes)
                            )
                        },
                            label = { Text(stringResource(screen.titleRes)) },
                            selected = scaffoldState.value.bottomBarItemIndex == index,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(screen.route) {
                                        inclusive = true
                                    }
                                }
                            })
                    }
                }
                Box(
                    modifier = Modifier.padding(start = RailWidth)
                ) {
                    content(it)
                }
            }

            else -> {
                content(it)
            }
        }
    }
}