package com.thejohnsondev.ui.components.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.getGlobalFontFamily

@Composable
fun ErrorText(
    errorText: String?,
    textColor: Color = MaterialTheme.colorScheme.error,
    fontSize: TextUnit,
    modifier: Modifier = Modifier
) {
    errorText?.let {
        Text(
            text = errorText,
            modifier = modifier.padding(top = Size8),
            style = TextStyle(
                color = textColor,
                fontSize = fontSize
            ),
            fontFamily = getGlobalFontFamily()
        )
    }
}