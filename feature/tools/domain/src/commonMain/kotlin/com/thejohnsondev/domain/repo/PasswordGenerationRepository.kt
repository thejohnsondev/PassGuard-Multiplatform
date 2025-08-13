package com.thejohnsondev.domain.repo

import com.thejohnsondev.model.tools.PasswordGeneratedResult
import com.thejohnsondev.model.tools.PasswordGenerationType
import com.thejohnsondev.model.tools.PasswordStrength

interface PasswordGenerationRepository {
    fun generatePassword(
        type: PasswordGenerationType,
        length: Int = 12,
        includeLower: Boolean = true,
        includeUpper: Boolean = true,
        includeDigits: Boolean = true,
        includeSpecial: Boolean = true,
    ): PasswordGeneratedResult

    fun evaluateStrength(password: String): PasswordStrength
    fun isCommonPassword(password: String): Boolean
}