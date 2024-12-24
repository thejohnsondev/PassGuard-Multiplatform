package com.thejohnsondev.domain

import com.thejohnsondev.common.utils.parseTime
import com.thejohnsondev.model.vault.PasswordDto
import com.thejohnsondev.ui.model.PasswordUIModel
import com.thejohnsondev.ui.model.filterlists.financeFilterUIModel
import com.thejohnsondev.ui.model.filterlists.othersFilterUIModel
import com.thejohnsondev.ui.model.filterlists.personalFilterUIModel
import com.thejohnsondev.ui.model.filterlists.workFilterUIModel
import com.thejohnsondev.ui.model.mappers.mapToCategory

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
                createdTime = dto.createdTimeStamp.parseTime(),
                modifiedTime = dto.modifiedTimeStamp.parseTime(),
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