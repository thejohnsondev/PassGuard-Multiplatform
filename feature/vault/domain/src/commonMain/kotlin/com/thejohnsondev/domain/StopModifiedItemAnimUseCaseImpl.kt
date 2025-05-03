package com.thejohnsondev.domain

import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel
import kotlinx.coroutines.delay

class StopModifiedItemAnimUseCaseImpl : StopModifiedItemAnimUseCase {

    companion object {
        private const val MODIFIED_ITEM_ANIMATION_DURATION = 1500L
    }

    override suspend fun invoke(passwordsList: List<PasswordUIModel>): List<PasswordUIModel> {
        val animatedItemPos = passwordsList
            .indexOfFirst { it.showUpdateAnimation }
        if (animatedItemPos == -1) return passwordsList

        delay(MODIFIED_ITEM_ANIMATION_DURATION)
        return passwordsList
            .mapIndexed { index, item ->
                if (index == animatedItemPos) {
                    item.copy(showUpdateAnimation = false)
                } else {
                    item
                }
            }
    }
}