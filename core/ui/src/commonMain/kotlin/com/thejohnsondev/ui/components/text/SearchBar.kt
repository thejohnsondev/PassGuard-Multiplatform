package com.thejohnsondev.ui.components.text

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import com.thejohnsondev.common.empty
import com.thejohnsondev.ui.designsystem.EquallyRounded
import com.thejohnsondev.ui.designsystem.Percent90
import com.thejohnsondev.ui.designsystem.Percent98
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size22
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.bounceClick
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.core.ui.generated.resources.search

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onQueryEntered: (String) -> Unit,
    onQueryClear: () -> Unit,
) {
    var searchQuery by remember {
        mutableStateOf("")
    }
    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current
    Surface(
        modifier = modifier
            .clip(EquallyRounded.large)
            .bounceClick(
                disableCursorEnterAnimation = true
            ),
        shape = EquallyRounded.large,
        color = MaterialTheme.colorScheme.surfaceContainerHigh
    ) {
        Row(
            modifier = Modifier
                .padding(Size16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PrimaryTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .wrapContentHeight()
                    .fillMaxWidth(Percent90),
                onValueChanged = {
                    searchQuery = it
                    onQueryEntered(searchQuery)
                },
                maxLines = 1,
                value = searchQuery,
                hint = if (searchQuery.isBlank()) stringResource(ResString.search) else String.empty,
                textColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (searchQuery.isNotBlank()) {
                Icon(
                    modifier = Modifier
                        .size(Size22)
                        .bounceClick()
                        .clip(EquallyRounded.medium)
                        .clickable {
                            searchQuery = String.empty
                            onQueryClear()
                            focusManager.clearFocus()
                        },
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(ResString.search)
                )
            }
        }
    }
}