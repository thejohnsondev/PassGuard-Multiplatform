package com.thejohnsondev.model.auth.firebase

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

const val GRAND_TYPE_REFRESH = "refresh_token"

@Serializable
data class FBRefreshTokenRequestBody(
    @SerialName("grant_type") val grantType: String,
    @SerialName("refresh_token") val refreshToken: String
)
