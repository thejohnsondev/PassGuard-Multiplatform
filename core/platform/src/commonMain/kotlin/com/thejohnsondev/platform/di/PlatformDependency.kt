package com.thejohnsondev.platform.di

import com.thejohnsondev.platform.encryption.EncryptionUtils
import com.thejohnsondev.platform.encryption.KeyGenerator
import com.thejohnsondev.platform.storage.SecureStorage
import com.thejohnsondev.platform.utils.ClipboardUtils

interface PlatformDependency {
    fun getKeyGenerator(): KeyGenerator
    fun getEncryptionUtils(): EncryptionUtils
    fun getSecureStorage(): SecureStorage
    fun getClipboardUtils(): ClipboardUtils
}