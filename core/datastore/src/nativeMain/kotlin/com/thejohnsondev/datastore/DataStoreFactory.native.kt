package com.thejohnsondev.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.thejohnsondev.common.DATA_STORE_FILE_NAME
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class DataStoreFactory {
    actual fun buildDataStore(): DataStore<Preferences> {
        val directory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        val path = directory.path + "/$DATA_STORE_FILE_NAME"
        return PreferenceDataStoreFactory.createWithPath(produceFile = {
            path.toPath()
        })
    }
}