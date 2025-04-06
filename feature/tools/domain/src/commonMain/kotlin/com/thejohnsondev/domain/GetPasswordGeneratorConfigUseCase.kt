package com.thejohnsondev.domain

import com.thejohnsondev.model.tools.PasswordGeneratorConfig

interface GetPasswordGeneratorConfigUseCase {
    suspend operator fun invoke(): PasswordGeneratorConfig
}