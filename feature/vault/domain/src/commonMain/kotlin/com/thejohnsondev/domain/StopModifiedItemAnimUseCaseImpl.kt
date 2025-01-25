package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.PasswordUIModel
import kotlinx.coroutines.delay

class StopModifiedItemAnimUseCaseImpl : StopModifiedItemAnimUseCase {

    companion object {
        private const val MODIFIED_ITEM_ANIMATION_DURATION = 1500L
    }

    override suspend operator fun invoke(
        passwordsList: List<List<PasswordUIModel>>,
    ): List<PasswordUIModel> {
        val combinedPasswordsList = passwordsList.flatten()
        val animatedItemPos = combinedPasswordsList
            .indexOfFirst { it.showUpdateAnimation }
        if (animatedItemPos == -1) return combinedPasswordsList

        delay(MODIFIED_ITEM_ANIMATION_DURATION)
        return combinedPasswordsList
            .mapIndexed { index, item ->
                if (index == animatedItemPos) {
                    item.copy(showUpdateAnimation = false)
                } else {
                    item
                }
            }
    }
}