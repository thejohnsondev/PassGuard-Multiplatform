package com.thejohnsondev.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

expect class DataStoreFactory {
    fun buildDataStore(): DataStore<Preferences>
}