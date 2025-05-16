package com.thejohnsondev.domain.export

import com.thejohnsondev.model.vault.PasswordDto

object CSVExportUtils {
    fun generateCsvContentForPasswords(passwords: List<PasswordDto>): String {
        val header = "name,url,username,password"
        val rows = passwords.map {
            val sanitizedTitle = it.title.replace(",", " ")
            val sanitizedUser = it.userName.replace(",", " ")
            val sanitizedPass = it.password.replace(",", " ")
            "$sanitizedTitle,,$sanitizedUser,$sanitizedPass"
        }
        return (listOf(header) + rows).joinToString("\n")
    }
}