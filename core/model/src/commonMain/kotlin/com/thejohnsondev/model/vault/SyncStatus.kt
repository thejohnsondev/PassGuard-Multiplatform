package com.thejohnsondev.model.vault

enum class SyncStatus {
    NEW,
    MODIFIED,
    DELETED,
    SYNCED;

    companion object {
        fun from(value: String): SyncStatus {
            return entries.firstOrNull { it.name == value } ?: throw IllegalArgumentException("No SyncStatus with $value name")
        }
    }
}