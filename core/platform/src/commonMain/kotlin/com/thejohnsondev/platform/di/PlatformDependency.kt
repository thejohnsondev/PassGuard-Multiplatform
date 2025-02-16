package com.thejohnsondev.platform.di

import com.thejohnsondev.platform.encryption.EncryptionUtils
import com.thejohnsondev.platform.encryption.KeyGenerator

interface PlatformDependency {
    fun getKeyGenerator(): KeyGenerator
    fun getEncryptionUtils(): EncryptionUtils
}