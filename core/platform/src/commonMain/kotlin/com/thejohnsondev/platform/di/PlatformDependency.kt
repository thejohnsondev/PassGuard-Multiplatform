package com.thejohnsondev.platform.di

import com.thejohnsondev.platform.encryption.EncryptionUtils
import com.thejohnsondev.platform.encryption.KeyGenerator
import com.thejohnsondev.platform.storage.SecureStorage

interface PlatformDependency {
    fun getKeyGenerator(): KeyGenerator
    fun getEncryptionUtils(): EncryptionUtils
    fun getSecureStorage(): SecureStorage
}