package com.thejohnsondev.platform.di

import com.thejohnsondev.platform.encryption.DesktopEncryptionUtils
import com.thejohnsondev.platform.encryption.DesktopKeyGenerator
import com.thejohnsondev.platform.encryption.EncryptionUtils
import com.thejohnsondev.platform.encryption.KeyGenerator
import com.thejohnsondev.platform.filemanager.DesktopFileManager
import com.thejohnsondev.platform.filemanager.FileManager
import com.thejohnsondev.platform.storage.DesktopSecureStorage
import com.thejohnsondev.platform.storage.SecureStorage
import com.thejohnsondev.platform.utils.ClipboardUtils
import com.thejohnsondev.platform.utils.DesktopClipboardUtils

class DesktopPlatformDependency: PlatformDependency {
    override fun getKeyGenerator(): KeyGenerator = DesktopKeyGenerator()
    override fun getEncryptionUtils(): EncryptionUtils = DesktopEncryptionUtils()
    override fun getSecureStorage(): SecureStorage = DesktopSecureStorage()
    override fun getClipboardUtils(): ClipboardUtils = DesktopClipboardUtils()
    override fun getFileManager(): FileManager = DesktopFileManager()
}