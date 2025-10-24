package com.thejohnsondev.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Commit
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Copyright
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Support
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import com.thejohnsondev.analytics.Analytics
import com.thejohnsondev.common.EXPAND_ANIM_DURATION
import com.thejohnsondev.common.model.OneTimeEvent
import com.thejohnsondev.common.model.settings.DarkThemeConfig
import com.thejohnsondev.common.model.settings.GeneralSettings
import com.thejohnsondev.common.model.settings.PrivacySettings
import com.thejohnsondev.common.model.settings.ThemeBrand
import com.thejohnsondev.localization.Language
import com.thejohnsondev.presentation.confirmdelete.DeleteAccountPasswordConfirmDialog
import com.thejohnsondev.presentation.exportv.ExportPasswordsScreen
import com.thejohnsondev.presentation.importv.ImportPasswordsScreen
import com.thejohnsondev.ui.components.CountryFlagItem
import com.thejohnsondev.ui.components.ExpandableSectionItem
import com.thejohnsondev.ui.components.HalfColoredCircle
import com.thejohnsondev.ui.components.MiniSelectableOptionItem
import com.thejohnsondev.ui.components.SelectableOptionItem
import com.thejohnsondev.ui.components.animation.CardWithAnimatedBorder
import com.thejohnsondev.ui.components.animation.getDefaultAnimatedBorderColors
import com.thejohnsondev.ui.components.button.RoundedButton
import com.thejohnsondev.ui.components.button.ToggleOptionItem
import com.thejohnsondev.ui.components.container.RoundedContainer
import com.thejohnsondev.ui.components.dialog.ConfirmAlertDialog
import com.thejohnsondev.ui.designsystem.EquallyRounded
import com.thejohnsondev.ui.designsystem.Percent100
import com.thejohnsondev.ui.designsystem.Percent80
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size2
import com.thejohnsondev.ui.designsystem.Size24
import com.thejohnsondev.ui.designsystem.Size36
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size64
import com.thejohnsondev.ui.designsystem.Size72
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.DefaultSelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.SelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.DeepForestSelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.MaterialSelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.MonochromeSelectableItemsColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.RedAlgaeSelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.SunnySelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.TealSelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.VioletSelectableItemsColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.tools.ToolSelectableItemColors
import com.thejohnsondev.ui.displaymessage.getAsText
import com.thejohnsondev.ui.model.ScaffoldConfig
import com.thejohnsondev.ui.model.getImageVector
import com.thejohnsondev.ui.model.message.MessageContent
import com.thejohnsondev.ui.model.message.MessageType
import com.thejohnsondev.ui.model.settings.SettingsSection
import com.thejohnsondev.ui.model.settings.SettingsSubSection
import com.thejohnsondev.ui.scaffold.BottomNavItem
import com.thejohnsondev.ui.utils.ResDrawable
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.isCompact
import com.thejohnsondev.ui.utils.padding
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import vaultmultiplatform.core.ui.generated.resources.block_screenshot
import vaultmultiplatform.core.ui.generated.resources.block_screenshot_description
import vaultmultiplatform.core.ui.generated.resources.cancel
import vaultmultiplatform.core.ui.generated.resources.confirm
import vaultmultiplatform.core.ui.generated.resources.contact_email_description
import vaultmultiplatform.core.ui.generated.resources.contact_info
import vaultmultiplatform.core.ui.generated.resources.contact_info_description
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
import vaultmultiplatform.core.ui.generated.resources.ic_export_monochrome
import vaultmultiplatform.core.ui.generated.resources.ic_import_monochrome
import vaultmultiplatform.core.ui.generated.resources.ic_vault_108_gradient
import vaultmultiplatform.core.ui.generated.resources.language
import vaultmultiplatform.core.ui.generated.resources.last_update_hash_placeholder
import vaultmultiplatform.core.ui.generated.resources.last_update_placeholder
import vaultmultiplatform.core.ui.generated.resources.legal_info_description
import vaultmultiplatform.core.ui.generated.resources.legal_info_open_source_license
import vaultmultiplatform.core.ui.generated.resources.legal_info_open_source_license_link
import vaultmultiplatform.core.ui.generated.resources.legal_info_title
import vaultmultiplatform.core.ui.generated.resources.license_info
import vaultmultiplatform.core.ui.generated.resources.license_info_arrow
import vaultmultiplatform.core.ui.generated.resources.license_info_arrow_description
import vaultmultiplatform.core.ui.generated.resources.license_info_arrow_url
import vaultmultiplatform.core.ui.generated.resources.license_info_coil
import vaultmultiplatform.core.ui.generated.resources.license_info_coil_description
import vaultmultiplatform.core.ui.generated.resources.license_info_coil_url
import vaultmultiplatform.core.ui.generated.resources.license_info_description
import vaultmultiplatform.core.ui.generated.resources.license_info_haze
import vaultmultiplatform.core.ui.generated.resources.license_info_haze_description
import vaultmultiplatform.core.ui.generated.resources.license_info_haze_url
import vaultmultiplatform.core.ui.generated.resources.license_info_koin
import vaultmultiplatform.core.ui.generated.resources.license_info_koin_description
import vaultmultiplatform.core.ui.generated.resources.license_info_koin_url
import vaultmultiplatform.core.ui.generated.resources.license_info_konnection
import vaultmultiplatform.core.ui.generated.resources.license_info_konnection_description
import vaultmultiplatform.core.ui.generated.resources.license_info_konnection_url
import vaultmultiplatform.core.ui.generated.resources.license_info_logos_provided
import vaultmultiplatform.core.ui.generated.resources.license_info_mockk
import vaultmultiplatform.core.ui.generated.resources.license_info_mockk_description
import vaultmultiplatform.core.ui.generated.resources.license_info_mockk_url
import vaultmultiplatform.core.ui.generated.resources.license_info_nappier
import vaultmultiplatform.core.ui.generated.resources.license_info_nappier_description
import vaultmultiplatform.core.ui.generated.resources.license_info_nappier_url
import vaultmultiplatform.core.ui.generated.resources.license_info_posthog
import vaultmultiplatform.core.ui.generated.resources.license_info_posthog_description
import vaultmultiplatform.core.ui.generated.resources.license_info_posthog_url
import vaultmultiplatform.core.ui.generated.resources.license_info_sqldelight
import vaultmultiplatform.core.ui.generated.resources.license_info_sqldelight_description
import vaultmultiplatform.core.ui.generated.resources.license_info_sqldelight_url
import vaultmultiplatform.core.ui.generated.resources.logout
import vaultmultiplatform.core.ui.generated.resources.logout_confirm_message
import vaultmultiplatform.core.ui.generated.resources.manage_account
import vaultmultiplatform.core.ui.generated.resources.no
import vaultmultiplatform.core.ui.generated.resources.privacy_policy
import vaultmultiplatform.core.ui.generated.resources.privacy_policy_link
import vaultmultiplatform.core.ui.generated.resources.setting_export_passwords
import vaultmultiplatform.core.ui.generated.resources.setting_import_passwords
import vaultmultiplatform.core.ui.generated.resources.settings
import vaultmultiplatform.core.ui.generated.resources.terms_of_use
import vaultmultiplatform.core.ui.generated.resources.terms_of_use_link
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
import vaultmultiplatform.core.ui.generated.resources.version_info
import vaultmultiplatform.core.ui.generated.resources.version_info_description
import vaultmultiplatform.core.ui.generated.resources.version_name_placeholder
import vaultmultiplatform.core.ui.generated.resources.yes

@Composable
fun SettingsScreen(
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    viewModel: SettingsViewModel,
    setScaffoldConfig: (ScaffoldConfig) -> Unit,
    onLogoutClick: () -> Unit,
    onGoToSignUp: () -> Unit,
    onShowMessage: (MessageContent) -> Unit,
) {
    val state = viewModel.state.collectAsState(SettingsViewModel.State())
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        Analytics.trackScreen("Settings Screen")
    }

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
                is OneTimeEvent.ErrorMessage -> onShowMessage(
                    MessageContent(
                        message = it.message.getAsText(),
                        type = MessageType.ERROR,
                        imageVector = Icons.Default.Error
                    )
                )

                is OneTimeEvent.InfoMessage -> onShowMessage(
                    MessageContent(
                        message = it.message.getAsText(),
                        type = MessageType.INFO,
                        imageVector = Icons.Default.Info
                    )
                )
            }
        }
    }
    LaunchedEffect(state.value.selectedLanguage) {
        setScaffoldConfig(
            ScaffoldConfig(
                topAppBarTitle = getString(ResString.settings),
                topAppBarIcon = Icons.Default.Settings,
                bottomBarItemIndex = BottomNavItem.Settings.index
            )
        )
    }

    SettingsContent(
        state = state.value,
        windowSizeClass = windowSizeClass,
        paddingValues = paddingValues,
        onAction = viewModel::perform,
        goToSignUp = onGoToSignUp,
        openUrl = {
            uriHandler.openUri(it)
        }
    )
    Dialogs(
        windowSizeClass = windowSizeClass,
        paddingValues = paddingValues,
        state = state.value,
        onAction = viewModel::perform
    )
}

@Composable
fun SettingsContent(
    state: SettingsViewModel.State,
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    onAction: (SettingsViewModel.Action) -> Unit,
    goToSignUp: () -> Unit,
    openUrl: (String) -> Unit
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
                }
                .semantics {
                    contentDescription = "Vertical Scroll"
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            SettingsList(
                state = state,
                paddingValues = paddingValues,
                onAction = onAction, goToSignUp = goToSignUp,
                openUrl = openUrl
            )
        }
    }
}

@Composable
fun SettingsList(
    state: SettingsViewModel.State,
    onAction: (SettingsViewModel.Action) -> Unit,
    goToSignUp: () -> Unit,
    paddingValues: PaddingValues,
    openUrl: (String) -> Unit
) {
    val colors = ToolSelectableItemColors()
    Column(
        modifier = Modifier
            .padding(
                top = paddingValues.calculateTopPadding(),
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
                            bottom = Size4
                        ),
                    state = state,
                    subSection = subsection,
                    subSectionsNumber = subSectionsNumber,
                    subSectionIndex = index,
                    onAction = onAction,
                    goToSignUp = goToSignUp,
                    colors = colors,
                    openUrl = openUrl
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
    colors: SelectableItemColors,
    openUrl: (String) -> Unit
) {
    val subsectionDescription =
        if (subSection.sectionTitleRes == ResString.manage_account) {
            state.userEmail.orEmpty()
        } else {
            subSection.sectionDescriptionRes?.let { stringResource(resource = it) }
        }
    var isSectionExpanded by remember {
        mutableStateOf(false)
    }
    val transitionState = remember {
        MutableTransitionState(isSectionExpanded).apply {
            targetState = !isSectionExpanded
        }
    }
    val transition = rememberTransition(transitionState, label = "")
    val cardPaddingHorizontal by transition.animateDp({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isSectionExpanded) Size8 else Size16
    }
    ExpandableSectionItem(
        modifier = modifier
            .padding(horizontal = cardPaddingHorizontal),
        title = stringResource(resource = subSection.sectionTitleRes),
        description = subsectionDescription,
        icon = subSection.sectionIcon.getImageVector(),
        isFirstItem = subSectionIndex == 0,
        isLastItem = subSectionIndex == subSectionsNumber - 1,
        onExpanded = {
            Analytics.trackEvent(
                "settings_subsection_expanded", mapOf(
                    "subsection" to subSection.sectionTitleRes.key,
                    "is_expanded" to it
                )
            )
            isSectionExpanded = it
        },
        colors = colors
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
                    goToSignUp = goToSignUp,
                    colors = colors
                )
            }

            SettingsSubSection.GeneralSettingsSub -> {
                GeneralSettingsSubSection(state = state, onAction = onAction)
            }

            SettingsSubSection.StyleSettingsSub -> {
                StyleSettingsSubSection(
                    state = state,
                    onAction = onAction,
                    colors = colors
                )
            }

            SettingsSubSection.ExportSettingsSub -> {
                ExportSettingsSubSection(
                    onAction = onAction
                )
            }

            SettingsSubSection.PrivacySettingsSub -> {
                PrivacySettingsSubSection(state = state, onAction = onAction)
            }

            SettingsSubSection.AboutSettingsSub -> {
                AboutSettingsSubSection(state = state, onAction = onAction, openUrl = openUrl)
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
            .padding(start = Size16, end = Size16, bottom = Size16, top = Size2)
            .fillMaxWidth(),
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
                .padding(Size16)
                .fillMaxWidth(),
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
    colors: SelectableItemColors,
) {
    // disabled for the first release
//    RoundedButton(
//        modifier = Modifier
//            .height(Size72)
//            .padding(start = Size16, end = Size16, bottom = Size8, top = Size2)
//            .fillMaxWidth(),
//        text = stringResource(resource = ResString.create_account),
//        onClick = {
//            goToSignUp()
//        },
//        colors = ButtonDefaults.buttonColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer,
//            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
//        )
//    )
//    Row(
//        modifier = Modifier.padding(start = Size16, end = Size16, bottom = Size8),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(
//            imageVector = Icons.Default.Info,
//            tint = colors.getSelectedContentColor(),
//            contentDescription = null
//        )
//        Text(
//            modifier = Modifier
//                .padding(start = Size4),
//            text = stringResource(ResString.create_account_description),
//            color = colors.getSelectedContentColor(),
//            style = MaterialTheme.typography.bodySmall
//        )
//    }
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
                .padding(Size16)
                .fillMaxWidth(),
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
        Text(
            modifier = Modifier.padding(vertical = Size8),
            text = stringResource(ResString.language),
            style = MaterialTheme.typography.titleLarge
        )
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Language.entries.forEach { language ->
                MiniSelectableOptionItem(
                    modifier = Modifier
                        .padding(
                            start = Size4,
                            end = Size4,
                        ),
                    optionTitle = stringResource(language.typeNameStringResource),
                    isSelected = state.selectedLanguage == language,
                    optionContent = {
                        CountryFlagItem(
                            modifier = Modifier
                                .size(Size36),
                            flagDrawableResource = language.typeFlagDrawableResource
                        )
                    }
                ) {
                    onAction(SettingsViewModel.Action.SelectLanguage(language))
                }
            }
        }
    }
}

@Composable
fun StyleSettingsSubSection(
    state: SettingsViewModel.State,
    onAction: (SettingsViewModel.Action) -> Unit,
    colors: SelectableItemColors,
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
                color = colors.getSelectedContentColor()
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
            AnimatedVisibility(state.settingsConfig?.customTheme == ThemeBrand.DEFAULT && state.supportsDynamicTheming) {
                Column {
                    Text(
                        modifier = Modifier.padding(
                            bottom = Size8,
                            top = Size8
                        ),
                        text = stringResource(ResString.use_dynamic_color),
                        style = MaterialTheme.typography.titleLarge,
                        color = colors.getSelectedContentColor()
                    )
                    SelectableOptionItem(
                        modifier = Modifier
                            .padding(top = Size4),
                        optionTitle = stringResource(ResString.yes),
                        isFirstItem = true,
                        isSelected = state.settingsConfig?.useDynamicColor == true
                    ) {
                        onAction(SettingsViewModel.Action.UpdateUseDynamicColor(true))
                    }
                    SelectableOptionItem(
                        modifier = Modifier
                            .padding(top = Size4),
                        optionTitle = stringResource(ResString.no),
                        isLastItem = true,
                        isSelected = state.settingsConfig?.useDynamicColor != true
                    ) {
                        onAction(SettingsViewModel.Action.UpdateUseDynamicColor(false))
                    }
                }
            }
            Text(
                modifier = Modifier.padding(vertical = Size8),
                text = stringResource(ResString.theme),
                style = MaterialTheme.typography.titleLarge,
                color = colors.getSelectedContentColor()
            )
        }
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState())
                .padding(bottom = Size16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ThemeBrand.entries.forEachIndexed { index, theme ->
                val color = when (theme) {
                    ThemeBrand.DEFAULT -> DefaultSelectableItemColors
                    ThemeBrand.TEAL -> TealSelectableItemColors
                    ThemeBrand.DEEP_FOREST -> DeepForestSelectableItemColors
                    ThemeBrand.RED_ALGAE -> RedAlgaeSelectableItemColors()
                    ThemeBrand.SUNNY -> SunnySelectableItemColors
                    ThemeBrand.VIOLET -> VioletSelectableItemsColors
                    ThemeBrand.MONOCHROME -> MonochromeSelectableItemsColors
                    else -> DefaultSelectableItemColors
                }
                val selectedContainerColor = color.getSelectedContainerColor()
                val selectedContentColor = color.getSelectedContentColor()
                MiniSelectableOptionItem(
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
                    optionContent = {
                        HalfColoredCircle(
                            modifier = Modifier.size(Size36),
                            color1 = selectedContainerColor,
                            color2 = selectedContentColor
                        )
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
fun ExportSettingsSubSection(
    onAction: (SettingsViewModel.Action) -> Unit,
) {
    Column(
        modifier = Modifier.padding(start = Size16, end = Size16, bottom = Size16)
    ) {
        LargeInfoButton(
            modifier = Modifier
                .padding(bottom = Size4)
                .fillMaxWidth(),
            description = stringResource(ResString.setting_export_passwords),
            icon = vectorResource(ResDrawable.ic_export_monochrome),
            onClick = {
                onAction(SettingsViewModel.Action.OpenCloseExportPasswords(true))
            },
            isFirstItem = true,
            colors = MaterialSelectableItemColors
        )
        LargeInfoButton(
            modifier = Modifier
                .fillMaxWidth(),
            description = stringResource(ResString.setting_import_passwords),
            icon = vectorResource(ResDrawable.ic_import_monochrome),
            onClick = {
                onAction(SettingsViewModel.Action.OpenCloseImportPasswords(true))
            },
            isLastItem = true,
            colors = MaterialSelectableItemColors
        )
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
fun AboutSettingsSubSection(
    state: SettingsViewModel.State,
    onAction: (SettingsViewModel.Action) -> Unit,
    openUrl: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(start = Size16, end = Size16, bottom = Size16)
    ) {
        ExpandableSectionItem(
            modifier = Modifier
                .fillMaxWidth(),
            title = stringResource(ResString.version_info),
            description = stringResource(ResString.version_info_description),
            icon = Icons.Default.Commit,
            isFirstItem = true
        ) {
            VersionInfoSubSection(state = state)
        }
        ExpandableSectionItem(
            modifier = Modifier
                .padding(top = Size4)
                .fillMaxWidth(),
            title = stringResource(ResString.contact_info),
            description = stringResource(ResString.contact_info_description),
            icon = Icons.Default.Support
        ) {
            ContactInfoSubsection(
                state = state,
                onAction = onAction
            )
        }
        ExpandableSectionItem(
            modifier = Modifier
                .padding(top = Size4)
                .fillMaxWidth(),
            title = stringResource(ResString.license_info),
            description = stringResource(ResString.license_info_description),
            icon = Icons.Default.Copyright
        ) {
            LicenseInfoSubsection(
                state = state,
                openUrl = openUrl
            )
        }
        ExpandableSectionItem(
            modifier = Modifier
                .padding(top = Size4)
                .fillMaxWidth(),
            title = stringResource(ResString.legal_info_title),
            description = stringResource(ResString.legal_info_description),
            icon = Icons.Default.Balance,
            isLastItem = true
        ) {
            LegalInfoSubsection(
                openUrl = openUrl
            )
        }
    }
}

@Composable
private fun VersionInfoSubSection(
    state: SettingsViewModel.State
) {
    CardWithAnimatedBorder(
        modifier = Modifier
            .padding(horizontal = Size8, bottom = Size8),
        borderColors = getDefaultAnimatedBorderColors()
    ) {
        RoundedContainer(
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(Size16)
                        .size(Size64),
                    imageVector = vectorResource(ResDrawable.ic_vault_108_gradient),
                    contentDescription = null,
                )
                Column(
                    modifier = Modifier
                        .weight(Percent100),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = stringResource(
                            ResString.version_name_placeholder,
                            state.versionInfo?.versionName.orEmpty()
                        )
                    )
                    state.versionInfo?.lastUpdateTime?.let { lastUpdateTime ->
                        Text(
                            text = stringResource(
                                ResString.last_update_placeholder,
                                lastUpdateTime
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Thin
                        )
                    }
                    state.versionInfo?.lastUpdateHash?.let { lastUpdateHash ->
                        Text(
                            text = stringResource(
                                ResString.last_update_hash_placeholder,
                                lastUpdateHash
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Thin
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ContactInfoSubsection(
    state: SettingsViewModel.State,
    onAction: (SettingsViewModel.Action) -> Unit
) {
    val email = state.contactInfo?.developerEmail.orEmpty()

    RoundedContainer(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Size8, bottom = Size8),
        color = MaterialTheme.colorScheme.surfaceContainerHigh
    ) {
        Row(
            modifier = Modifier
                .padding(Size16)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier.weight(Percent100),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = stringResource(ResString.contact_email_description),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Thin,
                    color = MaterialTheme.colorScheme.onSurface
                )
                RoundedContainer(
                    modifier = Modifier
                        .padding(top = Size8),
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = EquallyRounded.small
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = Size4, horizontal = Size8),
                        text = email,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }
            IconButton(
                onClick = {
                    onAction(SettingsViewModel.Action.CopyToClipboard(email))
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Copy Email"
                )
            }
        }
    }
}

@Composable
private fun LicenseInfoSubsection(
    state: SettingsViewModel.State,
    openUrl: (String) -> Unit
) {
    val hazeUrl = stringResource(ResString.license_info_haze_url)
    val koinUrl = stringResource(ResString.license_info_koin_url)
    val postHogUrl = stringResource(ResString.license_info_posthog_url)
    val nappierUrl = stringResource(ResString.license_info_nappier_url)
    val arrowUrl = stringResource(ResString.license_info_arrow_url)
    val mockkUrl = stringResource(ResString.license_info_mockk_url)
    val sqlDelightUrl = stringResource(ResString.license_info_sqldelight_url)
    val coiltUrl = stringResource(ResString.license_info_coil_url)
    val konnectionUrl = stringResource(ResString.license_info_konnection_url)
    LargeInfoButton(
        modifier = Modifier
            .padding(bottom = Size4, horizontal = Size8)
            .fillMaxWidth(),
        name = state.licenseInfo?.logoProviderName.orEmpty(),
        description = stringResource(ResString.license_info_logos_provided),
        onClick = {
            openUrl(state.licenseInfo?.logoProviderUrl.orEmpty())
        },
        isFirstItem = true
    )
    LargeInfoButton(
        modifier = Modifier
            .padding(bottom = Size4, horizontal = Size8)
            .fillMaxWidth(),
        name = stringResource(ResString.license_info_haze),
        description = stringResource(ResString.license_info_haze_description),
        onClick = {
            openUrl(hazeUrl)
        }
    )
    LargeInfoButton(
        modifier = Modifier
            .padding(bottom = Size4, horizontal = Size8)
            .fillMaxWidth(),
        name = stringResource(ResString.license_info_koin),
        description = stringResource(ResString.license_info_koin_description),
        onClick = {
            openUrl(koinUrl)
        }
    )
    LargeInfoButton(
        modifier = Modifier
            .padding(bottom = Size4, horizontal = Size8)
            .fillMaxWidth(),
        name = stringResource(ResString.license_info_posthog),
        description = stringResource(ResString.license_info_posthog_description),
        onClick = {
            openUrl(postHogUrl)
        }
    )
    LargeInfoButton(
        modifier = Modifier
            .padding(bottom = Size4, horizontal = Size8)
            .fillMaxWidth(),
        name = stringResource(ResString.license_info_nappier),
        description = stringResource(ResString.license_info_nappier_description),
        onClick = {
            openUrl(nappierUrl)
        }
    )
    LargeInfoButton(
        modifier = Modifier
            .padding(bottom = Size4, horizontal = Size8)
            .fillMaxWidth(),
        name = stringResource(ResString.license_info_arrow),
        description = stringResource(ResString.license_info_arrow_description),
        onClick = {
            openUrl(arrowUrl)
        }
    )
    LargeInfoButton(
        modifier = Modifier
            .padding(bottom = Size4, horizontal = Size8)
            .fillMaxWidth(),
        name = stringResource(ResString.license_info_mockk),
        description = stringResource(ResString.license_info_mockk_description),
        onClick = {
            openUrl(mockkUrl)
        }
    )
    LargeInfoButton(
        modifier = Modifier
            .padding(bottom = Size4, horizontal = Size8)
            .fillMaxWidth(),
        name = stringResource(ResString.license_info_sqldelight),
        description = stringResource(ResString.license_info_sqldelight_description),
        onClick = {
            openUrl(sqlDelightUrl)
        }
    )
    LargeInfoButton(
        modifier = Modifier
            .padding(bottom = Size4, horizontal = Size8)
            .fillMaxWidth(),
        name = stringResource(ResString.license_info_coil),
        description = stringResource(ResString.license_info_coil_description),
        onClick = {
            openUrl(coiltUrl)
        }
    )
    LargeInfoButton(
        modifier = Modifier
            .padding(bottom = Size8, horizontal = Size8)
            .fillMaxWidth(),
        name = stringResource(ResString.license_info_konnection),
        description = stringResource(ResString.license_info_konnection_description),
        onClick = {
            openUrl(konnectionUrl)
        },
        isLastItem = true
    )
}

@Composable
private fun LegalInfoSubsection(
    openUrl: (String) -> Unit
) {
    val privacyPolicyLink = stringResource(ResString.privacy_policy_link)
    val termsOfUseLink = stringResource(ResString.terms_of_use_link)
    val openSourceLicenseLink = stringResource(ResString.legal_info_open_source_license_link)

    LargeInfoButton(
        modifier = Modifier
            .padding(bottom = Size4, horizontal = Size8)
            .fillMaxWidth(),
        description = stringResource(ResString.privacy_policy),
        icon = Icons.Default.PrivacyTip,
        isFirstItem = true,
        onClick = {
            openUrl(privacyPolicyLink)
        }
    )
    LargeInfoButton(
        modifier = Modifier
            .padding(bottom = Size4, horizontal = Size8)
            .fillMaxWidth(),
        description = stringResource(ResString.terms_of_use),
        icon = Icons.AutoMirrored.Filled.Article,
        onClick = {
            openUrl(termsOfUseLink)
        }
    )
    LargeInfoButton(
        modifier = Modifier
            .padding(bottom = Size8, horizontal = Size8)
            .fillMaxWidth(),
        description = stringResource(ResString.legal_info_open_source_license),
        icon = Icons.Default.Code,
        isLastItem = true,
        onClick = {
            openUrl(openSourceLicenseLink)
        }
    )
}

@Composable
fun LargeInfoButton(
    modifier: Modifier = Modifier,
    name: String? = null,
    description: String? = null,
    icon: ImageVector = Icons.AutoMirrored.Default.LibraryBooks,
    isFirstItem: Boolean = false,
    isLastItem: Boolean = false,
    colors: SelectableItemColors = ToolSelectableItemColors(),
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(
        topStart = if (isFirstItem) Size16 else Size4,
        topEnd = if (isFirstItem) Size16 else Size4,
        bottomStart = if (isLastItem) Size16 else Size4,
        bottomEnd = if (isLastItem) Size16 else Size4
    )
    RoundedContainer(
        modifier = modifier,
        color = colors.getSelectedContainerColor(),
        onClick = {
            onClick()
        },
        shape = shape
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Surface(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(Size16)
                        .clip(EquallyRounded.small),
                    color = colors.getSelectedContentColor()
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(Size8)
                            .size(Size24),
                        imageVector = icon,
                        contentDescription = "icon",
                        tint = colors.getSelectedContainerColor()
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(vertical = Size12),
                    horizontalAlignment = Alignment.Start
                ) {
                    name?.let {
                        RoundedContainer(
                            color = colors.getSelectedContentColor(),
                            shape = EquallyRounded.small
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(Size4),
                                text = name,
                                style = MaterialTheme.typography.bodyLarge,
                                color = colors.getSelectedContainerColor()
                            )
                        }
                    }
                    description?.let {
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Normal,
                            color = colors.getSelectedContentColor()
                        )
                    }
                }
            }
            Icon(
                modifier = Modifier
                    .padding(Size12),
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dialogs(
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    state: SettingsViewModel.State,
    onAction: (SettingsViewModel.Action) -> Unit,
) {
    val exportPasswordsSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val importPasswordsSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (state.isConfirmDeleteAccountDialogOpened) {
        ConfirmAlertDialog(
            windowWidthSizeClass = windowSizeClass,
            title = stringResource(ResString.delete_account),
            message = stringResource(ResString.delete_account_confirm_message),
            confirmButtonText = stringResource(ResString.confirm),
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
            confirmButtonText = stringResource(ResString.confirm),
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
            confirmButtonText = stringResource(ResString.confirm),
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
    if (state.isExportPasswordsDialogOpened) {
        ExportPasswordsScreen(
            windowSizeClass = windowSizeClass,
            paddingValues = paddingValues,
            sheetState = exportPasswordsSheetState,
            onDismissRequest = {
                onAction(SettingsViewModel.Action.OpenCloseExportPasswords(false))
            },
            onExportSuccessful = {
                onAction(SettingsViewModel.Action.OnExportSuccessful)
                onAction(SettingsViewModel.Action.OpenCloseExportPasswords(false))
            },
            onExportError = {
                onAction(SettingsViewModel.Action.OnExportError(it))
                onAction(SettingsViewModel.Action.OpenCloseExportPasswords(false))
            }
        )
    }
    if (state.isImportPasswordsDialogOpened) {
        ImportPasswordsScreen(
            windowSizeClass = windowSizeClass,
            paddingValues = paddingValues,
            sheetState = importPasswordsSheetState,
            onDismissRequest = {
                onAction(SettingsViewModel.Action.OpenCloseImportPasswords(false))
            },
            onImportSuccessful = {
                onAction(SettingsViewModel.Action.OnImportSuccessful)
                onAction(SettingsViewModel.Action.OpenCloseImportPasswords(false))
            },
            onImportError = {
                onAction(SettingsViewModel.Action.OnImportError(it))
                onAction(SettingsViewModel.Action.OpenCloseImportPasswords(false))
            }
        )
    }
}