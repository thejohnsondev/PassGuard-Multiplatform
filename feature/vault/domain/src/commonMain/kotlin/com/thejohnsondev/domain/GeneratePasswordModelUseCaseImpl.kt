package com.thejohnsondev.domain

import com.thejohnsondev.common.utils.getCurrentTimeStamp
import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.model.vault.PasswordDto
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class GeneratePasswordModelUseCaseImpl : GeneratePasswordModelUseCase {
    @OptIn(ExperimentalUuidApi::class)
    override fun invoke(
        passwordId: String?,
        organization: String,
        title: String,
        password: String,
        categoryId: String,
        additionalFields: List<AdditionalFieldDto>,
        createdTime: String?,
        isFavorite: Boolean
    ): PasswordDto {
        val finalPasswordId = passwordId ?: Uuid.random().toString()
        val nowTime = getCurrentTimeStamp()
        val finalCreatedTime = createdTime ?: nowTime
        val modifiedTime = if (createdTime == null) {
            null
        } else {
            nowTime
        }
        return PasswordDto(
            id = finalPasswordId,
            organization = organization,
            title = title,
            password = password,
            categoryId = categoryId,
            additionalFields = additionalFields,
            createdTimeStamp = finalCreatedTime,
            modifiedTimeStamp = modifiedTime,
            isFavorite = isFavorite,
        )
    }
}