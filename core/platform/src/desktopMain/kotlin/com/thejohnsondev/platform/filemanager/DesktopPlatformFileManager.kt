package com.thejohnsondev.platform.filemanager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

class DesktopPlatformFileManager : PlatformFileManager {

    override fun downloadCSVWithContent(
        content: String,
        fileName: String,
        onCompletion: (ExportResult) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                val downloadsDir = File(System.getProperty("user.home"), "Downloads")
                if (!downloadsDir.exists()) {
                    downloadsDir.mkdirs()
                }
                val file = File(downloadsDir, fileName)
                file.writeText(content)
                ExportResult(
                    FileActionStatus.SUCCESS,
                    "File saved to Downloads: ${file.absolutePath}"
                )
            } catch (e: Exception) {
                ExportResult(FileActionStatus.FAILURE, "Export failed: ${e.message}")
            }
            withContext(Dispatchers.Main) {
                onCompletion(result)
            }
        }
    }

    override fun importCSV(onCompletion: (ImportResult) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val fileChooser = JFileChooser().apply {
                dialogTitle = "Select File to Import"
                fileFilter = FileNameExtensionFilter("CSV Files (*.csv)", "csv")
                currentDirectory = File(System.getProperty("user.home"))
            }

            val result = withContext(Dispatchers.Main) {
                fileChooser.showOpenDialog(null)
            }

            if (result == JFileChooser.APPROVE_OPTION) {
                val selectedFile = fileChooser.selectedFile
                if (selectedFile != null) {
                    try {
                        val content = selectedFile.readText()
                        withContext(Dispatchers.Main) {
                            onCompletion(
                                ImportResult(
                                    FileActionStatus.SUCCESS,
                                    "File selected: ${selectedFile.name}",
                                    content
                                )
                            )
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            onCompletion(
                                ImportResult(
                                    FileActionStatus.FAILURE,
                                    "Failed to read file: ${e.message}"
                                )
                            )
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onCompletion(ImportResult(FileActionStatus.FAILURE, "No file selected."))
                    }
                }
            } else if (result == JFileChooser.CANCEL_OPTION) {
                withContext(Dispatchers.Main) {
                    onCompletion(
                        ImportResult(
                            FileActionStatus.CANCELED,
                            "File selection cancelled."
                        )
                    )
                }
            } else {
                withContext(Dispatchers.Main) {
                    onCompletion(
                        ImportResult(
                            FileActionStatus.FAILURE,
                            "File selection failed with unknown error."
                        )
                    )
                }
            }
        }
    }
}