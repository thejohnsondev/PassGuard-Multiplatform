package com.thejohnsondev.network

import arrow.core.Either
import com.thejohnsondev.common.utils.Logger
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.HttpError
import com.thejohnsondev.model.NetworkError
import com.thejohnsondev.model.NoInternetConnectionException
import com.thejohnsondev.model.UnknownError
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T> HttpResponse.mapToResponse(): Either<Error, T> {
    return Either.catch {
        val response = this
        return when (response.status.value) {
            in 200..300 -> {
                Either.Right(response.body<T>())
            }

            401 -> {
                Either.Left(HttpError(response.status.value, "Unauthorized"))
            }

            in 400..500 -> {
                Either.Left(HttpError(response.status.value, "Bad Request"))
            }

            in 500..600 -> {
                Either.Left(HttpError(response.status.value, "Internal Server Error"))
            }

            else -> {
                Either.Left(HttpError(response.status.value, ""))
            }
        }
    }.mapLeft {
        Logger.e(it::class.simpleName)
        if (it is NoInternetConnectionException) {
            NetworkError(it)
        } else {
            UnknownError(it)
        }
    }
}