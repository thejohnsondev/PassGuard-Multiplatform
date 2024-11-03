package com.thejohnsondev.network

import com.thejohnsondev.model.NetworkError
import com.thejohnsondev.model.NoInternetConnectionException
import com.thejohnsondev.model.UnknownError
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.mockk.coEvery
import io.mockk.mockk
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import kotlin.test.assertTrue

class NetworkUtilsKtTest {

    @Test
    fun `callWithMapping returns Right on successful response`() = runTest {
        val mockResponse = mockk<HttpResponse> {
            coEvery { status } returns HttpStatusCode.OK
            coEvery { body<String>() } returns "Success"
        }

        val result = callWithMapping<String> { mockResponse }

        assertTrue(result.isRight())
    }

    @Test
    fun `callWithMapping returns Left on error response`() = runTest {
        val mockResponse = mockk<HttpResponse> {
            coEvery { status } returns HttpStatusCode.BadRequest
            coEvery { body<String>() } returns "Error"
        }

        val result = callWithMapping<String> { mockResponse }

        assertTrue(result.isLeft())
    }

    @Test
    fun `callWithMapping returns NetworkError on NoInternetConnectionException`() = runTest {
        val result = callWithMapping<String> { throw NoInternetConnectionException() }

        assertTrue(result.isLeft() && result.swap().orNull() is NetworkError)
    }

    @Test
    fun `callWithMapping returns UnknownError on unknown exception`() = runTest {
        val result = callWithMapping<String> { throw Exception("Unknown error") }

        assertTrue(result.isLeft() && result.swap().orNull() is UnknownError)
    }

}