package com.thejohnsondev.platform.di

import com.thejohnsondev.platform.encryption.DesktopEncryptionUtils
import com.thejohnsondev.platform.encryption.DesktopKeyGenerator
import com.thejohnsondev.platform.encryption.EncryptionUtils
import com.thejohnsondev.platform.encryption.KeyGenerator
import com.thejohnsondev.platform.storage.DesktopSecureStorage
import com.thejohnsondev.platform.storage.SecureStorage

class DesktopPlatformDependency: PlatformDependency {
    override fun getKeyGenerator(): KeyGenerator = DesktopKeyGenerator()
    override fun getEncryptionUtils(): EncryptionUtils = DesktopEncryptionUtils()
    override fun getSecureStorage(): SecureStorage = DesktopSecureStorage()
}