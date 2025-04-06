package com.thejohnsondev.data

import com.thejohnsondev.datastore.PreferencesDataStore
import com.thejohnsondev.model.tools.PasswordGeneratorConfig

class ToolsRepositoryImpl(
    private val preferencesDataStore: PreferencesDataStore,
) : ToolsRepository {
    override suspend fun updatePasswordGeneratorConfig(
        config: PasswordGeneratorConfig,
    ) {
        preferencesDataStore.updatePasswordGeneratorConfig(config)
    }


    override suspend fun getPasswordGeneratorConfig(): PasswordGeneratorConfig {
        return preferencesDataStore.getPasswordGeneratorConfig()
    }
}