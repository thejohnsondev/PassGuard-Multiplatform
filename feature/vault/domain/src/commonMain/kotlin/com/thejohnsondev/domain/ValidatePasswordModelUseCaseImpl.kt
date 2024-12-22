package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.AdditionalFieldDto

class ValidatePasswordModelUseCaseImpl : ValidatePasswordModelUseCase {

    override suspend fun invoke(
        organization: String, title: String, password: String,
        additionalFieldsList: List<AdditionalFieldDto>
    ): Boolean {
        return organization.isNotBlank()
                && title.isNotBlank()
                && password.isNotBlank()
                && additionalFieldsList.all {
            it.title.isNotBlank() && it.value.isNotBlank()
        }

    }
}