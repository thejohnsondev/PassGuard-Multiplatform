package com.thejohnsondev.domain

interface GetFirstScreenRouteUseCase {
    suspend operator fun invoke(): String
}