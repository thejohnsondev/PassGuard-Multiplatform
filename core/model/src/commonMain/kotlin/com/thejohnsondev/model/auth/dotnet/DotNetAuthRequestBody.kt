package com.thejohnsondev.model.auth.dotnet

import kotlinx.serialization.Serializable

@Serializable
data class DotNetAuthRequestBody(
    val email: String,
    val password: String
)