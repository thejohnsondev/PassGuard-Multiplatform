package com.thejohnsondev.presentation.onboarding

import com.thejohnsondev.ui.utils.ResString
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import vaultmultiplatform.core.ui.generated.resources.onboarding_export_title
import vaultmultiplatform.core.ui.generated.resources.onboarding_offline_title
import vaultmultiplatform.core.ui.generated.resources.onboarding_security_description
import vaultmultiplatform.core.ui.generated.resources.onboarding_security_title

data class OnboardingPageModel(
    val titleStringRes: StringResource,
    val descriptionStringRes: StringResource,
    val imageRes: DrawableResource?,
) {

    companion object {
        val pages = listOf(
            OnboardingPageModel(
                titleStringRes = ResString.onboarding_security_title,
                descriptionStringRes = ResString.onboarding_security_description,
                imageRes = null
            ),
            OnboardingPageModel(
                titleStringRes = ResString.onboarding_offline_title,
                descriptionStringRes = ResString.onboarding_security_description,
                imageRes = null
            ),
            OnboardingPageModel(
                titleStringRes = ResString.onboarding_export_title,
                descriptionStringRes = ResString.onboarding_security_description,
                imageRes = null
            )
        )
    }

}