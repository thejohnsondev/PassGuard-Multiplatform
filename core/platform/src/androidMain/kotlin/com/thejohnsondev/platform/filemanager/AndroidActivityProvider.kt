package com.thejohnsondev.platform.filemanager

import android.app.Activity
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import java.lang.ref.WeakReference


object AndroidActivityProvider {
    var currentActivity: WeakReference<FragmentActivity>? = null

    private var filePickerLauncher: ActivityResultLauncher<Array<String>>? = null
    private var pendingFilePickerCallback: ((ImportResult) -> Unit)? = null

    fun registerActivity(activity: Activity) {
        if (activity !is FragmentActivity) {
            throw IllegalArgumentException("Activity must be a ComponentActivity to register for file picking.")
        }
        currentActivity = WeakReference(activity)
        filePickerLauncher =
            activity.registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
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
        filePickerLauncher?.launch(arrayOf("*/*"))
            ?: callback(
                ImportResult(
                    FileActionStatus.FAILURE,
                    "File picker not initialized. Is Activity registered?"
                )
            )
    }

    private fun handleFilePickerResult(uri: Uri?) {
        val callback = pendingFilePickerCallback
        pendingFilePickerCallback = null

        if (uri != null) {
            val activity = currentActivity?.get()
            if (activity != null) {
                try {
                    val content = activity.contentResolver.openInputStream(uri)?.bufferedReader()
                        ?.use { it.readText() }
                    if (content != null) {
                        callback?.invoke(
                            ImportResult(
                                FileActionStatus.SUCCESS,
                                "File selected: ${uri.lastPathSegment}",
                                content
                            )
                        )
                    } else {
                        callback?.invoke(
                            ImportResult(
                                FileActionStatus.FAILURE,
                                "Failed to read file content."
                            )
                        )
                    }
                } catch (e: Exception) {
                    callback?.invoke(
                        ImportResult(
                            FileActionStatus.FAILURE,
                            "Error reading file: ${e.message}"
                        )
                    )
                }
            } else {
                callback?.invoke(
                    ImportResult(
                        FileActionStatus.FAILURE,
                        "Activity context lost during file selection."
                    )
                )
            }
        } else {
            callback?.invoke(ImportResult(FileActionStatus.CANCELED, "File selection canceled."))
        }
    }
}