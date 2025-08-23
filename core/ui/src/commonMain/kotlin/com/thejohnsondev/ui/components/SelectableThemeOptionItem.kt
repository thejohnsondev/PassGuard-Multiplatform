package com.thejohnsondev.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.thejohnsondev.ui.designsystem.Percent50i
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size36
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size56
import com.thejohnsondev.ui.designsystem.Size64
import com.thejohnsondev.ui.designsystem.Size8

@Composable
fun MiniSelectableOptionItem(
    modifier: Modifier = Modifier,
    optionTitle: String,
    isSelected: Boolean,
    optionContent: (@Composable () -> Unit)? = null,
    onOptionSelect: () -> Unit,
) {
    Surface(
        modifier = modifier
            .wrapContentWidth()
            .defaultMinSize(minWidth = Size56)
            .height(if (isSelected) Size64 else Size56)
            .wrapContentHeight()
            .clip(RoundedCornerShape(Size16))
            .clickable {
                onOptionSelect()
            }
    ) {
        Row(
            modifier = Modifier
                .padding(Size12),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            optionContent?.let {
                Box(
                    modifier = Modifier
                        .size(Size36)
                        .clip(RoundedCornerShape(percent = Percent50i))
                ) {
                    optionContent()
                }
            }
            AnimatedVisibility(isSelected) {
                Text(
                    modifier = Modifier.padding(start = Size8, end = Size4),
                    text = optionTitle
                )
            }
        }
    }
}