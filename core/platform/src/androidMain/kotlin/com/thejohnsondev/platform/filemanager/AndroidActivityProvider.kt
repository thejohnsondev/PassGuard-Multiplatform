package com.thejohnsondev.platform.filemanager

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import java.lang.ref.WeakReference

// This object provides a way for platform-specific code to access the current Activity.
// It must be updated from your Activity's lifecycle methods.
object AndroidActivityProvider {
    var currentActivity: WeakReference<ComponentActivity>? = null

    // This is a simple workaround. For production, consider using
    // a proper ActivityResultRegistry or dependency injection to pass
    // ActivityResultLauncher directly to your AndroidPlatformFileManager.
    private var filePickerLauncher: ActivityResultLauncher<Array<String>>? = null
    private var pendingFilePickerCallback: ((ImportResult) -> Unit)? = null

    fun registerFilePicker(activity: Activity) {
        if (activity !is ComponentActivity) {
            throw IllegalArgumentException("Activity must be a ComponentActivity to register for file picking.")
        }
        currentActivity = WeakReference(activity)
        filePickerLauncher = activity.registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            handleFilePickerResult(uri)
        }
    }

    fun unregisterFilePicker() {
        currentActivity = null
        filePickerLauncher = null
        pendingFilePickerCallback = null
    }

    fun launchFilePicker(callback: (ImportResult) -> Unit) {
        pendingFilePickerCallback = callback
        filePickerLauncher?.launch(arrayOf("*/*")) // Allow any file type
            ?: callback(ImportResult(FileActionStatus.FAILURE, "File picker not initialized. Is Activity registered?"))
    }

    private fun handleFilePickerResult(uri: Uri?) {
        val callback = pendingFilePickerCallback
        pendingFilePickerCallback = null // Clear pending callback

        if (uri != null) {
            val activity = currentActivity?.get()
            if (activity != null) {
                try {
                    val content = activity.contentResolver.openInputStream(uri)?.bufferedReader()?.use { it.readText() }
                    if (content != null) {
                        callback?.invoke(ImportResult(FileActionStatus.SUCCESS, "File selected: ${uri.lastPathSegment}", content))
                    } else {
                        callback?.invoke(ImportResult(FileActionStatus.FAILURE, "Failed to read file content."))
                    }
                } catch (e: Exception) {
                    callback?.invoke(ImportResult(FileActionStatus.FAILURE, "Error reading file: ${e.message}"))
                }
            } else {
                callback?.invoke(ImportResult(FileActionStatus.FAILURE, "Activity context lost during file selection."))
            }
        } else {
            callback?.invoke(ImportResult(FileActionStatus.CANCELED, "File selection canceled."))
        }
    }
}