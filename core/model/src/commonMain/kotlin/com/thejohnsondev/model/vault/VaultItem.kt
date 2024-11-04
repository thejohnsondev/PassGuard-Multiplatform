package com.thejohnsondev.model.vault

interface VaultItem {
    val id: String?
    val isFavorite: Boolean
    val createdTimeStamp: String
    val modifiedTimeStamp: String
    val categoryId: String
}