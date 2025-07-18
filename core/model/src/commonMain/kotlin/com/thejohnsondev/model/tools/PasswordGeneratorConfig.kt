package com.thejohnsondev.model.tools

const val PASSWORD_GENERATOR_DEFAULT_LENGTH = 12
const val PASSWORD_GENERATOR_MIN_LENGTH = 8f
const val PASSWORD_GENERATOR_MAX_LENGTH = 32f

data class PasswordGeneratorConfig(
    val type: PasswordGenerationType = PasswordGenerationType.RANDOM,
    val length: Int = PASSWORD_GENERATOR_DEFAULT_LENGTH,
    val includeLower: Boolean = true,
    val includeUpper: Boolean = true,
    val includeDigits: Boolean = true,
    val includeSpecial: Boolean = true
)