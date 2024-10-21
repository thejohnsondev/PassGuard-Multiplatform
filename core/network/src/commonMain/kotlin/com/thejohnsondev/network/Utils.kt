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

suspend inline fun <reified T> callWithMapping(call: (() -> HttpResponse)): Either<Error, T> {
    return Either.catch {
        val response = call.invoke()
        return when (response.status.value) {
            in 200..300 -> {
                Either.Right(response.body<T>())
            }

            else -> {
                Either.Left(HttpError(response.status.value, response.body<String>())) // TODO change the String type to a generic response model
            }
        }
    }.mapLeft {
        if (it is NoInternetConnectionException) {
            NetworkError(it)
        } else {
            UnknownError(it)
        }
    }
}