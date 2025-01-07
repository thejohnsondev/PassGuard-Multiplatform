package com.thejohnsondev.model.auth.firebase

import kotlinx.serialization.Serializable

@Serializable
data class FBErrorBody(
    val error: FirebaseApiErrorBody
)

@Serializable
data class FirebaseApiErrorBody(
    val code: Int,
    val message: String
)