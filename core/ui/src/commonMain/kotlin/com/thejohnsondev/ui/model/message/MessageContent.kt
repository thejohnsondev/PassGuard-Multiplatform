package com.thejohnsondev.ui.model.message

import androidx.compose.ui.graphics.vector.ImageVector

data class MessageContent(
    val message: String,
    val imageVector: ImageVector? = null,
    val type: MessageType = MessageType.INFO,
)

enum class MessageType {
    SUCCESS,
    ERROR,
    INFO
}