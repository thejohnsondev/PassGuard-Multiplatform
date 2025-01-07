package com.thejohnsondev.network

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.dotnet.DotNetAuthRequestBody
import com.thejohnsondev.model.auth.dotnet.DotNetAuthResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.path

class DotNetRemoteApiImpl(
    private val client: HttpClient
) {
    suspend fun signUp(body: DotNetAuthRequestBody): Either<Error, DotNetAuthResponse> {
        return callWithMapping {
            client.post {
                defaultRequestConfig()
                url {
                    path(DOT_NET_SIGN_UP)
                    defaultUrlConfig()
                }
                setBody(body)
            }
        }
    }

    suspend fun signIn(body: DotNetAuthRequestBody): Either<Error, DotNetAuthResponse> {
        return callWithMapping {
            client.post {
                defaultRequestConfig()
                url {
                    path(DOT_NET_LOGIN)
                    defaultUrlConfig()
                }
                setBody(body)
            }
        }
    }

    suspend fun deleteAccount(): Either<Error, Unit> {
        return callWithMapping {
            client.delete {
                defaultRequestConfig()
                url {
                    path(DOT_NET_DELETE_ACCOUNT)
                    defaultUrlConfig()
                }
            }
        }
    }

}