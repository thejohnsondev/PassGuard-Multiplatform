package com.thejohnsondev.domain

class ExtractCompanyNameUseCase {
    operator fun invoke(rawTitle: String): String? {
        val ignoredWords = CompanySearchIgnoredWords.getList()

        val words = rawTitle
            .split("\\s+".toRegex())
            .map { it.lowercase() }
            .filter { it !in ignoredWords }

        if (words.isEmpty()) return null

        if (words.size == 1) return words.first()

        val properCaseWords = rawTitle.split(" ").filter { it.firstOrNull()?.isUpperCase() == true }
        if (properCaseWords.isNotEmpty()) return properCaseWords.first()

        return words.maxByOrNull { it.length }
    }
}