package com.thejohnsondev.presentation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.thejohnsondev.common.EXPAND_ANIM_DURATION
import com.thejohnsondev.presentation.passwordgenerator.PasswordGeneratorWidget
import com.thejohnsondev.ui.components.SettingsItem
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.model.ScaffoldConfig
import com.thejohnsondev.ui.scaffold.BottomNavItem
import com.thejohnsondev.ui.utils.ResDrawable
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.padding
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import vaultmultiplatform.core.ui.generated.resources.ic_tools
import vaultmultiplatform.core.ui.generated.resources.password_generator
import vaultmultiplatform.core.ui.generated.resources.tools

@Composable
fun ToolsScreen(
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    setScaffoldConfig: (ScaffoldConfig) -> Unit,
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
    ToolsScreenContent(paddingValues = paddingValues)
}

@Composable
private fun ToolsScreenContent(
    paddingValues: PaddingValues,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceContainerLowest
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            PasswordGeneratorContainer(paddingValues)
        }
    }
}

@Composable
private fun PasswordGeneratorContainer(
    paddingValues: PaddingValues,
) {
    var isPasswordGeneratorExpanded by remember {
        mutableStateOf(false)
    }
    val transitionState = remember {
        MutableTransitionState(isPasswordGeneratorExpanded).apply {
            targetState = !isPasswordGeneratorExpanded
        }
    }
    val transition = rememberTransition(transitionState, label = "")
    val cardBgColor by transition.animateColor({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isPasswordGeneratorExpanded) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer
    }
    val cardPaddingHorizontal by transition.animateDp({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isPasswordGeneratorExpanded) Size4 else Size8
    }
//    RoundedContainer(
//        modifier = Modifier.fillMaxWidth()
//            .padding(horizontal = cardPaddingHorizontal, top = paddingValues.calculateTopPadding()),
//        color = cardBgColor,
//        onClick = {
//            isPasswordGeneratorExpanded = !isPasswordGeneratorExpanded
//        }
//    ) {
    Column {
//            Text(
//                modifier = Modifier.padding(vertical = Size24, horizontal = Size16),
//                text = stringResource(ResString.password_generator),
//                style = MaterialTheme.typography.titleLarge,
//                color = MaterialTheme.colorScheme.onSurface
//            )
        SettingsItem(
            modifier = Modifier.padding(
                horizontal = cardPaddingHorizontal,
                top = paddingValues.calculateTopPadding()
            ).fillMaxWidth(),
            title = stringResource(ResString.password_generator),
            description = null,
            icon = Icons.Default.Casino,
            isFirstItem = true,
            isLastItem = true,
            onExpanded = {
                isPasswordGeneratorExpanded = it
            }
        ) {
            PasswordGeneratorWidget(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
//    }
}