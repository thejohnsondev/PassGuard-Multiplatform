package com.thejosnsondev.biometric

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

@Serializable
data class AvailabilityResponse(val status: String, val type: String? = null)

@Serializable
data class AuthResultResponse(
    val result: String,
    val errorCode: Int? = null,
    val errorMessage: String? = null
)

actual class BiometricAuthenticator {

    private val helperExecutableName = "PassGuardMacOSBiometricCheck.kexe"
    private val json = Json { ignoreUnknownKeys = true }


    private fun getHelperPath(): String? {
        val osName = System.getProperty("os.name").lowercase()
        if (!osName.contains("mac")) {
            return null
        }

        val helperDevPath = extractHelperExecutable()
        if (File(helperDevPath).exists()) {
            println("Found helper at development path: $helperDevPath")
            return helperDevPath
        }

        val currentAppMacOSDir =
            Paths.get(System.getProperty("java.home")).parent.parent.toString()
        val bundledHelperPath =
            Paths.get(currentAppMacOSDir, "MacOS", helperExecutableName).normalize().toString()

        if (File(bundledHelperPath).exists()) {
            println("Found helper at bundled path: $bundledHelperPath")
            return bundledHelperPath
        }

        println("macOSBiometricHelper executable ($helperExecutableName) not found at expected locations for current OS ($osName).")
        return null
    }


    private fun extractHelperExecutable(): String {
        val resourceName = "PassGuardMacOSBiometricCheck.kexe"

        val resource = Thread.currentThread().contextClassLoader.getResourceAsStream(resourceName)
            ?: error("Resource $resourceName not found")
        val tempDir = Files.createTempDirectory("biometric-helper").toFile()
        tempDir.deleteOnExit()
        val tempFile = File(tempDir, resourceName)
        tempFile.deleteOnExit()

        resource.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        tempFile.setExecutable(true)

        return tempFile.absolutePath
    }

    actual fun getBiometricAvailability(): BiometricAvailability {
        val helperPath = getHelperPath() ?: return BiometricAvailability.Unavailable

        return try {
            val process = ProcessBuilder(helperPath, "check-availability").start()
            val output = process.inputStream.bufferedReader().readText()
            process.waitFor()

            if (process.exitValue() != 0) {
                val errorOutput = process.errorStream.bufferedReader().readText()
                println("Helper exited with error code ${process.exitValue()}. Error: $errorOutput")
                return BiometricAvailability.Unavailable
            }

            val response = json.decodeFromString<AvailabilityResponse>(output)
            when (response.status) {
                "AVAILABLE" -> BiometricAvailability.Available(
                    BiometricType.valueOf(
                        response.type ?: "NONE"
                    )
                )

                "UNAVAILABLE" -> BiometricAvailability.Unavailable
                "HARDWARE_UNAVAILABLE" -> BiometricAvailability.Unavailable
                "FEATURE_NOT_SUPPORTED" -> BiometricAvailability.Unavailable
                else -> BiometricAvailability.Unavailable
            }
        } catch (e: IOException) {
            println("Error running biometric helper: ${e.message}")
            BiometricAvailability.Unavailable
        } catch (e: Exception) {
            println("Error parsing biometric helper output: ${e.message}.")
            BiometricAvailability.Unavailable
        }
    }

    actual suspend fun authenticate(
        promptTitle: String,
        promptSubtitle: String?,
        promptDescription: String?
    ): BiometricAuthResult = withContext(Dispatchers.IO) {
        val helperPath = getHelperPath()
            ?: return@withContext BiometricAuthResult.Error(-1, "macOS biometric helper not found.")

        val commandArgs = mutableListOf(helperPath, "authenticate", promptTitle)
        promptSubtitle?.let { commandArgs.add(it) }
        promptDescription?.let { commandArgs.add(it) }

        return@withContext try {
            val process = ProcessBuilder(commandArgs).start()
            val output = process.inputStream.bufferedReader().readText()
            process.waitFor()

            if (process.exitValue() != 0) {
                val errorOutput = process.errorStream.bufferedReader().readText()
                println("Helper exited with error code ${process.exitValue()}. Error: $errorOutput")
                return@withContext BiometricAuthResult.Error(-1, "Biometric helper process failed.")
            }

            val response = json.decodeFromString<AuthResultResponse>(output)
            when (response.result) {
                "SUCCESS" -> BiometricAuthResult.Success
                "USER_CANCELLED" -> BiometricAuthResult.Failed
                "FAILED" -> BiometricAuthResult.Failed
                "LOCKED_OUT" -> BiometricAuthResult.Failed
                "NOT_AVAILABLE" -> BiometricAuthResult.Failed
                "ERROR" -> BiometricAuthResult.Error(
                    response.errorCode ?: -1,
                    response.errorMessage ?: "Unknown error from helper"
                )

                else -> BiometricAuthResult.Error(
                    -1,
                    "Unexpected response from helper: ${response.result}"
                )
            }
        } catch (e: IOException) {
            println("Error running biometric helper: ${e.message}")
            BiometricAuthResult.Error(
                -1,
                "Failed to launch or communicate with biometric helper: ${e.message}"
            )
        } catch (e: Exception) {
            println("Error parsing biometric helper output: ${e.message}. Output:'")
            BiometricAuthResult.Error(-1, "Failed to parse biometric helper output: ${e.message}")
        }
    }
}