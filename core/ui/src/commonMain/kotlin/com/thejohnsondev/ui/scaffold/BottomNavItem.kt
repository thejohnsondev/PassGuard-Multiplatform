package com.thejohnsondev.ui.scaffold

import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.common.navigation.Routes.SettingsRoute
import com.thejohnsondev.common.navigation.Routes.ToolsRoute
import com.thejohnsondev.common.navigation.Routes.VaultRoute
import com.thejohnsondev.ui.utils.ResString
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
    val route: Routes,
    val titleRes: StringResource,
    val imgResId: DrawableResource,
    val index: Int,
) {
    data object Vault : BottomNavItem(
        route = VaultRoute,
        titleRes = ResString.vault,
        imgResId = Res.drawable.ic_vault_108_gradient,
        index = 0
    )

    data object Tools : BottomNavItem(
        route = ToolsRoute,
        titleRes = ResString.tools,
        imgResId = Res.drawable.ic_tools,
        index = 1
    )

    data object Settings : BottomNavItem(
        route = SettingsRoute,
        titleRes = ResString.settings,
        imgResId = Res.drawable.ic_settings,
        index = 2
    )
}
