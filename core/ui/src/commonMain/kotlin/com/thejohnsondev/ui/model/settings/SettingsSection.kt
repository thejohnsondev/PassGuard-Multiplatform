package com.thejohnsondev.ui.model.settings

import com.thejohnsondev.ui.utils.ResString
import org.jetbrains.compose.resources.StringResource
import vaultmultiplatform.core.ui.generated.resources.account

data class SettingsSection(
    val sectionTitleRes: StringResource? = null,
    val subsections: List<SettingsSubSection>,
) {
    companion object {
        fun getSettingsSections(): List<SettingsSection> = listOf(
            SettingsSection(
                subsections = listOf(
                    SettingsSubSection.GeneralSettingsSub,
                    SettingsSubSection.StyleSettingsSub,
                    SettingsSubSection.PrivacySettingsSub
                )
            ),
            SettingsSection(
                sectionTitleRes = ResString.account,
                subsections = listOf(
                    SettingsSubSection.ManageAccountSub
                )
            )
        )
    }
}