package com.thejohnsondev.machelper

import com.thejosnsondev.biometric.BiometricAuthResult
import com.thejosnsondev.biometric.BiometricAvailability
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class AvailabilityResponse(val status: String, val type: String? = null)

@Serializable
data class AuthResultResponse(
    val result: String,
    val errorCode: Long? = null,
    val errorMessage: String? = null
)

val BiometricAvailability.statusName: String
    get() = when (this) {
        is BiometricAvailability.Available -> "AVAILABLE"
        BiometricAvailability.Unavailable -> "UNAVAILABLE"
    }

fun main(args: Array<String>) = runBlocking {
    if (args.isEmpty()) {
        println(
            Json.encodeToString(
                AuthResultResponse(
                    "ERROR",
                    -1,
                    "No command provided to helper."
                )
            )
        )
        return@runBlocking
    }

    val biometricService = MacOSBiometricService()
    val command = args[0]

    when (command) {
        "check-availability" -> {
            val availability = biometricService.getBiometricAvailability()
            val response = when (availability) {
                is BiometricAvailability.Available -> AvailabilityResponse(
                    availability.statusName,
                    availability.type.name
                )

                else -> AvailabilityResponse(availability.statusName)
            }
            println(Json.encodeToString(response))
        }

        "authenticate" -> {
            if (args.size < 2) {
                println(
                    Json.encodeToString(
                        AuthResultResponse(
                            "ERROR",
                            -2,
                            "Authenticate command requires prompt title."
                        )
                    )
                )
                return@runBlocking
            }
            val title = args[1]
            val subtitle = args.getOrNull(2)
            val description = args.getOrNull(3)

            val authResult = biometricService.authenticate(title, subtitle, description)
            val response = when (authResult) {
                BiometricAuthResult.Success -> AuthResultResponse("SUCCESS")
                BiometricAuthResult.Failed -> AuthResultResponse("FAILED")
                is BiometricAuthResult.Error -> AuthResultResponse(
                    "ERROR",
                    authResult.code.toLong(),
                    authResult.message
                )
            }
            println(Json.encodeToString(response))
        }

        else -> {
            println(
                Json.encodeToString(
                    AuthResultResponse(
                        "ERROR",
                        -3,
                        "Unknown command: $command"
                    )
                )
            )
        }
    }
}