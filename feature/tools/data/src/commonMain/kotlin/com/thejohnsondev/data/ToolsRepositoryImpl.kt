package com.thejohnsondev.data

import com.thejohnsondev.datastore.PreferencesDataStore
import com.thejohnsondev.model.tools.PasswordGeneratorConfig
import com.thejohnsondev.platform.utils.ClipboardUtils

class ToolsRepositoryImpl(
    private val preferencesDataStore: PreferencesDataStore,
    private val clipboardUtils: ClipboardUtils,
) : ToolsRepository {

    override suspend fun updatePasswordGeneratorConfig(
        config: PasswordGeneratorConfig,
    ) {
        preferencesDataStore.updatePasswordGeneratorConfig(config)
    }


    override suspend fun getPasswordGeneratorConfig(): PasswordGeneratorConfig {
        return preferencesDataStore.getPasswordGeneratorConfig()
    }

    override fun copyText(text: String, isSensitive: Boolean) {
        if (isSensitive) {
            clipboardUtils.copyToClipboardSensitive(text)
        } else {
            clipboardUtils.copyToClipboard(text)
        }
    }
}