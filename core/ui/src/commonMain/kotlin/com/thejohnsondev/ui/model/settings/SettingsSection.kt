package com.thejohnsondev.ui.model.settings

import com.thejohnsondev.ui.utils.ResString
import org.jetbrains.compose.resources.StringResource
import vaultmultiplatform.core.ui.generated.resources.account
import vaultmultiplatform.core.ui.generated.resources.vault

data class SettingsSection(
    val sectionTitleRes: StringResource? = null,
    val subsections: List<SettingsSubSection>,
) {
    companion object {
        private val settingsSection = SettingsSection(
            subsections = listOf(
                SettingsSubSection.GeneralSettingsSub,
                SettingsSubSection.StyleSettingsSub,
                SettingsSubSection.ExportSettingsSub,
                SettingsSubSection.PrivacySettingsSub,
                SettingsSubSection.AboutSettingsSub
            )
        )
        private val cloudVaultManageSection = SettingsSection(
            sectionTitleRes = ResString.account,
            subsections = listOf(
                SettingsSubSection.ManageAccountSub
            )
        )
        private val localVaultManageSection = SettingsSection(
            sectionTitleRes = ResString.vault,
            subsections = listOf(
                SettingsSubSection.ManageLocalVaultSub
            )
        )
        fun getCloudVaultSettingsSections(): List<SettingsSection> = listOf(
            settingsSection,
            cloudVaultManageSection
        )
        fun getLocalVaultSettingsSections(): List<SettingsSection> = listOf(
            settingsSection,
            localVaultManageSection
        )
    }
}