package com.thejohnsondev.domain.export

import com.thejohnsondev.common.VAULT_ITEM_CATEGORY_PERSONAL
import com.thejohnsondev.common.utils.getCurrentTimeMillis
import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.model.vault.PasswordDto
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

const val DEFAULT_HEADER = "name,url,username,password,note"
const val SAMPLE_ROW = "PassGuard,https://example.com,username,password,"

object CSVImportExportUtils {
    fun getSampleCsvContent(): String {
        return """
        $DEFAULT_HEADER
        $SAMPLE_ROW
    """.trimIndent()
    }
    fun generateCsvContentForPasswords(passwords: List<PasswordDto>): CSVGenerationResult {
        return try {
            val notExportedPasswords = mutableListOf<PasswordDto>()

            var header = DEFAULT_HEADER
            val maxNumberOfAdditionalFields =
                passwords.maxOfOrNull { it.additionalFields.size } ?: 0
            if (maxNumberOfAdditionalFields > 0) {
                val additionalFieldHeaders = (1..maxNumberOfAdditionalFields).map { "note$it" }
                header += "," + additionalFieldHeaders.joinToString(",")
            }

            val rows = passwords.map {
                if (it.domain.isNullOrBlank() || !isValidUrl(it.domain)) {
                    notExportedPasswords.add(it)
                    return@map null
                }
                val sanitizedTitle = it.title.getSanitized()
                val sanitizedDomain =
                    it.domain?.let { domain -> "https://".plus(domain.getSanitized()) }
                val sanitizedUser = it.userName.getSanitized()
                val sanitizedPass = it.password.getSanitized()
                val notes = it.additionalFields.map { field ->
                    field.title.getSanitized() + "|" + field.value.getSanitized()
                }
                val result = "$sanitizedTitle,$sanitizedDomain,$sanitizedUser,$sanitizedPass,${
                    notes.joinToString(",")
                }"
                result
            }.filterNotNull()
            val csvContent = (listOf(header) + rows).joinToString("\n")
            CSVGenerationResult(
                isSuccessful = true,
                notExportedPasswords = notExportedPasswords,
                csvContent = csvContent
            )
        } catch (e: Exception) {
            CSVGenerationResult(
                isSuccessful = false,
                message = e.message,
                notExportedPasswords = passwords,
                csvContent = ""
            )
        }

    }

    private fun isValidUrl(url: String?): Boolean {
        val regex = "^([\\w-]+\\.)+[\\w]{2,}$"
        return url?.matches(regex.toRegex()) == true
    }

    private fun isValidUrlForImport(url: String?): Boolean {
        val regex =
            "^(https?://)?(www\\.)?([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}(/[a-zA-Z0-9-._~:/?#\\[\\]@!$&'()*+,;=]*)?$"
        return url?.matches(regex.toRegex()) == true
    }

    private fun String.getSanitized(): String {
        return this.replace(",", " ")
            .replace("|", " ")
    }

    @OptIn(ExperimentalUuidApi::class)
    fun parseCsvContentToPasswords(
        csvContent: String,
    ): CsvParsingResult {
        val parsingTimestamp = getCurrentTimeMillis()

        if (csvContent.isBlank()) {
            return CsvParsingResult.EmptyContent(parsingTimestamp = parsingTimestamp)
        }

        val lines = csvContent.lines()
        val totalLines = lines.size

        if (lines.isEmpty()) {
            return CsvParsingResult.ValidationError(
                message = "CSV is empty or has no lines.",
                rawContent = csvContent,
                parsingTimestamp = parsingTimestamp
            )
        }

        val headerLine = lines.first()
        val headerParts = headerLine.split(",").map { it.trim().lowercase() }

        val requiredHeadersForParsing = listOf("name", "url", "username", "password", "note")
        val missingHeaders = requiredHeadersForParsing.filter { !headerParts.contains(it) }

        val headerIsValid =
            missingHeaders.isEmpty() && headerParts.size >= requiredHeadersForParsing.size

        if (!headerIsValid) {
            val message = if (missingHeaders.isNotEmpty()) {
                "CSV header is missing required columns: ${missingHeaders.joinToString(", ")}."
            } else {
                "CSV header is invalid. Expected at least ${requiredHeadersForParsing.size} columns."
            }
            return CsvParsingResult.ValidationError(
                message = message,
                rawContent = csvContent,
                details = "Expected headers: ${requiredHeadersForParsing.joinToString(", ")}, Found: ${
                    headerParts.joinToString(
                        ", "
                    )
                }",
                parsingTimestamp = parsingTimestamp
            )
        }

        val dataLines = lines.drop(1)
        val parsedPasswords = mutableListOf<PasswordDto>()
        val failedEntries = mutableListOf<FailedPasswordParsingEntry>()
        var dataLinesProcessed = 0

        dataLines.forEachIndexed { index, line ->
            dataLinesProcessed++
            val lineNumber = index + 2

            val parts = line.split(
                ",",
                limit = 5 + (headerParts.size - requiredHeadersForParsing.size)
            )

            if (parts.size < requiredHeadersForParsing.size) {
                failedEntries.add(
                    FailedPasswordParsingEntry(
                        lineNumber = lineNumber,
                        rawLineContent = "${headerLine}\n$line",
                        reason = "Incorrect number of columns (expected at least ${requiredHeadersForParsing.size}, got ${parts.size})."
                    )
                )
                return@forEachIndexed
            }

            try {
                val name = parts[0].trim()
                val url = parts[1].trim().takeIf { it.isNotEmpty() }
                val userName = parts[2].trim()
                val password = parts[3].trim()

                val additionalFields = mutableListOf<AdditionalFieldDto>()
                val potentialAdditionalFieldParts = parts.drop(4)

                potentialAdditionalFieldParts.forEach { notePart ->
                    if (notePart.isNotBlank()) {
                        val noteSubParts = notePart.split("|", limit = 2)
                        if (noteSubParts.size == 2) {
                            additionalFields.add(
                                AdditionalFieldDto(
                                    id = Uuid.random().toString(),
                                    title = noteSubParts[0].trim().getSanitizedForParsing(),
                                    value = noteSubParts[1].trim().getSanitizedForParsing()
                                )
                            )
                        } else {
                            additionalFields.add(
                                AdditionalFieldDto(
                                    id = Uuid.random().toString(),
                                    title = "Note",
                                    value = notePart.trim().getSanitizedForParsing()
                                )
                            )
                        }
                    }
                }

                if (name.isEmpty() || userName.isEmpty() || password.isEmpty() || url.isNullOrBlank()) {
                    val missingField = when {
                        name.isEmpty() -> "name"
                        url.isNullOrBlank() -> "url"
                        userName.isEmpty() -> "username"
                        else -> "password"
                    }
                    failedEntries.add(
                        FailedPasswordParsingEntry(
                            lineNumber = lineNumber,
                            rawLineContent = "${headerLine}\n$line",
                            errorField = missingField,
                            reason = "Missing required field ($missingField cannot be empty)."
                        )
                    )
                    return@forEachIndexed
                }
                if (!isValidUrlForImport(url)) {
                    failedEntries.add(
                        FailedPasswordParsingEntry(
                            lineNumber = lineNumber,
                            rawLineContent = "${headerLine}\n$line",
                            errorField = url,
                            reason = "Invalid URL format for domain: '$url'."
                        )
                    )
                    return@forEachIndexed
                }

                parsedPasswords.add(
                    PasswordDto(
                        id = Uuid.random().toString(),
                        title = name,
                        domain = url,
                        userName = userName,
                        password = password,
                        categoryId = VAULT_ITEM_CATEGORY_PERSONAL,
                        additionalFields = additionalFields
                    )
                )
            } catch (e: Exception) {
                failedEntries.add(
                    FailedPasswordParsingEntry(
                        lineNumber = lineNumber,
                        rawLineContent = "${headerLine}\n$line",
                        reason = "Error parsing line: ${e.message ?: "Unknown error"}"
                    )
                )
            }
        }

        val summary = CsvParsingSummary(
            totalLinesProcessed = totalLines,
            dataLinesProcessed = dataLinesProcessed,
            successfullyParsedCount = parsedPasswords.size,
            failedToParseCount = failedEntries.size,
            headerValidated = headerIsValid,
            parsingTimestamp = parsingTimestamp
        )

        return CsvParsingResult.Success(
            passwords = parsedPasswords,
            summary = summary,
            failedEntries = failedEntries
        )
    }

    private fun String.getSanitizedForParsing(): String {
        return this.trim()
    }

}


data class CSVGenerationResult(
    val isSuccessful: Boolean,
    val message: String? = null,
    val notExportedPasswords: List<PasswordDto>,
    val csvContent: String,
)

data class FailedPasswordParsingEntry(
    val lineNumber: Int,
    val rawLineContent: String,
    val errorField: String? = null,
    val reason: String?
)

data class CsvParsingSummary(
    val totalLinesProcessed: Int,
    val dataLinesProcessed: Int,
    val successfullyParsedCount: Int,
    val failedToParseCount: Int,
    val headerValidated: Boolean,
    val parsingTimestamp: Long
)

sealed class CsvParsingResult {
    data class Success(
        val passwords: List<PasswordDto>,
        val summary: CsvParsingSummary,
        val failedEntries: List<FailedPasswordParsingEntry>
    ) : CsvParsingResult()

    data class EmptyContent(
        val message: String = "CSV content is empty.",
        val parsingTimestamp: Long
    ) : CsvParsingResult()

    data class ValidationError(
        val message: String,
        val rawContent: String,
        val details: String? = null,
        val parsingTimestamp: Long
    ) : CsvParsingResult()
}