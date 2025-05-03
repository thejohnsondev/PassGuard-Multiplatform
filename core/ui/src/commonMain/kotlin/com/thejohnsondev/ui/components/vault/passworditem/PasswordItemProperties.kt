package com.thejohnsondev.ui.components.vault.passworditem

data class PasswordItemProperties(
    val showCopyButton: Boolean = true,
    val showFavoriteButton: Boolean = true
) {
    companion object {
        fun default(): PasswordItemProperties = PasswordItemProperties()
    }
}