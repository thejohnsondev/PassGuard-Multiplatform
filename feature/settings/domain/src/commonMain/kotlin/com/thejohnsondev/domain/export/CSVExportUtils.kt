package com.thejohnsondev.domain.export

import com.thejohnsondev.model.vault.PasswordDto

object CSVExportUtils {
    fun generateCsvContentForPasswords(passwords: List<PasswordDto>): CSVGenerationResult {
        return try {
            val notExportedPasswords = mutableListOf<PasswordDto>()

            var header = "name,url,username,password,note"
            val maxNumberOfAdditionalFields = passwords.maxOfOrNull { it.additionalFields.size } ?: 0
            if (maxNumberOfAdditionalFields > 0) {
                val additionalFieldHeaders = (1..maxNumberOfAdditionalFields).map { "note$it" }
                header += "," + additionalFieldHeaders.joinToString(",")
            }

            val rows = passwords.map {
                if (it.domain.isNullOrBlank()) {
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

    private fun String.getSanitized(): String {
        return this.replace(",", " ")
            .replace("|", " ")
    }
}

data class CSVGenerationResult(
    val isSuccessful: Boolean,
    val message: String? = null,
    val notExportedPasswords: List<PasswordDto>,
    val csvContent: String,
)