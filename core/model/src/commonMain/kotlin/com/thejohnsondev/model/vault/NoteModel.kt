package com.thejohnsondev.model.vault

import com.thejohnsondev.model.vault.CategoryModel.Companion.DEFAULT_CATEGORY_ID

data class NoteModel(
    val title: String,
    val description: String,
    val backgroundColorHex: String, // TODO create a color picker for this
    override val id: String?,
    override val createdTime: String,
    override val modifiedTime: String,
    override val isFavorite: Boolean = false, // Ignore on the server side
    override val categoryId: String = DEFAULT_CATEGORY_ID,
): VaultItem