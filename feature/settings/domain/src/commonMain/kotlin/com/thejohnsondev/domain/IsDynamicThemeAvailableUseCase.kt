package com.thejohnsondev.domain

interface IsDynamicThemeAvailableUseCase {
    suspend operator fun invoke(): Boolean
}