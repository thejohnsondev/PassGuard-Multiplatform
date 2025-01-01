package com.thejohnsondev.domain

import com.thejohnsondev.common.empty
import com.thejohnsondev.common.utils.getCurrentTimeStamp
import com.thejohnsondev.model.vault.AdditionalFieldDto

class AddAdditionalFieldUseCaseImpl : AddAdditionalFieldUseCase {
    override fun invoke(currentList: List<AdditionalFieldDto>): List<AdditionalFieldDto> {
        val newList = currentList.toMutableList()
        newList.add(
            AdditionalFieldDto(
                id = getCurrentTimeStamp(),
                title = String.Companion.empty,
                value = String.Companion.empty
            )
        )
        return newList
    }
}