package com.thejohnsondev.presentation.vaulttype

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.thejohnsondev.common.EXPAND_ANIM_DURATION
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.model.vault.VaultType
import com.thejohnsondev.ui.components.button.BackArrowButton
import com.thejohnsondev.ui.components.container.ExpandableContent
import com.thejohnsondev.ui.components.button.RoundedButton
import com.thejohnsondev.ui.designsystem.Percent70
import com.thejohnsondev.ui.designsystem.Size128
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size32
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size600
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.colorscheme.isLight
import com.thejohnsondev.ui.utils.ResDrawable
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.darken
import com.thejohnsondev.ui.utils.isCompact
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.core.ui.generated.resources.choose_vault_type
import vaultmultiplatform.core.ui.generated.resources.cloud_vault
import vaultmultiplatform.core.ui.generated.resources.cloud_vault_description
import vaultmultiplatform.core.ui.generated.resources.cloud_vault_in_development
import vaultmultiplatform.core.ui.generated.resources.create_local_vault
import vaultmultiplatform.core.ui.generated.resources.ic_vault_img_512_blue_gradient
import vaultmultiplatform.core.ui.generated.resources.local_vault
import vaultmultiplatform.core.ui.generated.resources.local_vault_description
import vaultmultiplatform.core.ui.generated.resources.log_in
import vaultmultiplatform.core.ui.generated.resources.sign_up

@Composable
fun SelectVaultTypeScreen(
    windowSize: WindowWidthSizeClass,
    viewModel: SelectedVaultTypeViewModel,
    goToSignUp: () -> Unit,
    goToLogin: () -> Unit,
    goToHome: () -> Unit,
    goBack: () -> Unit,
) {
    val state = viewModel.viewState.collectAsState(SelectedVaultTypeViewModel.State())

    LaunchedEffect(true) {
        viewModel.getEventFlow().collect {
            when (it) {
                is SelectedVaultTypeViewModel.NavigateToHome -> goToHome()
            }
        }
    }

    SelectVaultTypeContent(
        windowSize,
        state.value,
        goToSignUp,
        goToLogin,
        goBack,
        viewModel::perform
    )
}

@Composable
private fun SelectVaultTypeContent(
    windowSize: WindowWidthSizeClass,
    state: SelectedVaultTypeViewModel.State,
    goToSignUp: () -> Unit,
    goToLogin: () -> Unit,
    goBack: () -> Unit,
    onAction: (SelectedVaultTypeViewModel.Action) -> Unit,
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (MaterialTheme.colorScheme.isLight()) {
                        Color.White
                    } else {
                        Color.Black
                    }
                )
        ) {
            BackArrowButton(
                modifier = Modifier.padding(
                    start = Size16, top = paddingValues.calculateTopPadding().plus(
                        Size16
                    )
                ), onClick = goBack
            )
            Column(
                modifier = Modifier
                    .applyIf(windowSize.isCompact()) {
                        fillMaxSize()
                    }
                    .applyIf(!windowSize.isCompact()) {
                        width(Size600)
                            .fillMaxHeight()
                            .align(Alignment.Center)
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(Size128),
                    painter = painterResource(ResDrawable.ic_vault_img_512_blue_gradient),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(Size32))
                Text(
                    text = stringResource(ResString.choose_vault_type),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(Size32))
                VaultOption(
                    icon = Icons.Filled.Lock,
                    title = stringResource(ResString.local_vault),
                    description = stringResource(ResString.local_vault_description),
                    onClick = { isSelected ->
                        onAction(
                            SelectedVaultTypeViewModel.Action.SelectVaultType(
                                VaultType.LOCAL,
                                isSelected
                            )
                        )
                    },
                    isSelected = state.selectedVaultType == VaultType.LOCAL
                ) {
                    LocalVaultExpandedContent(state, onAction)
                }

                VaultOption(
                    icon = Icons.Filled.Cloud,
                    title = stringResource(ResString.cloud_vault),
                    titleComment = stringResource(ResString.cloud_vault_in_development),
                    description = stringResource(ResString.cloud_vault_description),
                    onClick = { isSelected ->
                        onAction(
                            SelectedVaultTypeViewModel.Action.SelectVaultType(
                                VaultType.CLOUD,
                                isSelected
                            )
                        )
                    },
                    isSelected = state.selectedVaultType == VaultType.CLOUD
                ) {
                    CloudVaultExpandedContent(goToLogin, goToSignUp)
                }
            }
        }
    }

}

@Composable
private fun CloudVaultExpandedContent(goToLogin: () -> Unit, goToSignUp: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RoundedButton(
            modifier = Modifier
                .padding(bottom = Size8, start = Size16, end = Size16),
            text = stringResource(ResString.log_in),
            onClick = {
                goToLogin()
            }
        )
        RoundedButton(
            modifier = Modifier
                .padding(
                    top = Size8,
                    bottom = Size16,
                    start = Size16,
                    end = Size16
                ),
            text = stringResource(ResString.sign_up),
            onClick = {
                goToSignUp()
            }
        )
    }
}

@Composable
private fun LocalVaultExpandedContent(
    state: SelectedVaultTypeViewModel.State,
    onAction: (SelectedVaultTypeViewModel.Action) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RoundedButton(
            modifier = Modifier
                .padding(bottom = Size16, start = Size16, end = Size16),
            text = stringResource(ResString.create_local_vault),
            loading = state.screenState is ScreenState.Loading,
            onClick = {
                onAction(SelectedVaultTypeViewModel.Action.CreateLocalVault)
            }
        )
    }
}

@Composable
fun VaultOption(
    icon: ImageVector,
    title: String,
    titleComment: String? = null,
    description: String,
    isSelected: Boolean,
    onClick: (Boolean) -> Unit,
    content: @Composable () -> Unit = {},
) {
    val itemTransitionState = remember {
        MutableTransitionState(isSelected).apply {
            targetState = !isSelected
        }
    }
    val itemTransition = rememberTransition(itemTransitionState, label = "")
    val cardBgColor by itemTransition.animateColor({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isSelected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceContainerLow
    }
    Column(
        modifier = Modifier
            .padding(horizontal = Size16, vertical = Size8)
            .clip(RoundedCornerShape(Size16))
            .background(cardBgColor)
            .clickable {
                onClick(!isSelected)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Size16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(Size32),
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(Size16))
            VaultTypeDescriptions(title = title, titleComment = titleComment, description = description)
        }
        ExpandableContent(isSelected) {
            content()
        }
    }
}

@Composable
private fun VaultTypeDescriptions(
    title: String,
    titleComment: String?,
    description: String
) {
    Column {
        Row {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            titleComment?.let {
                Text(
                    modifier = Modifier.padding(start = Size4),
                    text = titleComment,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        Text(
            modifier = Modifier.padding(top = Size8),
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.darken(darkenBy = Percent70)
        )
    }
}