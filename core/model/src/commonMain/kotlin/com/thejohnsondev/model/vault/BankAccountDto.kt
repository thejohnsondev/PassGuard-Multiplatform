package com.thejohnsondev.model.vault

data class BankAccountDto(
    val cardHolderName: String,
    val cardNumber: String,
    val expirationDateTimeStamp: String,
    val cvv: String,
    val pin: String,
    override val id: String,
    override val createdTimeStamp: String? = null,
    override val modifiedTimeStamp: String? = null,
    override val syncedTimeStamp: String? = null,
    override val syncStatus: String? = null,
    override val isFavorite: Boolean = false,
    override val categoryId: String,
): VaultItemDto