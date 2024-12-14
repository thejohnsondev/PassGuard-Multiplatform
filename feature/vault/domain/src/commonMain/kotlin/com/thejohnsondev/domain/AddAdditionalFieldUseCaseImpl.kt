package com.thejohnsondev.domain

import com.thejohnsondev.common.empty
import com.thejohnsondev.model.vault.AdditionalFieldDto
import kotlinx.datetime.Clock

class AddAdditionalFieldUseCaseImpl : AddAdditionalFieldUseCase {
    override fun invoke(currentList: List<AdditionalFieldDto>): List<AdditionalFieldDto> {
        val newList = currentList.toMutableList()
        newList.add(
            AdditionalFieldDto(
                id = Clock.System.now().epochSeconds.toString(),
                title = String.Companion.empty,
                value = String.Companion.empty
            )
        )
        return newList
    }
}