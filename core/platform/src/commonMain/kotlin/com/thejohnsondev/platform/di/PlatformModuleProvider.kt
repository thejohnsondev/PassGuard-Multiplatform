package com.thejohnsondev.platform.di

import com.thejohnsondev.platform.encryption.EncryptionUtils
import com.thejohnsondev.platform.encryption.KeyGenerator
import com.thejohnsondev.platform.filemanager.FileManager
import com.thejohnsondev.platform.storage.SecureStorage
import com.thejohnsondev.platform.utils.ClipboardUtils
import org.koin.dsl.module

data class PlatformModuleProvider(
    val platformDependency: PlatformDependency
) {
    fun generatePlatformModule() = module {
        single<KeyGenerator> { platformDependency.getKeyGenerator() }
        single<EncryptionUtils> { platformDependency.getEncryptionUtils() }
        single<SecureStorage> { platformDependency.getSecureStorage() }
        single<ClipboardUtils> { platformDependency.getClipboardUtils() }
        single<FileManager> { platformDependency.getFileManager() }
    }
}