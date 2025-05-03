package com.thejohnsondev.ui.components.vault.passworditem

data class PasswordItemProperties(
    val showCopyButton: Boolean = true,
    val showFavoriteButton: Boolean = true,
    val showEditButton: Boolean = true,
    val showDeleteButton: Boolean = true,
) {
    companion object {
        fun default(): PasswordItemProperties = PasswordItemProperties()
    }
}