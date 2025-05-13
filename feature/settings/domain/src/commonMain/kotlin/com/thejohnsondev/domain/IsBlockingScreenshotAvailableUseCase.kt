package com.thejohnsondev.domain

interface IsBlockingScreenshotAvailableUseCase {
    suspend operator fun invoke(): Boolean
}