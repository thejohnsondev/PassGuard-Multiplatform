package com.thejohnsondev.ui.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Filter(
    val id: String,
    val name: String,
    val imageVector: ImageVector?,
    val contentColor: Color,
    val backgroundColor: Color
)