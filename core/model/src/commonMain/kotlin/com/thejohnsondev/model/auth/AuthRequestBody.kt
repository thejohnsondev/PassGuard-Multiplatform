package com.thejohnsondev.model.auth

data class AuthRequestBody(
    val email: String,
    val password: String
)