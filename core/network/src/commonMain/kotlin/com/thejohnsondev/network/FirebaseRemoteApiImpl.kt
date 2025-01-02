package com.thejohnsondev.network

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.firebase.FBAuthDeleteAccountBody
import com.thejohnsondev.model.auth.firebase.FBAuthRequestBody
import com.thejohnsondev.model.auth.firebase.FBAuthSignInResponse
import com.thejohnsondev.model.auth.firebase.FBAuthSignUpResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.path

class FirebaseRemoteApiImpl(
    private val client: HttpClient
) : RemoteApi {
    override suspend fun signUp(
        body: FBAuthRequestBody,
        apiKey: String
    ): Either<Error, FBAuthSignUpResponse> {
        return callWithMapping {
            client.post {
                defaultRequestConfig()
                url {
                    defaultUrlConfig()
                    path(FIREBASE_SIGN_UP)
                    parameters.append(QUERY_KEY, apiKey)
                }
                setBody(body)
            }
        }
    }

    override suspend fun signIn(
        body: FBAuthRequestBody,
        apiKey: String
    ): Either<Error, FBAuthSignInResponse> {
        return callWithMapping {
            client.post {
                defaultRequestConfig()
                url {
                    defaultUrlConfig()
                    path(FIREBASE_LOGIN)
                    parameters.append(QUERY_KEY, apiKey)
                }
                setBody(body)
            }
        }
    }

    override suspend fun deleteAccount(body: FBAuthDeleteAccountBody, apiKey: String): Either<Error, Unit> {
        return callWithMapping {
            client.post {
                defaultRequestConfig()
                url {
                    defaultUrlConfig()
                    path(FIREBASE_DELETE_ACCOUNT)
                    parameters.append(QUERY_KEY, apiKey)
                }
                setBody(body)
            }
        }
    }
}