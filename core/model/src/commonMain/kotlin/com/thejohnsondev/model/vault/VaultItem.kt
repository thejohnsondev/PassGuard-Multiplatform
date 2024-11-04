package com.thejohnsondev.model.vault

interface VaultItem {
    val id: String?
    val isFavorite: Boolean
    val createdTime: String
    val modifiedTime: String
    val categoryId: String
}