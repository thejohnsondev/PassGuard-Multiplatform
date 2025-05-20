package com.thejohnsondev.ui.components.vault.passworditem

data class PasswordItemProperties(
    val showCopyButton: Boolean = true,
    val showFavoriteButton: Boolean = true,
    val showEditButton: Boolean = true,
    val showDeleteButton: Boolean = true,
    val swapColorsWhenExpanding: Boolean = true,
    val resizeCardWhenExpanded: Boolean = true,
) {
    companion object {
        fun default(): PasswordItemProperties = PasswordItemProperties()
    }
}