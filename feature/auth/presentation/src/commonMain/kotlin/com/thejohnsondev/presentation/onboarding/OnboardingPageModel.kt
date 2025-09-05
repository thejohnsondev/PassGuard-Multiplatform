package com.thejohnsondev.presentation.onboarding

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class OnboardingPageModel(
    val titleStringRes: StringResource,
    val descriptionStringRes: StringResource,
    val imageRes: DrawableResource,
) {

    companion object {
        val pages = emptyList<OnboardingPageModel>()
    }

}