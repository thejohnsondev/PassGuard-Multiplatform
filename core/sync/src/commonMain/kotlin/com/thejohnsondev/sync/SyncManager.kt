package com.thejohnsondev.sync

import com.thejohnsondev.common.utils.Logger
import com.thejohnsondev.common.utils.getCurrentTimeStamp
import com.thejohnsondev.database.LocalDataSource
import com.thejohnsondev.datastore.PreferencesDataStore
import com.thejohnsondev.model.vault.PasswordDto
import com.thejohnsondev.network.RemoteApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class SyncManager(
    private val localDataSource: LocalDataSource,
    private val preferencesDataStore: PreferencesDataStore,
    private val remoteApi: RemoteApi,
    private val appScope: CoroutineScope,
) {

    /** Called when the user logs in */
    fun syncOnLogin() {
        Logger.d("Syncing on login")
        appScope.launch(Dispatchers.IO) {
            if (inSkipSync()) {
                return@launch
            }
            syncRemoteToLocal(forceReplace = true)
        }
    }

    /** Called when the app launches */
    fun syncOnAppLaunch() {
        Logger.d("Syncing on app launch")
        appScope.launch(Dispatchers.IO) {
            if (inSkipSync()) {
                return@launch
            }
            syncRemoteToLocal(forceReplace = false)
            syncLocalToRemote()
        }
    }

    /** Called after creating/modifying/deleting a password */
    fun syncLocalToRemote() {
        appScope.launch(Dispatchers.IO) {
            if (inSkipSync()) {
                return@launch
            }
            syncNewAndModifiedPasswords()
            syncDeletedPasswords()
        }
    }

    private suspend fun inSkipSync(): Boolean {
        return preferencesDataStore.isVaultLocal()
    }

    /** Fetches all remote passwords and updates local storage */
    private suspend fun syncRemoteToLocal(forceReplace: Boolean) {
        remoteApi.getPasswords().fold(
            ifLeft = { result ->
                throw IllegalStateException("Sync failed: $result")
            },
            ifRight = { remotePasswords ->
                val localPasswords = localDataSource.getAllPasswords()

                if (forceReplace) {
                    // User just logged in, replace all local passwords
                    localDataSource.updatePasswords(remotePasswords)
                } else {
                    // App launch: Merge local and remote data
                    mergePasswords(remotePasswords, localPasswords)
                }
            }
        )
    }

    /** Merges local and remote passwords based on timestamps */
    private suspend fun mergePasswords(
        remotePasswords: List<PasswordDto>,
        localPasswords: Flow<List<PasswordDto>>,
    ) {
        val localPasswordsList = localPasswords.firstOrNull() ?: emptyList()

        for (remotePassword in remotePasswords) {
            val localPassword = localPasswordsList.find { it.id == remotePassword.id }

            if (localPassword == null) {
                // New password from another device -> Add to local database
                localDataSource.createOrUpdatePassword(remotePassword)
            } else if (isRemoteNewer(
                    remotePassword.modifiedTimeStamp,
                    localPassword.modifiedTimeStamp
                )
            ) {
                // Remote version is newer -> Update local database
                localDataSource.createOrUpdatePassword(remotePassword)
            }
        }
    }

    /** Pushes new or modified passwords to the backend */
    private suspend fun syncNewAndModifiedPasswords() {
        val unsynchronizedPasswords = localDataSource.getUnsynchronisedPasswords()
        for (password in unsynchronizedPasswords) {
            try {
                val result = if (password.syncStatus == "NEW") {
                    remoteApi.createPassword(password)
                } else {
                    remoteApi.updatePassword(password)
                }

                if (result.isRight()) {
                    val timestamp = getCurrentTimeStamp()
                    localDataSource.markAsSynchronised(password.id, timestamp)
                } else {
                    Logger.e("Failed to sync password: ${password.id}")
                }
            } catch (e: Exception) {
                Logger.e("Failed to sync password: ${password.id}")
            }
        }
    }

    /** Pushes locally deleted passwords to the backend */
    private suspend fun syncDeletedPasswords() {
        val deletedPasswords = localDataSource.getDeletedPasswordsIDs()
        for (passwordId in deletedPasswords) {
            val result = remoteApi.deletePassword(passwordId)

            if (result.isRight()) {
                localDataSource.deleteDeletedPasswordID(passwordId)
            }
        }
    }

    private fun isRemoteNewer(remoteTimestamp: String?, localTimestamp: String?): Boolean {
        val remoteTime = remoteTimestamp?.toLongOrNull() ?: 0L
        val localTime = localTimestamp?.toLongOrNull() ?: 0L
        return remoteTime > localTime
    }
}