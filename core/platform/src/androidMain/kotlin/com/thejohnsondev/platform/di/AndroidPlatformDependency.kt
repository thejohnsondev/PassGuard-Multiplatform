package com.thejohnsondev.platform.di

import com.thejohnsondev.platform.encryption.AndroidEncryptionUtils
import com.thejohnsondev.platform.encryption.AndroidKeyGenerator
import com.thejohnsondev.platform.encryption.EncryptionUtils
import com.thejohnsondev.platform.encryption.KeyGenerator

class AndroidPlatformDependency: PlatformDependency {
    override fun getKeyGenerator(): KeyGenerator = AndroidKeyGenerator()
    override fun getEncryptionUtils(): EncryptionUtils = AndroidEncryptionUtils()
}