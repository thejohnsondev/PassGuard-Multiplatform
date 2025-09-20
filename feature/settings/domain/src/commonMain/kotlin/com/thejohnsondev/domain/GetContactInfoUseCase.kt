package com.thejohnsondev.domain

import com.thejohnsondev.common.utils.BuildKonfigProvider
import com.thejohnsondev.domain.model.ContactInfo

class GetContactInfoUseCase {
    operator fun invoke(): ContactInfo {
        val developerEmail = BuildKonfigProvider.getContactEmail()
        return ContactInfo(
            developerEmail = developerEmail
        )
    }
}