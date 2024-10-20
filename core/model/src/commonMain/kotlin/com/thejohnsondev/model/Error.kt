package com.thejohnsondev.model

class NoInternetConnectionException: Throwable()

open class Error(
    val throwable: Throwable? = null
)

data class HttpError(
    val code: Int,
    val message: String,
) : Error()

data class NetworkError(val cause: Throwable) : Error(cause)

data class UnknownError(val cause: Throwable) : Error(cause)