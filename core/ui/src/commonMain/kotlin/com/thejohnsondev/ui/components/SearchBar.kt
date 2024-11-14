package com.thejohnsondev.ui.components

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
import androidx.compose.ui.platform.LocalFocusManager
import com.thejohnsondev.common.EMPTY
import com.thejohnsondev.ui.designsystem.EqualRounded
import com.thejohnsondev.ui.designsystem.Percent90
import com.thejohnsondev.ui.designsystem.Percent98
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size22
import com.thejohnsondev.ui.utils.bounceClick
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.core.ui.generated.resources.Res
import vaultmultiplatform.core.ui.generated.resources.search

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onQueryEntered: (String) -> Unit,
    onQueryClear: () -> Unit
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
            .clip(EqualRounded.large)
            .bounceClick(
                minScale = Percent98,
                disableCursorEnterAnimation = true
            ),
        shape = EqualRounded.large,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier
                .padding(Size16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HintTextField(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(Percent90),
                onValueChanged = {
                    searchQuery = it
                    onQueryEntered(searchQuery)
                },
                maxLines = 1,
                value = searchQuery,
                hint = if (searchQuery.isBlank()) stringResource(Res.string.search) else EMPTY,
                textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusRequester = focusRequester
            )
            if (searchQuery.isNotBlank()) {
                Icon(
                    modifier = Modifier
                        .size(Size22)
                        .bounceClick()
                        .clip(EqualRounded.medium)
                        .clickable {
                            searchQuery = EMPTY
                            onQueryClear()
                            focusManager.clearFocus()
                        },
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(Res.string.search)
                )
            }
        }
    }
}