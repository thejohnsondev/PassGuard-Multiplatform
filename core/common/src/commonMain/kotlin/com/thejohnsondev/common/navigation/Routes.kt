package com.thejohnsondev.common.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object WelcomeRoute : Routes()

    @Serializable
    data object LoginRoute : Routes()

    @Serializable
    data object SignUpRoute : Routes()

    @Serializable
    data object BiometricRoute : Routes()

    @Serializable
    data object HomeRoute : Routes()

    @Serializable
    data object VaultRoute : Routes()

    @Serializable
    data object ToolsRoute : Routes()

    @Serializable
    data object SettingsRoute : Routes()
}