package com.thejohnsondev.model.vault

data class CategoryModel(
    val id: String? = null,
    val name: String,
    val iconId: Int, // todo create a library of icons
    val colorHex: String
) {
    companion object {
        const val DEFAULT_CATEGORY_ID = "personal"
    }
}