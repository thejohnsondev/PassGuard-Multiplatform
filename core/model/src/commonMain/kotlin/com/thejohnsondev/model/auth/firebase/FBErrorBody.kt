package com.thejohnsondev.model.auth.firebase

import com.thejohnsondev.model.Error
import kotlinx.serialization.Serializable

@Serializable
data class FBErrorBody(
    val error: FirebaseApiErrorBody
): Error()

@Serializable
data class FirebaseApiErrorBody(
    val code: Int,
    val message: String
)