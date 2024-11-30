package com.thejohnsondev.ui.model

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp

data class ScaffoldConfig(
    val topAppBarTitle: String? = null,
    val topAppBarIcon: ImageVector? = null,
    val onTopAppBarIconClick: (() -> Unit)? = null,
    val isFabVisible: Boolean = false,
    val fabTitle: String? = null,
    val fabIcon: ImageVector? = null,
    val onFabClick: () -> Unit = { },
    val isFabExpanded: Boolean = false,
    val snackBarPaddingHorizontal: Dp? = null,
    val snackBarPaddingVertical: Dp? = null,
    val snackBarHostState: SnackbarHostState? = null,
    val bottomBarItemIndex: Int = 0,
    val isEmptyVaultScreen: Boolean = false
)