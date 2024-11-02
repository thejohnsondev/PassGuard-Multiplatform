package com.thejohnsondev.ui.scaffold

import com.thejohnsondev.common.navigation.Screens
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import vaultmultiplatform.core.ui.generated.resources.Res
import vaultmultiplatform.core.ui.generated.resources.ic_settings
import vaultmultiplatform.core.ui.generated.resources.ic_tools
import vaultmultiplatform.core.ui.generated.resources.ic_vault_108_gradient
import vaultmultiplatform.core.ui.generated.resources.settings
import vaultmultiplatform.core.ui.generated.resources.tools
import vaultmultiplatform.core.ui.generated.resources.vault

sealed class BottomNavItem(
    val route: String,
    val titleRes: StringResource,
    val imgResId: DrawableResource,
    val index: Int
) {
    data object Vault : BottomNavItem(
        route = Screens.VaultScreen.name,
        titleRes = Res.string.vault,
        imgResId = Res.drawable.ic_vault_108_gradient,
        index = 0
    )

    data object Notes : BottomNavItem(
        route = Screens.NotesScreen.name,
        titleRes = Res.string.tools,
        imgResId = Res.drawable.ic_tools,
        index = 1
    )

    data object Settings : BottomNavItem(
        route = Screens.Settings.name,
        titleRes = Res.string.settings,
        imgResId = Res.drawable.ic_settings,
        index = 2
    )
}
