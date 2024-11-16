package com.thejohnsondev.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size24
import com.thejohnsondev.ui.designsystem.Size8

@Composable
fun FilterGroup(
    modifier: Modifier = Modifier,
    filters: List<String>,
    onFilterClick: (String) -> Unit,
    defaultSelected: String
) {
    val selectedFilter = remember {
        mutableStateOf(defaultSelected)
    }

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start
    ) {
        items(filters) { filter ->
            Chip(
                modifier = Modifier
                    .padding(
                        start = if (filters.first() == filter) Size16 else Size8,
                        end = if (filters.last() == filter) Size16 else Size8,
                        bottom = Size8,
                        top = Size8
                    ),
                title = filter, selected = selectedFilter.value
            ) {
                selectedFilter.value = it
                onFilterClick(it)
            }
        }
    }

}

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    title: String,
    selected: String,
    onSelected: (String) -> Unit
) {

    val isSelected = selected == title

    val background =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer
    val contentColor =
        if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer

    Box(modifier = modifier
        .wrapContentHeight()
        .clip(CircleShape)
        .background(background)
        .clickable {
            onSelected(title)
        }) {
        Text(
            modifier = Modifier.padding(
                vertical = Size8, horizontal = if (isSelected) Size24 else Size16
            ),
            text = title,
            color = contentColor,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}