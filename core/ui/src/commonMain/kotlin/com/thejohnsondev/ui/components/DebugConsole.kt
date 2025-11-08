package com.thejohnsondev.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import com.thejohnsondev.common.utils.Logger
import com.thejohnsondev.ui.designsystem.Percent90
import com.thejohnsondev.ui.designsystem.Size256
import com.thejohnsondev.ui.designsystem.Size36
import com.thejohnsondev.ui.designsystem.Size360
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.Size98
import com.thejohnsondev.ui.designsystem.SizeBorder
import com.thejohnsondev.ui.utils.padding

@Composable
fun DebugConsole(modifier: Modifier = Modifier) {
    val logs = remember { mutableStateListOf<String>() }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        Logger.attachLogListener { msg ->
            logs.add(0, msg)
        }
    }

    Box(
        modifier = modifier
            .width(if (expanded) Size360 else Size98)
            .height(if (expanded) Size256 else Size36)
            .background(MaterialTheme.colorScheme.surface.copy(Percent90))
            .border(SizeBorder, MaterialTheme.colorScheme.outline)
            .clip(RoundedCornerShape(Size8))
            .padding(Size8)
            .clickable { expanded = !expanded }
    ) {
        if (!expanded) {
            Text(
                "Logs (${logs.size})",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    "Clear", modifier = Modifier
                        .padding(vertical = Size8)
                        .clickable {
                            logs.clear()
                        },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
                logs.forEachIndexed { index, log ->
                    Text(
                        text = log,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = Size4)
                    )
                }
            }
        }
    }
}
