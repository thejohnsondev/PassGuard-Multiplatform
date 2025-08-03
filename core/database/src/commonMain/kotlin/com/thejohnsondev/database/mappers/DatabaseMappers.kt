package com.thejohnsondev.database.mappers

import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.model.vault.PasswordDto
import org.thejohnsondev.vault.database.PasswordAdditionalField
import org.thejohnsondev.vault.database.PasswordEntity

fun PasswordAdditionalField.mapToDto(): AdditionalFieldDto {
    return AdditionalFieldDto(
        id = id,
        title = fieldTitle,
        value = fieldValue
    )
}

fun PasswordEntity.mapToDto(
    additionalFieldEntities: List<AdditionalFieldDto>
): PasswordDto {
    return PasswordDto(
        title = title,
        organizationLogo = organizationLogo,
        domain = domain,
        userName = userName,
        password = password,
        additionalFields = additionalFieldEntities,
        id = id,
        createdTimeStamp = createdTimeStamp,
        modifiedTimeStamp = modifiedTimeStamp,
        categoryId = categoryId,
        isFavorite = isFavorite ?: false,
        syncStatus = syncStatus,
        syncedTimeStamp = syncedTimeStamp
    )
}