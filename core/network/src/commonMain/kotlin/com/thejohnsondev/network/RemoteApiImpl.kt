package com.thejohnsondev.network

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.AuthRequestBody
import com.thejohnsondev.model.auth.AuthResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.path

class RemoteApiImpl(
    private val client: HttpClient
) : RemoteApi {

    override suspend fun signUp(body: AuthRequestBody): Either<Error, AuthResponse> {
        return callWithMapping {
            client.post {
                defaultRequestConfig()
                url {
                    path(SIGN_UP)
                    defaultUrlConfig()
                }
                setBody(body)
            }
        }
    }

    override suspend fun signIn(body: AuthRequestBody): Either<Error, AuthResponse> {
        return callWithMapping {
            client.post {
                defaultRequestConfig()
                url {
                    path(LOGIN)
                    defaultUrlConfig()
                }
                setBody(body)
            }
        }
    }

}