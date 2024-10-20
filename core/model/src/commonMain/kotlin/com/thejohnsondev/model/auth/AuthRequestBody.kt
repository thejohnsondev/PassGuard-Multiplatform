package com.thejohnsondev.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequestBody(
    val email: String,
    val password: String
)