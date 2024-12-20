package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.PasswordDto
import com.thejohnsondev.uimodel.filterlists.financeFilterUIModel
import com.thejohnsondev.uimodel.filterlists.othersFilterUIModel
import com.thejohnsondev.uimodel.filterlists.personalFilterUIModel
import com.thejohnsondev.uimodel.filterlists.workFilterUIModel
import com.thejohnsondev.uimodel.mappers.mapToCategory
import com.thejohnsondev.uimodel.models.PasswordUIModel

class PasswordsMapToUiModelsUseCaseImpl : PasswordsMapToUiModelsUseCase {
    override fun invoke(passwordsDto: List<PasswordDto>): List<PasswordUIModel> {
        return passwordsDto.map { dto ->
            PasswordUIModel(
                id = dto.id,
                organization = dto.organization,
                organizationLogo = dto.organizationLogo,
                title = dto.title,
                password = dto.password,
                additionalFields = dto.additionalFields,
                createdTime = dto.createdTimeStamp,
                modifiedTime = dto.modifiedTimeStamp,
                isFavorite = dto.isFavorite,
                category = when (dto.categoryId) {
                    personalFilterUIModel.id -> personalFilterUIModel.mapToCategory()
                    workFilterUIModel.id -> workFilterUIModel.mapToCategory()
                    financeFilterUIModel.id -> financeFilterUIModel.mapToCategory()
                    othersFilterUIModel.id -> othersFilterUIModel.mapToCategory()
                    else -> throw IllegalArgumentException("Invalid category id: ${dto.categoryId}")
                }
            )
        }
    }
}