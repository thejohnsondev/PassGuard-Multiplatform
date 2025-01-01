package com.thejohnsondev.ui.model.settings

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import vaultmultiplatform.core.ui.generated.resources.Res
import vaultmultiplatform.core.ui.generated.resources.general_description
import vaultmultiplatform.core.ui.generated.resources.general_title
import vaultmultiplatform.core.ui.generated.resources.manage_account
import vaultmultiplatform.core.ui.generated.resources.security_and_privacy_description
import vaultmultiplatform.core.ui.generated.resources.security_and_privacy_title
import vaultmultiplatform.core.ui.generated.resources.setting_description_style
import vaultmultiplatform.core.ui.generated.resources.setting_title_style

sealed class SettingsSubSection(
    val sectionTitleRes: StringResource,
    val sectionDescriptionRes: StringResource? = null,
    val sectionIcon: ImageVector,
    val expanded: Boolean
) {
    data object ManageAccountSub : SettingsSubSection(
        sectionTitleRes = Res.string.manage_account,
        sectionIcon = Icons.Default.Person,
        expanded = false
    )

    data object GeneralSettingsSub : SettingsSubSection(
        sectionTitleRes = Res.string.general_title,
        sectionDescriptionRes = Res.string.general_description,
        sectionIcon = Icons.Default.Settings,
        expanded = false
    )

    data object StyleSettingsSub : SettingsSubSection(
        sectionTitleRes = Res.string.setting_title_style,
        sectionDescriptionRes = Res.string.setting_description_style,
        sectionIcon = Icons.Default.FormatPaint,
        expanded = false
    )

    data object PrivacySettingsSub : SettingsSubSection(
        sectionTitleRes = Res.string.security_and_privacy_title,
        sectionDescriptionRes = Res.string.security_and_privacy_description,
        sectionIcon = Icons.Default.Security,
        expanded = false
    )

}