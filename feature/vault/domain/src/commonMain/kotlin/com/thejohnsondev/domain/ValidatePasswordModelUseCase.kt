package com.thejohnsondev.domain

interface ValidatePasswordModelUseCase {
    suspend operator fun invoke(organization: String, title: String, password: String): Boolean
}