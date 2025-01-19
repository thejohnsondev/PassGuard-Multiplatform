package com.thejohnsondev.domain

import com.thejohnsondev.common.utils.parseTime
import com.thejohnsondev.model.vault.PasswordDto
import com.thejohnsondev.ui.model.FilterUIModel.Companion.mapToCategory
import com.thejohnsondev.ui.model.PasswordUIModel
import com.thejohnsondev.ui.model.filterlists.FiltersProvider

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
                category = FiltersProvider.Category.getCategoryFilterUiModelById(dto.categoryId).mapToCategory()
            )
        }
    }
}