package com.thejohnsondev.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

class NoInternetConnectionException: Throwable()

@Serializable
open class Error(
    @Transient val throwable: Throwable? = null
)

data class HttpError(
    val code: Int,
    val message: String,
) : Error()

data class NetworkError(val cause: Throwable) : Error(cause)

data class UnknownError(val cause: Throwable) : Error(cause)
data object InvalidTokenError : Error()
data object LoginAgainError : Error()