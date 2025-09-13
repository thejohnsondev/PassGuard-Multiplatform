package com.thejohnsondev.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.thejohnsondev.common.AppType
import com.thejohnsondev.common.DATA_STORE_FILE_NAME
import com.thejohnsondev.common.utils.BuildKonfigProvider
import okio.Path.Companion.toPath
import java.io.File

actual class DataStoreFactory {
    actual fun buildDataStore(): DataStore<Preferences> {
        val os = System.getProperty("os.name").lowercase()
        val filePath = when {
            os.contains("win") -> {
                File(
                    System.getenv("AppData"),
                    "VaultDatabase/preferences"
                ) // "C:\Users<username>\AppData\Roaming\VaultDatabase\db"
            }

            os.contains("nix") || os.contains("nux") || os.contains("aix") -> {
                File(
                    System.getProperty("user.home"), ".VaultDatabase/preferences"
                ) // "/home/<username>/.VaultDatabase"
            }

            os.contains("mac") -> {
                val appType = BuildKonfigProvider.getAppType()
                val folderName = when (appType) {
                    AppType.PROD.name -> "VaultDatabase"
                    else -> "VaultDatabaseDev"
                }
                File(
                    System.getProperty("user.home"),
                    "Library/Application Support/$folderName"
                ) // "/Users/<username>/Library/Application Support/$folderName"
            }

            else -> error("Unsupported operating system")
        }
        val appType = BuildKonfigProvider.getAppType()
        val fileNameSuffix = when (appType) {
            AppType.DEV.name -> "Dev"
            else -> ""
        }
        return PreferenceDataStoreFactory.createWithPath(produceFile = {
            "${filePath.path}/$fileNameSuffix$DATA_STORE_FILE_NAME".toPath()
        })
    }
}