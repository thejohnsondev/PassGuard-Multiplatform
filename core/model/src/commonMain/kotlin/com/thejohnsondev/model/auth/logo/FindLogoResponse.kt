package com.thejohnsondev.model.auth.logo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FindLogoResponse(
    @SerialName("name") val name: String,
    @SerialName("domain") val domain: String,
    @SerialName("logo_url") val logoUrl: String
)