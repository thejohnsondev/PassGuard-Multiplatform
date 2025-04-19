package com.thejohnsondev.domain

interface CopyTextUseCase {
    operator fun invoke(text: String, isSensitive: Boolean)
}