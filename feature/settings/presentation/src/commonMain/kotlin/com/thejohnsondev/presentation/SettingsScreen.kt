package com.thejohnsondev.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.settings.DarkThemeConfig
import com.thejohnsondev.model.settings.GeneralSettings
import com.thejohnsondev.model.settings.PrivacySettings
import com.thejohnsondev.model.settings.ThemeBrand
import com.thejohnsondev.presentation.confirmdelete.DeleteAccountPasswordConfirmDialog
import com.thejohnsondev.ui.components.SelectableOptionItem
import com.thejohnsondev.ui.components.SelectableThemeOptionItem
import com.thejohnsondev.ui.components.SettingsItem
import com.thejohnsondev.ui.components.button.RoundedButton
import com.thejohnsondev.ui.components.button.ToggleOptionItem
import com.thejohnsondev.ui.components.dialog.ConfirmAlertDialog
import com.thejohnsondev.ui.designsystem.Percent80
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size2
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size72
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.DefaultSelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.DeepForestSelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.MonochromeSelectableItemsColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.RedAlgaeSelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.SunnySelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.TealSelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.VioletSelectableItemsColors
import com.thejohnsondev.ui.displaymessage.getAsText
import com.thejohnsondev.ui.model.ScaffoldConfig
import com.thejohnsondev.ui.model.getImageVector
import com.thejohnsondev.ui.model.message.MessageContent
import com.thejohnsondev.ui.model.message.MessageType
import com.thejohnsondev.ui.model.settings.SettingsSection
import com.thejohnsondev.ui.model.settings.SettingsSubSection
import com.thejohnsondev.ui.scaffold.BottomNavItem
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.isCompact
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.core.ui.generated.resources.block_screenshot
import vaultmultiplatform.core.ui.generated.resources.block_screenshot_description
import vaultmultiplatform.core.ui.generated.resources.cancel
import vaultmultiplatform.core.ui.generated.resources.create_account
import vaultmultiplatform.core.ui.generated.resources.create_account_description
import vaultmultiplatform.core.ui.generated.resources.dangerous_zone
import vaultmultiplatform.core.ui.generated.resources.dark_mode_preference
import vaultmultiplatform.core.ui.generated.resources.dark_mode_preference_dark
import vaultmultiplatform.core.ui.generated.resources.dark_mode_preference_light
import vaultmultiplatform.core.ui.generated.resources.dark_mode_preference_system
import vaultmultiplatform.core.ui.generated.resources.deep_search_description
import vaultmultiplatform.core.ui.generated.resources.deep_search_title
import vaultmultiplatform.core.ui.generated.resources.delete_account
import vaultmultiplatform.core.ui.generated.resources.delete_account_confirm_message
import vaultmultiplatform.core.ui.generated.resources.delete_vault
import vaultmultiplatform.core.ui.generated.resources.delete_vault_confirm_message
import vaultmultiplatform.core.ui.generated.resources.logout
import vaultmultiplatform.core.ui.generated.resources.logout_confirm_message
import vaultmultiplatform.core.ui.generated.resources.manage_account
import vaultmultiplatform.core.ui.generated.resources.no
import vaultmultiplatform.core.ui.generated.resources.settings
import vaultmultiplatform.core.ui.generated.resources.theme
import vaultmultiplatform.core.ui.generated.resources.theme_deep_forest
import vaultmultiplatform.core.ui.generated.resources.theme_default
import vaultmultiplatform.core.ui.generated.resources.theme_monochrome
import vaultmultiplatform.core.ui.generated.resources.theme_red_algae
import vaultmultiplatform.core.ui.generated.resources.theme_sunny
import vaultmultiplatform.core.ui.generated.resources.theme_teal
import vaultmultiplatform.core.ui.generated.resources.theme_violet
import vaultmultiplatform.core.ui.generated.resources.unlock_with_biometrics
import vaultmultiplatform.core.ui.generated.resources.unlock_with_biometrics_description
import vaultmultiplatform.core.ui.generated.resources.use_dynamic_color
import vaultmultiplatform.core.ui.generated.resources.yes

@Composable
fun SettingsScreen(
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    viewModel: SettingsViewModel,
    setScaffoldConfig: (ScaffoldConfig) -> Unit,
    onLogoutClick: () -> Unit,
    onGoToSignUp: () -> Unit,
    onShowError: (MessageContent) -> Unit,
) {
    val state = viewModel.state.collectAsState(SettingsViewModel.State())
    LaunchedEffect(true) {
        setScaffoldConfig(
            ScaffoldConfig(
                topAppBarTitle = getString(ResString.settings),
                topAppBarIcon = Icons.Default.Settings,
                bottomBarItemIndex = BottomNavItem.Settings.index
            )
        )
        viewModel.perform(SettingsViewModel.Action.FetchSettings)
        viewModel.getEventFlow().collect {
            when (it) {
                is OneTimeEvent.SuccessNavigation -> onLogoutClick()
                is OneTimeEvent.ErrorMessage -> onShowError(
                    MessageContent(
                        message = it.message.getAsText(),
                        type = MessageType.ERROR,
                        imageVector = Icons.Default.Error
                    )
                )
            }
        }
    }

    SettingsContent(
        state = state.value,
        windowSizeClass = windowSizeClass,
        paddingValues = paddingValues,
        onAction = { action ->
            viewModel.perform(action)
        },
        goToSignUp = onGoToSignUp
    )

}

@Composable
fun SettingsContent(
    state: SettingsViewModel.State,
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    onAction: (SettingsViewModel.Action) -> Unit,
    goToSignUp: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest),
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxHeight()
                .applyIf(!windowSizeClass.isCompact()) {
                    fillMaxWidth(Percent80)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            SettingsList(
                state = state,
                paddingValues = paddingValues,
                onAction = onAction, goToSignUp = goToSignUp
            )
            Dialogs(windowSizeClass = windowSizeClass, state = state, onAction = onAction)
        }
    }
}

@Composable
fun SettingsList(
    state: SettingsViewModel.State,
    onAction: (SettingsViewModel.Action) -> Unit,
    goToSignUp: () -> Unit,
    paddingValues: PaddingValues,
) {
    Column(
        modifier = Modifier
            .padding(
                top = paddingValues.calculateTopPadding().plus(Size16),
                bottom = paddingValues.calculateBottomPadding().plus(Size4)
            )
    ) {
        state.settingsSection.forEach { section ->
            val subSectionsNumber = section.subsections.size
            SettingsSectionTitle(
                section = section
            )
            section.subsections.forEachIndexed { index, subsection ->
                SettingsSubSections(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(
                            start = Size16,
                            end = Size16,

                            bottom = Size4
                        ),
                    state = state,
                    subSection = subsection,
                    subSectionsNumber = subSectionsNumber,
                    subSectionIndex = index,
                    onAction = onAction,
                    goToSignUp = goToSignUp
                )
            }
        }
    }
}

@Composable
fun SettingsSectionTitle(
    section: SettingsSection,
) {
    section.sectionTitleRes?.let { sectionTitleRes ->
        Column {
            Text(
                modifier = Modifier
                    .padding(Size16)
                    .align(Alignment.Start),
                text = stringResource(resource = sectionTitleRes),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun SettingsSubSections(
    modifier: Modifier = Modifier,
    state: SettingsViewModel.State,
    subSection: SettingsSubSection,
    subSectionIndex: Int,
    subSectionsNumber: Int,
    onAction: (SettingsViewModel.Action) -> Unit,
    goToSignUp: () -> Unit,
) {
    val subsectionDescription =
        if (subSection.sectionTitleRes == ResString.manage_account) {
            state.userEmail.orEmpty()
        } else {
            subSection.sectionDescriptionRes?.let { stringResource(resource = it) }
        }
    SettingsItem(
        modifier = modifier,
        title = stringResource(resource = subSection.sectionTitleRes),
        description = subsectionDescription,
        icon = subSection.sectionIcon.getImageVector(),
        isFirstItem = subSectionIndex == 0,
        isLastItem = subSectionIndex == subSectionsNumber - 1
    ) {
        when (subSection) {
            SettingsSubSection.ManageAccountSub -> {
                ManageAccountSubSection(
                    onAction = onAction,
                )
            }

            SettingsSubSection.ManageLocalVaultSub -> {
                ManageLocalVaultSubSection(
                    onAction = onAction,
                    goToSignUp = goToSignUp
                )
            }

            SettingsSubSection.GeneralSettingsSub -> {
                GeneralSettingsSubSection(state = state, onAction = onAction)
            }

            SettingsSubSection.StyleSettingsSub -> {
                StyleSettingsSubSection(state = state, onAction = onAction)
            }

            SettingsSubSection.PrivacySettingsSub -> {
                PrivacySettingsSubSection(state = state, onAction = onAction)
            }
        }
    }
}

@Composable
fun ManageAccountSubSection(
    onAction: (SettingsViewModel.Action) -> Unit,
) {
    RoundedButton(
        modifier = Modifier
            .height(Size72)
            .padding(start = Size16, end = Size16, bottom = Size16, top = Size2),
        text = stringResource(resource = ResString.logout),
        onClick = {
            onAction(SettingsViewModel.Action.OpenConfirmLogoutDialog)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
    Column(
        modifier = Modifier
            .padding(start = Size16, end = Size16, top = Size8, bottom = Size16)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(Size16))
            .background(MaterialTheme.colorScheme.errorContainer)
    ) {
        Text(
            text = stringResource(ResString.dangerous_zone),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onErrorContainer,
            modifier = Modifier.padding(start = Size16, top = Size16)
        )
        RoundedButton(
            modifier = Modifier
                .padding(Size16),
            text = stringResource(ResString.delete_account),
            onClick = {
                onAction(SettingsViewModel.Action.OpenConfirmDeleteAccountDialog)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onErrorContainer,
                contentColor = MaterialTheme.colorScheme.errorContainer
            )
        )
    }
}

@Composable
fun ManageLocalVaultSubSection(
    onAction: (SettingsViewModel.Action) -> Unit,
    goToSignUp: () -> Unit,
) {
    RoundedButton(
        modifier = Modifier
            .height(Size72)
            .padding(start = Size16, end = Size16, bottom = Size8, top = Size2),
        text = stringResource(resource = ResString.create_account),
        onClick = {
            goToSignUp()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
    Row(
        modifier = Modifier.padding(start = Size16, end = Size16, bottom = Size8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            tint = MaterialTheme.colorScheme.primaryContainer,
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .padding(start = Size4),
            text = stringResource(ResString.create_account_description),
            color = MaterialTheme.colorScheme.primaryContainer,
            style = MaterialTheme.typography.bodySmall
        )
    }
    Column(
        modifier = Modifier
            .padding(start = Size16, end = Size16, top = Size8, bottom = Size16)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(Size16))
            .background(MaterialTheme.colorScheme.errorContainer)
    ) {
        Text(
            text = stringResource(ResString.dangerous_zone),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onErrorContainer,
            modifier = Modifier.padding(start = Size16, top = Size16)
        )
        RoundedButton(
            modifier = Modifier
                .padding(Size16),
            text = stringResource(ResString.delete_vault),
            onClick = {
                onAction(SettingsViewModel.Action.OpenConfirmDeleteVaultDialog)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onErrorContainer,
                contentColor = MaterialTheme.colorScheme.errorContainer
            )
        )
    }
}

@Composable
fun GeneralSettingsSubSection(
    state: SettingsViewModel.State,
    onAction: (SettingsViewModel.Action) -> Unit,
) {
    Column(
        modifier = Modifier.padding(start = Size16, end = Size16, bottom = Size16)
    ) {
        ToggleOptionItem(
            optionTitle = stringResource(ResString.deep_search_title),
            optionDescription = stringResource(ResString.deep_search_description),
            isSelected = state.settingsConfig?.generalSettings?.isDeepSearchEnabled
                ?: false,
            isFirstItem = true,
            isLastItem = true
        ) {
            onAction(
                SettingsViewModel.Action.UpdateGeneralSettings(
                    state.settingsConfig?.generalSettings?.copy(
                        isDeepSearchEnabled = it
                    ) ?: GeneralSettings(isDeepSearchEnabled = it)
                )
            )
        }
    }
}

@Composable
fun StyleSettingsSubSection(
    state: SettingsViewModel.State,
    onAction: (SettingsViewModel.Action) -> Unit,
) {
    Column {
        Column(
            modifier = Modifier.padding(start = Size16, end = Size16, bottom = Size8)
        ) {
            Text(
                modifier = Modifier.padding(
                    bottom = Size8
                ),
                text = stringResource(ResString.dark_mode_preference),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondary
            )
            SelectableOptionItem(
                modifier = Modifier
                    .padding(top = Size4),
                optionTitle = stringResource(ResString.dark_mode_preference_system),
                isFirstItem = true,
                isSelected = state.settingsConfig?.darkThemeConfig == DarkThemeConfig.SYSTEM
            ) {
                onAction(SettingsViewModel.Action.UpdateDarkThemeConfig(DarkThemeConfig.SYSTEM))
            }
            SelectableOptionItem(
                modifier = Modifier
                    .padding(top = Size4),
                optionTitle = stringResource(ResString.dark_mode_preference_dark),
                isSelected = state.settingsConfig?.darkThemeConfig == DarkThemeConfig.DARK
            ) {
                onAction(SettingsViewModel.Action.UpdateDarkThemeConfig(DarkThemeConfig.DARK))
            }
            SelectableOptionItem(
                modifier = Modifier
                    .padding(top = Size4),
                optionTitle = stringResource(ResString.dark_mode_preference_light),
                isLastItem = true,
                isSelected = state.settingsConfig?.darkThemeConfig == DarkThemeConfig.LIGHT
            ) {
                onAction(SettingsViewModel.Action.UpdateDarkThemeConfig(DarkThemeConfig.LIGHT))
            }
            if (state.settingsConfig?.customTheme == ThemeBrand.DEFAULT && state.supportsDynamicTheming) {
                Text(
                    modifier = Modifier.padding(
                        bottom = Size8,
                        top = Size8
                    ),
                    text = stringResource(ResString.use_dynamic_color),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                SelectableOptionItem(
                    modifier = Modifier
                        .padding(top = Size4),
                    optionTitle = stringResource(ResString.yes),
                    isFirstItem = true,
                    isSelected = state.settingsConfig.useDynamicColor
                ) {
                    onAction(SettingsViewModel.Action.UpdateUseDynamicColor(true))
                }
                SelectableOptionItem(
                    modifier = Modifier
                        .padding(top = Size4),
                    optionTitle = stringResource(ResString.no),
                    isLastItem = true,
                    isSelected = !state.settingsConfig.useDynamicColor
                ) {
                    onAction(SettingsViewModel.Action.UpdateUseDynamicColor(false))
                }
            }
            Text(
                modifier = Modifier.padding(vertical = Size8),
                text = stringResource(ResString.theme),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState())
                .padding(bottom = Size16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ThemeBrand.entries.forEachIndexed { index, theme ->
                SelectableThemeOptionItem(
                    modifier = Modifier
                        .padding(
                            start = if (index == 0) Size16 else Size4,
                            end = if (index == ThemeBrand.entries.size - 1) Size16 else Size4,
                        ),
                    optionTitle = stringResource(
                        when (theme) {
                            ThemeBrand.DEFAULT -> ResString.theme_default
                            ThemeBrand.TEAL -> ResString.theme_teal
                            ThemeBrand.DEEP_FOREST -> ResString.theme_deep_forest
                            ThemeBrand.RED_ALGAE -> ResString.theme_red_algae
                            ThemeBrand.SUNNY -> ResString.theme_sunny
                            ThemeBrand.VIOLET -> ResString.theme_violet
                            ThemeBrand.MONOCHROME -> ResString.theme_monochrome
                            else -> ResString.theme_default
                        }
                    ),
                    isSelected = state.settingsConfig?.customTheme == theme,
                    colors = when (theme) {
                        ThemeBrand.DEFAULT -> DefaultSelectableItemColors
                        ThemeBrand.TEAL -> TealSelectableItemColors
                        ThemeBrand.DEEP_FOREST -> DeepForestSelectableItemColors
                        ThemeBrand.RED_ALGAE -> RedAlgaeSelectableItemColors
                        ThemeBrand.SUNNY -> SunnySelectableItemColors
                        ThemeBrand.VIOLET -> VioletSelectableItemsColors
                        ThemeBrand.MONOCHROME -> MonochromeSelectableItemsColors
                        else -> DefaultSelectableItemColors
                    }
                ) {
                    onAction(SettingsViewModel.Action.UpdateUseDynamicColor(false))
                    onAction(SettingsViewModel.Action.UpdateUseCustomTheme(theme))
                }
            }
        }
    }
}

@Composable
fun PrivacySettingsSubSection(
    state: SettingsViewModel.State,
    onAction: (SettingsViewModel.Action) -> Unit,
) {
    Column(
        modifier = Modifier.padding(start = Size16, end = Size16, bottom = Size16)
    ) {
        if (state.isBiometricsAvailable) {
            ToggleOptionItem(
                optionTitle = stringResource(ResString.unlock_with_biometrics),
                optionDescription = stringResource(ResString.unlock_with_biometrics_description),
                isSelected = state.settingsConfig?.privacySettings?.isUnlockWithBiometricEnabled
                    ?: false,
                isFirstItem = true,
                isLastItem = true
            ) { isUnlockWithBiometricsEnabled ->
                onAction(
                    SettingsViewModel.Action.UpdatePrivacySettings(
                        state.settingsConfig?.privacySettings?.copy(
                            isUnlockWithBiometricEnabled = isUnlockWithBiometricsEnabled
                        )
                            ?: PrivacySettings(
                                isUnlockWithBiometricEnabled = isUnlockWithBiometricsEnabled
                            )
                    )
                )
            }
        }
        if (state.isBlockingScreenshotsAvailable) {
            ToggleOptionItem(
                modifier = Modifier.applyIf(state.isBiometricsAvailable) {
                    padding(top = Size16)
                },
                optionTitle = stringResource(ResString.block_screenshot),
                optionDescription = stringResource(ResString.block_screenshot_description),
                isSelected = state.settingsConfig?.privacySettings?.isBlockScreenshotsEnabled
                    ?: false,
                isFirstItem = true,
                isLastItem = true
            ) {
                onAction(
                    SettingsViewModel.Action.UpdatePrivacySettings(
                        state.settingsConfig?.privacySettings?.copy(
                            isBlockScreenshotsEnabled = it
                        ) ?: PrivacySettings(isBlockScreenshotsEnabled = it)
                    )
                )
            }
        }
    }
}

@Composable
fun Dialogs(
    windowSizeClass: WindowWidthSizeClass,
    state: SettingsViewModel.State,
    onAction: (SettingsViewModel.Action) -> Unit,
) {
    if (state.isConfirmDeleteAccountDialogOpened) {
        ConfirmAlertDialog(
            windowWidthSizeClass = windowSizeClass,
            title = stringResource(ResString.delete_account),
            message = stringResource(ResString.delete_account_confirm_message),
            confirmButtonText = stringResource(ResString.delete_account),
            cancelButtonText = stringResource(ResString.cancel),
            onConfirm = {
                onAction(SettingsViewModel.Action.CloseConfirmDeleteAccountDialog)
                onAction(SettingsViewModel.Action.OpenDeleteAccountPasswordConfirm)
            },
            onCancel = {
                onAction(SettingsViewModel.Action.CloseConfirmDeleteAccountDialog)
            }
        )
    }
    if (state.isConfirmLogoutDialogOpened) {
        ConfirmAlertDialog(
            windowWidthSizeClass = windowSizeClass,
            title = stringResource(ResString.logout),
            message = stringResource(ResString.logout_confirm_message),
            confirmButtonText = stringResource(ResString.logout),
            cancelButtonText = stringResource(ResString.cancel),
            onConfirm = {
                onAction(SettingsViewModel.Action.CloseConfirmLogoutDialog)
                onAction(SettingsViewModel.Action.Logout)
            },
            onCancel = {
                onAction(SettingsViewModel.Action.CloseConfirmLogoutDialog)
            }
        )
    }
    if (state.isDeleteAccountPasswordConfirmDialogOpened) {
        DeleteAccountPasswordConfirmDialog(
            state = state,
            onAction = onAction,
            windowWidthSizeClass = windowSizeClass
        )
    }
    if (state.isConfirmDeleteVaultDialogOpened) {
        ConfirmAlertDialog(
            windowWidthSizeClass = windowSizeClass,
            title = stringResource(ResString.delete_vault),
            message = stringResource(ResString.delete_vault_confirm_message),
            confirmButtonText = stringResource(ResString.delete_vault),
            cancelButtonText = stringResource(ResString.cancel),
            onConfirm = {
                onAction(SettingsViewModel.Action.CloseConfirmDeleteVaultDialog)
                onAction(SettingsViewModel.Action.DeleteLocalVaultConfirm)
            },
            onCancel = {
                onAction(SettingsViewModel.Action.CloseConfirmDeleteVaultDialog)
            }
        )
    }
}