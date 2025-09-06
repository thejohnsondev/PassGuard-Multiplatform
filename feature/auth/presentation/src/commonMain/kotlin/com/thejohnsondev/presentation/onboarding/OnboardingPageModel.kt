package com.thejohnsondev.presentation.onboarding

import com.thejohnsondev.ui.utils.ResDrawable
import com.thejohnsondev.ui.utils.ResString
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import vaultmultiplatform.core.ui.generated.resources.img_phone_vault
import vaultmultiplatform.core.ui.generated.resources.img_security_shield
import vaultmultiplatform.core.ui.generated.resources.img_sync
import vaultmultiplatform.core.ui.generated.resources.onboarding_export_description
import vaultmultiplatform.core.ui.generated.resources.onboarding_export_title
import vaultmultiplatform.core.ui.generated.resources.onboarding_offline_description
import vaultmultiplatform.core.ui.generated.resources.onboarding_offline_title
import vaultmultiplatform.core.ui.generated.resources.onboarding_security_description
import vaultmultiplatform.core.ui.generated.resources.onboarding_security_title

data class OnboardingPageModel(
    val pageNumber: Int,
    val titleStringRes: StringResource,
    val descriptionStringRes: StringResource,
    val imageRes: DrawableResource?,
) {

    companion object {
        val pages = listOf(
            OnboardingPageModel(
                pageNumber = 0,
                titleStringRes = ResString.onboarding_security_title,
                descriptionStringRes = ResString.onboarding_security_description,
                imageRes = ResDrawable.img_security_shield
            ),
            OnboardingPageModel(
                pageNumber = 1,
                titleStringRes = ResString.onboarding_offline_title,
                descriptionStringRes = ResString.onboarding_offline_description,
                imageRes = ResDrawable.img_phone_vault
            ),
            OnboardingPageModel(
                pageNumber = 2,
                titleStringRes = ResString.onboarding_export_title,
                descriptionStringRes = ResString.onboarding_export_description,
                imageRes = ResDrawable.img_sync
            )
        )
    }

}