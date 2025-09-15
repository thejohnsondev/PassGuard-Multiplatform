package com.thejohnsondev.ui.model.settings

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.ImportExport
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import com.thejohnsondev.ui.model.IconContainer
import com.thejohnsondev.ui.utils.ResDrawable
import com.thejohnsondev.ui.utils.ResString
import org.jetbrains.compose.resources.StringResource
import vaultmultiplatform.core.ui.generated.resources.about_description
import vaultmultiplatform.core.ui.generated.resources.about_title
import vaultmultiplatform.core.ui.generated.resources.general_description
import vaultmultiplatform.core.ui.generated.resources.general_title
import vaultmultiplatform.core.ui.generated.resources.ic_vault_img_512_blue_gradient
import vaultmultiplatform.core.ui.generated.resources.manage_account
import vaultmultiplatform.core.ui.generated.resources.manage_vault
import vaultmultiplatform.core.ui.generated.resources.security_and_privacy_description
import vaultmultiplatform.core.ui.generated.resources.security_and_privacy_title
import vaultmultiplatform.core.ui.generated.resources.setting_description_style
import vaultmultiplatform.core.ui.generated.resources.setting_export_description
import vaultmultiplatform.core.ui.generated.resources.setting_export_title
import vaultmultiplatform.core.ui.generated.resources.setting_title_style

sealed class SettingsSubSection(
    val sectionTitleRes: StringResource,
    val sectionDescriptionRes: StringResource? = null,
    val sectionIcon: IconContainer,
    val expanded: Boolean,
) {
    data object ManageAccountSub : SettingsSubSection(
        sectionTitleRes = ResString.manage_account,
        sectionIcon = IconContainer(Icons.Default.Person),
        expanded = false
    )

    data object ManageLocalVaultSub : SettingsSubSection(
        sectionTitleRes = ResString.manage_vault,
        sectionIcon = IconContainer(imageVectorResId = ResDrawable.ic_vault_img_512_blue_gradient),
        expanded = false
    )

    data object GeneralSettingsSub : SettingsSubSection(
        sectionTitleRes = ResString.general_title,
        sectionDescriptionRes = ResString.general_description,
        sectionIcon = IconContainer(Icons.Default.Settings),
        expanded = false
    )

    data object StyleSettingsSub : SettingsSubSection(
        sectionTitleRes = ResString.setting_title_style,
        sectionDescriptionRes = ResString.setting_description_style,
        sectionIcon = IconContainer(Icons.Default.FormatPaint),
        expanded = false
    )

    data object ExportSettingsSub : SettingsSubSection(
        sectionTitleRes = ResString.setting_export_title,
        sectionDescriptionRes = ResString.setting_export_description,
        sectionIcon = IconContainer(Icons.Default.ImportExport),
        expanded = false
    )

    data object PrivacySettingsSub : SettingsSubSection(
        sectionTitleRes = ResString.security_and_privacy_title,
        sectionDescriptionRes = ResString.security_and_privacy_description,
        sectionIcon = IconContainer(Icons.Default.Security),
        expanded = false
    )

    data object AboutSettingsSub : SettingsSubSection(
        sectionTitleRes = ResString.about_title,
        sectionDescriptionRes = ResString.about_description,
        sectionIcon = IconContainer(Icons.Default.Info),
        expanded = false
    )

}