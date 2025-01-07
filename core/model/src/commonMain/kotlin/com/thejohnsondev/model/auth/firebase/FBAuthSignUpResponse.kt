package com.thejohnsondev.model.auth.firebase

import kotlinx.serialization.Serializable

@Serializable
data class FBAuthSignUpResponse(
    val idToken: String? = null,
    val email: String? = null,
    val refreshToken: String? = null,
    val expiresIn: String? = null,
    val localId: String? = null,
)
