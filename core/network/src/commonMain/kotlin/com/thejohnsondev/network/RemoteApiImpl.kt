package com.thejohnsondev.network

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.AuthRequestBody
import com.thejohnsondev.model.auth.AuthResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path

class RemoteApiImpl(
    private val client: HttpClient
) : RemoteApi {

    override suspend fun signUp(body: AuthRequestBody): Either<Error, AuthResponse> {
        return client.post {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                path(SIGN_UP)
                contentType(ContentType.Application.Json)
            }
            setBody(body)
        }.mapToResponse()
    }

}