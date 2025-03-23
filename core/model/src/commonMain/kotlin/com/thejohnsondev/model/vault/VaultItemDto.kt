package com.thejohnsondev.model.vault

interface VaultItemDto {
    val id: String
    val isFavorite: Boolean
    val createdTimeStamp: String?
    val modifiedTimeStamp: String?
    val syncedTimeStamp: String?
    val syncStatus: String?
    val categoryId: String
}