package com.thejohnsondev.model.auth.firebase

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FBRefreshTokenResponseBody(
    @SerialName("id_token") val idToken: String,
    @SerialName("refresh_token") val refreshToken: String,
)