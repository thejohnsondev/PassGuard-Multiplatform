package com.thejohnsondev.network

import arrow.core.Either
import com.thejohnsondev.common.ERROR_CREDENTIAL_TOO_OLD_LOGIN_AGAIN
import com.thejohnsondev.common.ERROR_INVALID_ID_TOKEN
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.HttpError
import com.thejohnsondev.model.InvalidTokenError
import com.thejohnsondev.model.LoginAgainError
import com.thejohnsondev.model.NetworkError
import com.thejohnsondev.model.NoInternetConnectionException
import com.thejohnsondev.model.UnknownError
import com.thejohnsondev.model.auth.firebase.FBErrorBody
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMessageBuilder
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.contentType

suspend inline fun <reified T> callWithMapping(call: (() -> HttpResponse)): Either<Error, T> {
    return Either.catch {
        val response = call.invoke()
        return when (response.status.value) {
            in 200..300 -> {
                Either.Right(response.body<T>())
            }
            400 -> {
                try {
                    when (response.body<FBErrorBody>().error.message) {
                        ERROR_INVALID_ID_TOKEN -> {
                            Either.Left(InvalidTokenError)
                        }
                        ERROR_CREDENTIAL_TOO_OLD_LOGIN_AGAIN -> {
                            Either.Left(LoginAgainError)
                        }
                        else -> {
                            Either.Left(HttpError(response.status.value, response.body<String>()))
                        }
                    }
                } catch (e: Exception) {
                    Either.Left(HttpError(response.status.value, "Cast error"))
                }
            }
            else -> {
                try {
                    Either.Left(response.body<Error>())
                } catch (e: Exception) {
                    Either.Left(HttpError(response.status.value, response.body<String>()))
                }
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

fun URLBuilder.defaultUrlConfig() {
    protocol = URLProtocol.HTTPS
    host = FIREBASE_BASE_URL
}

fun HttpMessageBuilder.defaultRequestConfig(
    contentType: ContentType = ContentType.Application.Json
) {
    contentType(contentType)
}