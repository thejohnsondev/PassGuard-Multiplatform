package com.thejohnsondev.platform.di

import com.thejohnsondev.platform.encryption.DesktopEncryptionUtils
import com.thejohnsondev.platform.encryption.DesktopKeyGenerator
import com.thejohnsondev.platform.encryption.EncryptionUtils
import com.thejohnsondev.platform.encryption.KeyGenerator

class DesktopPlatformDependency: PlatformDependency {
    override fun getKeyGenerator(): KeyGenerator = DesktopKeyGenerator()
    override fun getEncryptionUtils(): EncryptionUtils = DesktopEncryptionUtils()
}