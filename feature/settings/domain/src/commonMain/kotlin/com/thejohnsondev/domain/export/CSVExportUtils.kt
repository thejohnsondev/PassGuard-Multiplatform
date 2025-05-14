package com.thejohnsondev.domain.export

import com.thejohnsondev.model.vault.PasswordDto

fun generateCsvContent(passwords: List<PasswordDto>): String {
    val header = "name,url,username,password"
    val rows = passwords.map {
        val sanitizedTitle = it.title.replace(",", " ")
        val sanitizedUser = it.userName.replace(",", " ")
        val sanitizedPass = it.password.replace(",", " ")
        val domain = extractDomainFromTitle(it.title)
        "$sanitizedTitle,$domain,$sanitizedUser,$sanitizedPass"
    }
    return (listOf(header) + rows).joinToString("\n")
}

fun extractDomainFromTitle(title: String): String {
    val knownDomains = listOf("google.com", "facebook.com", "amazon.com", "twitter.com")
    return knownDomains.firstOrNull { title.contains(it, ignoreCase = true) } ?: ""
}
