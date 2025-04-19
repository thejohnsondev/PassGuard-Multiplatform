package com.thejohnsondev.presentation

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.thejohnsondev.common.EXPAND_ANIM_DURATION
import com.thejohnsondev.model.DisplayableMessageValue
import com.thejohnsondev.presentation.passwordgenerator.PasswordGeneratorWidget
import com.thejohnsondev.ui.components.SettingsItem
import com.thejohnsondev.ui.designsystem.Percent80
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.displaymessage.getAsText
import com.thejohnsondev.ui.model.ScaffoldConfig
import com.thejohnsondev.ui.model.message.MessageContent
import com.thejohnsondev.ui.model.message.MessageType
import com.thejohnsondev.ui.scaffold.BottomNavItem
import com.thejohnsondev.ui.utils.ResDrawable
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.isCompact
import com.thejohnsondev.ui.utils.padding
import kotlinx.coroutines.launch
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
    showMessage: (MessageContent) -> Unit,
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val toolsIcon = vectorResource(ResDrawable.ic_tools)
    val coroutineScope = rememberCoroutineScope()

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

    ToolsScreenContent(
        windowSizeClass = windowSizeClass,
        paddingValues = paddingValues,
        onCopyClick = {
            coroutineScope.launch {
                showMessage(
                    MessageContent(
                        message = DisplayableMessageValue.Copied.getAsText(),
                        type = MessageType.INFO,
                        imageVector = Icons.Filled.Info
                    )
                )
            }
        }
    )
}

@Composable
private fun ToolsScreenContent(
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    onCopyClick: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .applyIf(!windowSizeClass.isCompact()) {
                    fillMaxWidth(Percent80)
                }
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            PasswordGeneratorContainer(
                paddingValues = paddingValues,
                onCopyClick = onCopyClick
            )
        }
    }
}

@Composable
private fun PasswordGeneratorContainer(
    paddingValues: PaddingValues,
    onCopyClick: () -> Unit,
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
    val cardPaddingHorizontal by transition.animateDp({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isPasswordGeneratorExpanded) Size4 else Size16
    }
    Column {
        SettingsItem(
            modifier = Modifier.padding(
                horizontal = cardPaddingHorizontal,
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
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
                    .padding(horizontal = Size8, bottom = Size8)
                    .fillMaxWidth(),
                onCopyClick = {
                    onCopyClick()
                }
            )
        }
    }
}