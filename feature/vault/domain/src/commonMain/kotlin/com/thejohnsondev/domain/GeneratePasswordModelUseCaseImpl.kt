package com.thejohnsondev.domain

import com.thejohnsondev.common.utils.getCurrentTimeStamp
import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.model.vault.PasswordDto
import com.thejohnsondev.model.vault.SyncStatus
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class GeneratePasswordModelUseCaseImpl : GeneratePasswordModelUseCase {
    @OptIn(ExperimentalUuidApi::class)
    override fun invoke(
        passwordId: String?,
        title: String,
        organizationLogoUrl: String,
        domain: String,
        userName: String,
        password: String,
        categoryId: String,
        additionalFields: List<AdditionalFieldDto>,
        createdTime: String?,
        isFavorite: Boolean,
        syncStatus: SyncStatus
    ): PasswordDto {
        val finalPasswordId = passwordId ?: Uuid.random().toString()
        val nowTime = getCurrentTimeStamp()
        val finalCreatedTime = createdTime ?: nowTime
        val modifiedTime = if (createdTime == null) {
            null
        } else {
            nowTime
        }
        val filteredAdditionalFields = additionalFields.filter { it.title.isNotBlank() && it.value.isNotBlank() }
        return PasswordDto(
            id = finalPasswordId,
            title = title,
            organizationLogo = organizationLogoUrl,
            domain = domain,
            userName = userName,
            password = password,
            categoryId = categoryId,
            additionalFields = filteredAdditionalFields,
            createdTimeStamp = finalCreatedTime,
            modifiedTimeStamp = modifiedTime,
            isFavorite = isFavorite,
            syncStatus = syncStatus.name,
        )
    }
}