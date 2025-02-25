package com.thejohnsondev.platform.di

import android.content.Context
import com.thejohnsondev.platform.encryption.AndroidEncryptionUtils
import com.thejohnsondev.platform.encryption.AndroidKeyGenerator
import com.thejohnsondev.platform.encryption.EncryptionUtils
import com.thejohnsondev.platform.encryption.KeyGenerator
import com.thejohnsondev.platform.storage.AndroidSecureStorage
import com.thejohnsondev.platform.storage.SecureStorage

class AndroidPlatformDependency(private val context: Context): PlatformDependency {
    override fun getKeyGenerator(): KeyGenerator = AndroidKeyGenerator()
    override fun getEncryptionUtils(): EncryptionUtils = AndroidEncryptionUtils()
    override fun getSecureStorage(): SecureStorage = AndroidSecureStorage(context)
}