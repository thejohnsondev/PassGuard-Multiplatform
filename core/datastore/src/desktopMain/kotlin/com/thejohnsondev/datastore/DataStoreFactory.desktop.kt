package com.thejohnsondev.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.thejohnsondev.common.DATA_STORE_FILE_NAME
import okio.Path.Companion.toPath

actual class DataStoreFactory {
    actual fun buildDataStore(): DataStore<Preferences> {
        return PreferenceDataStoreFactory.createWithPath(produceFile = {
            DATA_STORE_FILE_NAME.toPath()
        })
    }
}