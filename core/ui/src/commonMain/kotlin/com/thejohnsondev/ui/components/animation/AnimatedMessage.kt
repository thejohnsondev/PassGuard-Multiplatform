package com.thejohnsondev.ui.components.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size32
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.model.message.MessageColors
import com.thejohnsondev.ui.model.message.MessageContent
import com.thejohnsondev.ui.model.message.MessageType
import kotlinx.coroutines.delay

private const val TEXT_SHOW_DELAY = 750L
private const val TEXT_SUCCESS_HIDE_DELAY = 1500L
private const val TEXT_ERROR_HIDE_DELAY = 3000L
private const val ICON_HIDE_DELAY = 100L

@Composable
fun AnimatedMessage(
    modifier: Modifier = Modifier,
    messageContent: MessageContent? = null,
    onAnimationFinished: () -> Unit = {}
) {
    val isTextVisible = remember {
        mutableStateOf(false)
    }
    val isWholeMessageVisible = remember {
        mutableStateOf(false)
    }
    val colorModel = when (messageContent?.type) {
        MessageType.SUCCESS -> MessageColors.getSuccessColors()
        MessageType.ERROR -> MessageColors.getErrorColors()
        else -> MessageColors.getInfoColors()
    }
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = isWholeMessageVisible.value
    ) {
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(Size32))
                .background(color = colorModel.containerColor),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            messageContent?.imageVector?.let {
                Icon(
                    modifier = Modifier
                        .padding(
                            top = Size8,
                            bottom = Size8,
                            start = Size8,
                            end = if (!isTextVisible.value) Size8 else Size4
                        )
                        .size(Size16),
                    imageVector = it,
                    tint = colorModel.messageColor,
                    contentDescription = null,
                )
            }
            AnimatedVisibility(visible = isTextVisible.value) {
                if (isMessageVisible(messageContent)) {
                    Text(
                        text = messageContent?.message.orEmpty(),
                        modifier = Modifier.padding(
                            top = Size8,
                            bottom = Size8,
                            end = Size8,
                            start = if (messageContent?.imageVector != null) Size4 else Size8
                        ),
                        color = colorModel.messageColor,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
        }
    }

    LaunchedEffect(messageContent) {
        isWholeMessageVisible.value = messageContent != null
    }

    LaunchedEffect(isWholeMessageVisible.value) {
        if (messageContent != null) {
            delay(TEXT_SHOW_DELAY)
            if (isMessageVisible(messageContent)) {
                isTextVisible.value = true
                if (isMessageError(messageContent)) {
                    delay(TEXT_ERROR_HIDE_DELAY)
                } else {
                    delay(TEXT_SUCCESS_HIDE_DELAY)
                }
                isTextVisible.value = false
            }
            delay(ICON_HIDE_DELAY)
            isWholeMessageVisible.value = false
            onAnimationFinished()
        }
    }
}

private fun isMessageVisible(messageContent: MessageContent?): Boolean {
    return messageContent?.message?.isNotEmpty() ?: false
}

private fun isMessageError(messageContent: MessageContent?): Boolean {
    return messageContent?.type == MessageType.ERROR
}