package com.thejohnsondev.model.vault

import com.thejohnsondev.model.vault.CategoryModel.Companion.DEFAULT_CATEGORY_ID

data class BankAccountDto(
    val cardHolderName: String,
    val cardNumber: String,
    val expirationDateTimeStamp: String,
    val cvv: String,
    val pin: String,
    override val id: String?,
    override val createdTimeStamp: String? = null,
    override val modifiedTimeStamp: String? = null,
    override val isFavorite: Boolean = false,
    override val categoryId: String = DEFAULT_CATEGORY_ID,
): VaultItemDto