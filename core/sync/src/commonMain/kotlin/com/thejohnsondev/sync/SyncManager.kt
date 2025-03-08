package com.thejohnsondev.sync

import com.thejohnsondev.common.utils.getCurrentTimeStamp
import com.thejohnsondev.database.LocalDataSource
import com.thejohnsondev.network.RemoteApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class SyncManager(
    private val localDataSource: LocalDataSource,
    private val remoteApi: RemoteApi,
    private val appScope: CoroutineScope
) {

    fun syncData() {
        appScope.launch(Dispatchers.IO) {
            syncNewAndModifiedPasswords()
            syncDeletedPasswords()
        }
    }

    private suspend fun syncNewAndModifiedPasswords() {
        val unsynchronizedPasswords = localDataSource.getUnsynchronisedPasswords()
        for (password in unsynchronizedPasswords) {
            val result = if (password.syncStatus == "NEW") {
                remoteApi.createPassword(password)
            } else {
                remoteApi.updatePassword(password)
            }

            if (result.isRight()) {
                val timestamp = getCurrentTimeStamp()
                localDataSource.markAsSynchronised(password.id, timestamp)
            }
        }
    }

    private suspend fun syncDeletedPasswords() {
        val deletedPasswords = localDataSource.getDeletedPasswordsIDs()
        for (passwordId in deletedPasswords) {
            val result = remoteApi.deletePassword(passwordId)

            if (result.isRight()) {
                localDataSource.deleteDeletedPasswordID(passwordId)
            }
        }
    }
}