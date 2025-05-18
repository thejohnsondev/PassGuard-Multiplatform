package com.thejohnsondev.domain

import com.thejohnsondev.common.utils.getTimeDifferenceInMillis
import com.thejohnsondev.common.utils.parseTime
import com.thejohnsondev.model.vault.PasswordDto
import com.thejohnsondev.ui.model.FilterUIModel.Companion.mapToCategory
import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel
import com.thejohnsondev.ui.model.filterlists.FiltersProvider

class PasswordsMapToUiModelsUseCaseImpl : PasswordsMapToUiModelsUseCase {

    companion object {
        const val PASSWORD_UPDATED_TIME_THRESHOLD = 2
    }

    override fun invoke(passwordsDto: List<PasswordDto>): List<PasswordUIModel> {
        return passwordsDto.map { dto ->
            val wasJustModified = getWasJustModified(dto)
            PasswordUIModel(
                id = dto.id,
                title = dto.title,
                organizationLogo = dto.organizationLogo,
                domain = dto.domain,
                userName = dto.userName,
                password = dto.password,
                additionalFields = dto.additionalFields,
                createdTime = dto.createdTimeStamp.parseTime(),
                modifiedTime = dto.modifiedTimeStamp.parseTime(),
                isFavorite = dto.isFavorite,
                category = FiltersProvider.Category.getCategoryFilterUiModelById(dto.categoryId)
                    .mapToCategory(),
                showUpdateAnimation = wasJustModified
            )
        }
    }

    private fun getWasJustModified(passwordDto: PasswordDto): Boolean {
        return (passwordDto.modifiedTimeStamp ?: passwordDto.createdTimeStamp)
            ?.getTimeDifferenceInMillis().takeIf {
                it != null && it <= PASSWORD_UPDATED_TIME_THRESHOLD
            } != null
    }
}