package com.thejohnsondev.model.tools

data class PasswordGeneratedResult(
    val password: String,
    val strengthLevel: Float,
    val suggestion: String?
)