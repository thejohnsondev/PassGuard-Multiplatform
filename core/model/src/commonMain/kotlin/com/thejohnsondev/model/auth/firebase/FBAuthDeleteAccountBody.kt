package com.thejohnsondev.model.auth.firebase

import kotlinx.serialization.Serializable

@Serializable
data class FBAuthDeleteAccountBody(
    val idToken: String
)