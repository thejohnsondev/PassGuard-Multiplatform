package com.thejohnsondev.model.auth.firebase

import kotlinx.serialization.Serializable

@Serializable
data class FBAuthRequestBody(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean
)
