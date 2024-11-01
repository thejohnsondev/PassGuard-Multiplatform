package com.thejohnsondev.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String
)
