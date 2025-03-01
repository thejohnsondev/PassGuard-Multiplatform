package com.thejohnsondev.platform.storage

interface SecureStorage {
    fun save(key: String, value: String)
    fun read(key: String): String?
    fun remove(key: String)
}