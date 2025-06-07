package com.thejohnsondev.ui.components.container

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.thejohnsondev.ui.designsystem.Percent10
import com.thejohnsondev.ui.designsystem.Percent100
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size32
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.SizeBorder

@Composable
fun CsvTableDisplay(
    modifier: Modifier = Modifier, csvContent: String, errorValue: String? = null
) {
    val lines = csvContent.lines().filter { it.isNotBlank() }
    if (lines.isEmpty()) {
        Text(
            text = "No CSV content to display.", modifier = Modifier.padding(16.dp)
        )
        return
    }
    val headerCells = lines.first().split(",")
    val dataRows = lines.drop(1).map { it.split(",") }

    RoundedContainer(
        modifier = modifier.fillMaxWidth().padding(Size16).border(
            width = SizeBorder,
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(
                Size8
            )
        ), shape = RoundedCornerShape(Size8)
    ) {
        Column {
            CsvTableRow(
                cells = headerCells,
                isHeader = true,
                errorValue = errorValue
            )
            dataRows.forEach { row ->
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().height(SizeBorder),
                    color = MaterialTheme.colorScheme.primary
                )
                CsvTableRow(
                    cells = row,
                    isHeader = false,
                    errorValue = errorValue
                )
            }
        }
    }
}

@Composable
private fun CsvTableRow(
    cells: List<String>, isHeader: Boolean, errorValue: String? = null
) {

    val fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal
    val cellPadding = Modifier.padding(horizontal = Size8, vertical = Size4)
    val backgroundColor =
        if (isHeader) MaterialTheme.colorScheme.primary.copy(alpha = Percent10) else Color.Transparent

    Row(
        modifier = Modifier.fillMaxWidth().background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        cells.forEachIndexed { index, cellContent ->

            Box(
                modifier = Modifier.weight(Percent100).wrapContentHeight().background(
                    if (errorValue == cellContent) MaterialTheme.colorScheme.errorContainer.copy(
                        alpha = Percent10
                    ) else Color.Transparent
                ).then(cellPadding)
            ) {
                Text(
                    text = cellContent.trim(),
                    fontWeight = if (errorValue == cellContent) FontWeight.SemiBold else fontWeight,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = if (errorValue == cellContent) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                )
            }
            if (index != cells.lastIndex) {
                VerticalDivider(
                    modifier = Modifier.width(SizeBorder).height(Size32),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}