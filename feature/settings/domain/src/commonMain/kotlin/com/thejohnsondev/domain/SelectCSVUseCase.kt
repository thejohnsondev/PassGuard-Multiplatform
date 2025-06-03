package com.thejohnsondev.domain

import com.thejohnsondev.platform.filemanager.ImportResult

interface SelectCSVUseCase {
    suspend operator fun invoke(
        onCompletion: (ImportResult) -> Unit
    )
}