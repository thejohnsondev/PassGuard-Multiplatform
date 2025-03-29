package com.thejohnsondev.domain

interface ExtractCompanyNameUseCase {
    operator fun invoke(rawTitle: String): String?
}