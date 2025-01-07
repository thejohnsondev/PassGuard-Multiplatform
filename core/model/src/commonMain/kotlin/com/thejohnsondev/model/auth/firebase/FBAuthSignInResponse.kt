package com.thejohnsondev.model.auth.firebase

import kotlinx.serialization.Serializable

@Serializable
data class FBAuthSignInResponse(
    val idToken: String? = null,
    val email: String? = null,
    val refreshToken: String? = null,
    val expiresIn: String? = null,
    val localId: String? = null,
)
