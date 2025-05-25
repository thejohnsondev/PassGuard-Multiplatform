package com.thejohnsondev.ui.components.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.thejohnsondev.ui.components.button.BackArrowButton
import com.thejohnsondev.ui.designsystem.Size16

@Composable
fun ModalDragHandle(
    onDismissRequest: () -> Unit,
    centerContent: (@Composable () -> Unit)? = null,
    endContent: (@Composable () -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Size16)
    ) {
        BackArrowButton(
            modifier = Modifier.padding(start = Size16)
                .align(Alignment.CenterStart),
            onClick = {
                onDismissRequest()
            }
        )
        centerContent?.let {
            Box(
                modifier = Modifier
                    .align(Alignment.Center),
            ) {
                centerContent()
            }
        }
        endContent?.let {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
            ) {
                endContent()
            }
        }
    }
}