package com.thejohnsondev.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
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
import com.thejohnsondev.ui.components.ConfirmAlertDialog
import com.thejohnsondev.ui.components.RoundedButton
import com.thejohnsondev.ui.components.SelectableOptionItem
import com.thejohnsondev.ui.components.SettingsItem
import com.thejohnsondev.ui.components.ToggleOptionItem
import com.thejohnsondev.ui.designsystem.Percent80
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size2
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size72
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.displaymessage.getAsText
import com.thejohnsondev.ui.model.ScaffoldConfig
import com.thejohnsondev.ui.model.button.ButtonShape
import com.thejohnsondev.ui.model.message.MessageContent
import com.thejohnsondev.ui.model.message.MessageType
import com.thejohnsondev.ui.model.settings.SettingsSection
import com.thejohnsondev.ui.model.settings.SettingsSubSection
import com.thejohnsondev.ui.scaffold.BottomNavItem
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.isCompact
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.feature.settings.presentation.generated.resources.Res
import vaultmultiplatform.feature.settings.presentation.generated.resources.block_screenshot
import vaultmultiplatform.feature.settings.presentation.generated.resources.block_screenshot_description
import vaultmultiplatform.feature.settings.presentation.generated.resources.cancel
import vaultmultiplatform.feature.settings.presentation.generated.resources.dangerous_zone
import vaultmultiplatform.feature.settings.presentation.generated.resources.dark_mode_preference
import vaultmultiplatform.feature.settings.presentation.generated.resources.dark_mode_preference_dark
import vaultmultiplatform.feature.settings.presentation.generated.resources.dark_mode_preference_light
import vaultmultiplatform.feature.settings.presentation.generated.resources.dark_mode_preference_system
import vaultmultiplatform.feature.settings.presentation.generated.resources.deep_search_description
import vaultmultiplatform.feature.settings.presentation.generated.resources.deep_search_title
import vaultmultiplatform.feature.settings.presentation.generated.resources.delete_account
import vaultmultiplatform.feature.settings.presentation.generated.resources.delete_account_confirm_message
import vaultmultiplatform.feature.settings.presentation.generated.resources.logout
import vaultmultiplatform.feature.settings.presentation.generated.resources.logout_confirm_message
import vaultmultiplatform.feature.settings.presentation.generated.resources.manage_account
import vaultmultiplatform.feature.settings.presentation.generated.resources.no
import vaultmultiplatform.feature.settings.presentation.generated.resources.settings
import vaultmultiplatform.feature.settings.presentation.generated.resources.theme
import vaultmultiplatform.feature.settings.presentation.generated.resources.theme_android
import vaultmultiplatform.feature.settings.presentation.generated.resources.theme_default
import vaultmultiplatform.feature.settings.presentation.generated.resources.unlock_with_biometrics
import vaultmultiplatform.feature.settings.presentation.generated.resources.unlock_with_biometrics_description
import vaultmultiplatform.feature.settings.presentation.generated.resources.use_dynamic_color
import vaultmultiplatform.feature.settings.presentation.generated.resources.yes

@Composable
fun SettingsScreen(
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    viewModel: SettingsViewModel,
    setScaffoldConfig: (ScaffoldConfig) -> Unit,
    onLogoutClick: () -> Unit,
    onShowError: (MessageContent) -> Unit
) {
    val state = viewModel.state.collectAsState(SettingsViewModel.State())
    LaunchedEffect(true) {
        setScaffoldConfig(
            ScaffoldConfig(
                topAppBarTitle = getString(Res.string.settings),
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
                        type = MessageType.ERROR
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
        }
    )

}

@Composable
fun SettingsContent(
    state: SettingsViewModel.State,
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    onAction: (SettingsViewModel.Action) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(
                bottom = paddingValues.calculateBottomPadding(),
                top = paddingValues.calculateTopPadding()
            ).fillMaxSize()
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
            SettingsList(state = state, onAction = onAction)
            Dialogs(windowSizeClass = windowSizeClass, state = state, onAction = onAction)
        }
    }
}

@Composable
fun SettingsList(
    state: SettingsViewModel.State,
    onAction: (SettingsViewModel.Action) -> Unit,
) {
    Column {
        state.settingsSection.forEach { section ->
            val subSectionsNumber = section.subsections.size
            SettingsSectionTitle(
                section = section
            )
            section.subsections.forEachIndexed { index, subsection ->
                SettingsSubSections(
                    state = state,
                    subSection = subsection,
                    subSectionsNumber = subSectionsNumber,
                    subSectionIndex = index,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
fun SettingsSectionTitle(
    section: SettingsSection
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
    state: SettingsViewModel.State,
    subSection: SettingsSubSection,
    subSectionIndex: Int,
    subSectionsNumber: Int,
    onAction: (SettingsViewModel.Action) -> Unit,
) {
    val subsectionDescription =
        if (subSection.sectionTitleRes == Res.string.manage_account) {
            state.userEmail.orEmpty()
        } else {
            subSection.sectionDescriptionRes?.let { stringResource(resource = it) } ?: ""
        }
    SettingsItem(
        title = stringResource(resource = subSection.sectionTitleRes),
        description = subsectionDescription,
        icon = subSection.sectionIcon,
        isFirstItem = subSectionIndex == 0,
        isLastItem = subSectionIndex == subSectionsNumber - 1
    ) {
        when (subSection) {
            SettingsSubSection.ManageAccountSub -> {
                ManageAccountSubSection(
                    onAction = onAction,
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
        text = stringResource(resource = Res.string.logout),
        onClick = {
            onAction(SettingsViewModel.Action.OpenConfirmLogoutDialog)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        buttonShape = ButtonShape.ROUNDED
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
            text = stringResource(Res.string.dangerous_zone),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onErrorContainer,
            modifier = Modifier.padding(start = Size16, top = Size16)
        )
        RoundedButton(
            modifier = Modifier
                .padding(Size16),
            text = stringResource(Res.string.delete_account),
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
fun GeneralSettingsSubSection(
    state: SettingsViewModel.State,
    onAction: (SettingsViewModel.Action) -> Unit
) {
    Column(
        modifier = Modifier.padding(start = Size16, end = Size16, bottom = Size16)
    ) {
        ToggleOptionItem(
            optionTitle = stringResource(Res.string.deep_search_title),
            optionDescription = stringResource(Res.string.deep_search_description),
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
    onAction: (SettingsViewModel.Action) -> Unit
) {
    Column(
        modifier = Modifier.padding(start = Size16, end = Size16, bottom = Size16)
    ) {
        Text(
            modifier = Modifier.padding(bottom = Size8),
            text = stringResource(Res.string.theme),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSecondary
        )
        SelectableOptionItem(
            modifier = Modifier
                .padding(top = Size4),
            optionTitle = stringResource(Res.string.theme_default),
            isFirstItem = true,
            isSelected = state.settingsConfig?.customTheme == ThemeBrand.DEFAULT
        ) {
            onAction(
                SettingsViewModel.Action.UpdateUseCustomTheme(
                    ThemeBrand.DEFAULT
                )
            )
        }
        SelectableOptionItem(
            modifier = Modifier
                .padding(top = Size4),
            optionTitle = stringResource(Res.string.theme_android),
            isLastItem = true,
            isSelected = state.settingsConfig?.customTheme == ThemeBrand.ANDROID
        ) {
            onAction(
                SettingsViewModel.Action.UpdateUseDynamicColor(
                    false
                )
            )
            onAction(
                SettingsViewModel.Action.UpdateUseCustomTheme(
                    ThemeBrand.ANDROID
                )
            )
        }
        if (state.settingsConfig?.customTheme == ThemeBrand.DEFAULT && state.supportsDynamicTheming) {
            Text(
                modifier = Modifier.padding(
                    bottom = Size8,
                    top = Size16
                ),
                text = stringResource(Res.string.use_dynamic_color),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondary
            )
            SelectableOptionItem(
                modifier = Modifier
                    .padding(top = Size4),
                optionTitle = stringResource(Res.string.yes),
                isFirstItem = true,
                isSelected = state.settingsConfig.useDynamicColor
            ) {
                onAction(
                    SettingsViewModel.Action.UpdateUseDynamicColor(
                        true
                    )
                )
            }
            SelectableOptionItem(
                modifier = Modifier
                    .padding(top = Size4),
                optionTitle = stringResource(Res.string.no),
                isLastItem = true,
                isSelected = !state.settingsConfig.useDynamicColor
            ) {
                onAction(
                    SettingsViewModel.Action.UpdateUseDynamicColor(
                        false
                    )
                )
            }
        }
        Text(
            modifier = Modifier.padding(
                bottom = Size8,
                top = Size16
            ),
            text = stringResource(Res.string.dark_mode_preference),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSecondary
        )
        SelectableOptionItem(
            modifier = Modifier
                .padding(top = Size4),
            optionTitle = stringResource(Res.string.dark_mode_preference_system),
            isFirstItem = true,
            isSelected = state.settingsConfig?.darkThemeConfig == DarkThemeConfig.SYSTEM
        ) {
            onAction(
                SettingsViewModel.Action.UpdateDarkThemeConfig(
                    DarkThemeConfig.SYSTEM
                )
            )
        }
        SelectableOptionItem(
            modifier = Modifier
                .padding(top = Size4),
            optionTitle = stringResource(Res.string.dark_mode_preference_dark),
            isSelected = state.settingsConfig?.darkThemeConfig == DarkThemeConfig.DARK
        ) {
            onAction(
                SettingsViewModel.Action.UpdateDarkThemeConfig(
                    DarkThemeConfig.DARK
                )
            )
        }
        SelectableOptionItem(
            modifier = Modifier
                .padding(top = Size4),
            optionTitle = stringResource(Res.string.dark_mode_preference_light),
            isLastItem = true,
            isSelected = state.settingsConfig?.darkThemeConfig == DarkThemeConfig.LIGHT
        ) {
            onAction(
                SettingsViewModel.Action.UpdateDarkThemeConfig(
                    DarkThemeConfig.LIGHT
                )
            )
        }
    }
}

@Composable
fun PrivacySettingsSubSection(
    state: SettingsViewModel.State,
    onAction: (SettingsViewModel.Action) -> Unit
) {
    Column(
        modifier = Modifier.padding(start = Size16, end = Size16, bottom = Size16)
    ) {
        if (state.isBiometricsAvailable) {
            ToggleOptionItem(
                optionTitle = stringResource(Res.string.unlock_with_biometrics),
                optionDescription = stringResource(Res.string.unlock_with_biometrics_description),
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
        ToggleOptionItem(
            modifier = Modifier.applyIf(state.isBiometricsAvailable) {
                padding(top = Size16)
            },
            optionTitle = stringResource(Res.string.block_screenshot),
            optionDescription = stringResource(Res.string.block_screenshot_description),
            isSelected = state.settingsConfig?.privacySettings?.isBlockScreenshotsEnabled
                ?: false,
            isFirstItem = true,
            isLastItem = true
        ) {
            onAction(
                SettingsViewModel.Action.UpdatePrivacySettings(
                    state.settingsConfig?.privacySettings?.copy(
                        isBlockScreenshotsEnabled = it
                    )
                        ?: PrivacySettings(isBlockScreenshotsEnabled = it)
                )
            )
        }
    }
}

@Composable
fun Dialogs(
    windowSizeClass: WindowWidthSizeClass,
    state: SettingsViewModel.State,
    onAction: (SettingsViewModel.Action) -> Unit,
) {
    if (state.openConfirmDeleteAccountDialog) {
        ConfirmAlertDialog(
            windowWidthSizeClass = windowSizeClass,
            title = stringResource(Res.string.delete_account),
            message = stringResource(Res.string.delete_account_confirm_message),
            confirmButtonText = stringResource(Res.string.delete_account),
            cancelButtonText = stringResource(Res.string.cancel),
            onConfirm = {
                onAction(SettingsViewModel.Action.CloseConfirmDeleteAccountDialog)
                onAction(SettingsViewModel.Action.DeleteAccount)
            },
            onCancel = {
                onAction(SettingsViewModel.Action.CloseConfirmDeleteAccountDialog)
            }
        )
    }
    if (state.openConfirmLogoutDialog) {
        ConfirmAlertDialog(
            windowWidthSizeClass = windowSizeClass,
            title = stringResource(Res.string.logout),
            message = stringResource(Res.string.logout_confirm_message),
            confirmButtonText = stringResource(Res.string.logout),
            cancelButtonText = stringResource(Res.string.cancel),
            onConfirm = {
                onAction(SettingsViewModel.Action.CloseConfirmLogoutDialog)
                onAction(SettingsViewModel.Action.Logout)
            },
            onCancel = {
                onAction(SettingsViewModel.Action.CloseConfirmLogoutDialog)
            }
        )
    }
}