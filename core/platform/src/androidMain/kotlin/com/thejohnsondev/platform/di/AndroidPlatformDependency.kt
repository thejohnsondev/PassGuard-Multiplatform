package com.thejohnsondev.platform.di

import android.content.Context
import com.thejohnsondev.platform.encryption.AndroidEncryptionUtils
import com.thejohnsondev.platform.encryption.AndroidKeyGenerator
import com.thejohnsondev.platform.encryption.EncryptionUtils
import com.thejohnsondev.platform.encryption.KeyGenerator
import com.thejohnsondev.platform.filemanager.AndroidPlatformFileManager
import com.thejohnsondev.platform.filemanager.PlatformFileManager
import com.thejohnsondev.platform.storage.AndroidSecureStorage
import com.thejohnsondev.platform.storage.SecureStorage
import com.thejohnsondev.platform.utils.AndroidClipboardUtils
import com.thejohnsondev.platform.utils.ClipboardUtils

class AndroidPlatformDependency(private val context: Context): PlatformDependency {
    override fun getKeyGenerator(): KeyGenerator = AndroidKeyGenerator()
    override fun getEncryptionUtils(): EncryptionUtils = AndroidEncryptionUtils()
    override fun getSecureStorage(): SecureStorage = AndroidSecureStorage(context)
    override fun getClipboardUtils(): ClipboardUtils = AndroidClipboardUtils(context)
    override fun getFileManager(): PlatformFileManager = AndroidPlatformFileManager(context)
}