package com.thejohnsondev.model.auth.dotnet

import kotlinx.serialization.Serializable

@Serializable
data class DotNetAuthResponse(
    val token: String
)
