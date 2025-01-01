package com.thejohnsondev.domain

interface IsBiometricsAvailableUseCase {
    suspend operator fun invoke(): Boolean
}