package com.thejohnsondev.uimodel.mappers

import com.thejohnsondev.model.vault.PasswordDto
import com.thejohnsondev.uimodel.filterlists.financeFilterUIModel
import com.thejohnsondev.uimodel.filterlists.othersFilterUIModel
import com.thejohnsondev.uimodel.filterlists.personalFilterUIModel
import com.thejohnsondev.uimodel.filterlists.workFilterUIModel
import com.thejohnsondev.uimodel.models.CategoryUIModel
import com.thejohnsondev.uimodel.models.FilterUIModel
import com.thejohnsondev.uimodel.models.PasswordUIModel

// TODO think of a better way to store mappers
fun FilterUIModel.mapToCategory(): CategoryUIModel {
    return CategoryUIModel(
        id = this.id,
        categoryNameResId = this.nameResId,
        categoryIcon = this.filterIcon,
        contentColorResName = this.contentColorResName
    )
}

fun PasswordDto.mapToUiModel(): PasswordUIModel {
    return PasswordUIModel(
        id = this.id,
        organization = this.organization,
        organizationLogo = this.organizationLogo,
        title = this.title,
        password = this.password,
        additionalFields = this.additionalFields,
        createdTime = this.createdTimeStamp,
        modifiedTime = this.modifiedTimeStamp,
        isFavorite = this.isFavorite,
        category = when (this.categoryId) {
            personalFilterUIModel.id -> personalFilterUIModel.mapToCategory()
            workFilterUIModel.id -> workFilterUIModel.mapToCategory()
            financeFilterUIModel.id -> financeFilterUIModel.mapToCategory()
            othersFilterUIModel.id -> othersFilterUIModel.mapToCategory()
            else -> throw IllegalArgumentException("Invalid category id: $categoryId")
        }
    )
}

fun PasswordUIModel.mapToDto(): PasswordDto {
    return PasswordDto(
        id = this.id ?: throw IllegalArgumentException("Password id cannot be null"),
        organization = this.organization,
        organizationLogo = this.organizationLogo,
        title = this.title,
        password = this.password,
        additionalFields = this.additionalFields,
        createdTimeStamp = this.createdTime,
        modifiedTimeStamp = this.modifiedTime,
        isFavorite = this.isFavorite,
        categoryId = this.category.id
    )
}