package com.thejohnsondev.model.tools

data class PasswordGeneratedResult(
    val password: String,
    val strengthLevel: Int,
    val suggestion: String?
)