package com.thejohnsondev.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.thejohnsondev.common.DATA_STORE_FILE_NAME
import okio.Path.Companion.toPath

actual class DataStoreFactory(
    private val context: Context
) {
    actual fun buildDataStore(): DataStore<Preferences> {
        val path = context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
        return PreferenceDataStoreFactory.createWithPath(produceFile = {
            path.toPath()
        })
    }
}