package com.thejohnsondev.domain.repo

import com.thejohnsondev.model.tools.PasswordGeneratorConfig

interface ToolsRepository {
    suspend fun updatePasswordGeneratorConfig(
        config: PasswordGeneratorConfig
    )
    suspend fun getPasswordGeneratorConfig(): PasswordGeneratorConfig
    fun copyText(text: String, isSensitive: Boolean)
}