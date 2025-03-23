package com.thejohnsondev.common.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object WelcomeRoute : Routes()

    @Serializable
    data object SelectVaultTypeRoute : Routes()

    @Serializable
    data object LoginRoute : Routes()

    @Serializable
    data object SignUpRoute : Routes()

    @Serializable
    data object BiometricRoute : Routes()

    @Serializable
    data class HomeRoute(
        val isFromLogin: Boolean = false
    ) : Routes()

    @Serializable
    data class VaultRoute(
        val isFromLogin: Boolean = false
    ) : Routes()

    @Serializable
    data object ToolsRoute : Routes()

    @Serializable
    data object SettingsRoute : Routes()

    @Serializable
    data object AskToLoginRoute: Routes()
}