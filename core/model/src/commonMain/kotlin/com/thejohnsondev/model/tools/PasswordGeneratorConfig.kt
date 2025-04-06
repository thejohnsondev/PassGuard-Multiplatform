package com.thejohnsondev.model.tools

const val PASSWORD_GENERATOR_DEFAULT_LENGTH = 12

data class PasswordGeneratorConfig(
    val type: PasswordGenerationType = PasswordGenerationType.RANDOM,
    val length: Int = PASSWORD_GENERATOR_DEFAULT_LENGTH,
    val includeLower: Boolean = true,
    val includeUpper: Boolean = true,
    val includeDigits: Boolean = true,
    val includeSpecial: Boolean = true
)