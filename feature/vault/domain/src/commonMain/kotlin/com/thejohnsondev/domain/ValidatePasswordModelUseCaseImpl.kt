package com.thejohnsondev.domain

class ValidatePasswordModelUseCaseImpl : ValidatePasswordModelUseCase {

    override suspend fun invoke(organization: String, title: String, password: String): Boolean {
        return !(organization.isBlank() || title.isBlank() || password.isBlank())
    }
}