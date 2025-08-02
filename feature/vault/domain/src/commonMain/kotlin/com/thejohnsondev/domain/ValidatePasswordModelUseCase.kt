package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.AdditionalFieldDto

class ValidatePasswordModelUseCase {

    operator fun invoke(
        title: String, userName: String, password: String,
        additionalFieldsList: List<AdditionalFieldDto>
    ): Boolean {
        return title.isNotBlank()
                && userName.isNotBlank()
                && password.isNotBlank()
                && additionalFieldsList.all {
            it.title.isNotBlank() && it.value.isNotBlank()
        }

    }
}