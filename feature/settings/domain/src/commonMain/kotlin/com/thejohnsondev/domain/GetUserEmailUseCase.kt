package com.thejohnsondev.domain

interface GetUserEmailUseCase {
    suspend operator fun invoke(): String
}