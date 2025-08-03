package com.thejohnsondev.ui.components.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.thejohnsondev.ui.designsystem.Percent10
import com.thejohnsondev.ui.designsystem.Size48
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.bounceClick

@Composable
fun BackArrowButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val wasClicked = remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = modifier
            .bounceClick()
            .clip(RoundedCornerShape(Size48))
            .applyIf(!wasClicked.value) {
                clickable {
                    wasClicked.value = true
                    onClick()
                }
            },
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = Percent10),
    ) {
        Icon(
            modifier = Modifier
                .padding(Size8),
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back Button" // TODO add content description
        )
    }
}