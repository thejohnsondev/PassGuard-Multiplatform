package com.thejohnsondev.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import com.thejohnsondev.ui.components.HintTextField
import com.thejohnsondev.ui.designsystem.EqualRounded
import com.thejohnsondev.ui.designsystem.Percent90
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size2
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.Size98
import com.thejohnsondev.ui.utils.KeyboardManager
import com.thejohnsondev.ui.utils.ResString
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.core.ui.generated.resources.title
import vaultmultiplatform.core.ui.generated.resources.value
import vaultmultiplatform.core.ui.generated.resources.visibility

@Composable
internal fun AdditionalFieldItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    isEditMode: Boolean,
    onTitleChanged: (String) -> Unit,
    onValueChanged: (String) -> Unit,
    onDeleteClick: () -> Unit,
) {
    val titleFocusRequester = remember {
        FocusRequester()
    }
    val valueFocusRequester = remember {
        FocusRequester()
    }
    var isHidden by remember {
        mutableStateOf(false)
    }
    val keyboardController = KeyboardManager.getKeyboardController()
    val eyeImage = if (isHidden) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        shape = EqualRounded.medium,
        color = MaterialTheme.colorScheme.surfaceContainerHigh
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f),
                color = MaterialTheme.colorScheme.surfaceContainerHigh
            ) {
                Column {
                    HintTextField(
                        modifier = Modifier
                            .padding(Size12)
                            .fillMaxWidth()
                            .focusRequester(titleFocusRequester),
                        value = title,
                        onValueChanged = {
                            onTitleChanged(it)
                        },
                        imeAction = androidx.compose.ui.text.input.ImeAction.Next,
                        onKeyboardAction = KeyboardActions {
                            valueFocusRequester.requestFocus()
                        },
                        hint = stringResource(ResString.title)
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(horizontal = Size8),
                        thickness = Size2
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Top
                        ) {
                            HintTextField(
                                modifier = Modifier
                                    .padding(Size12)
                                    .fillMaxWidth(Percent90)
                                    .focusRequester(valueFocusRequester),
                                value = value,
                                onValueChanged = {
                                    onValueChanged(it)
                                },
                                imeAction = androidx.compose.ui.text.input.ImeAction.Done,
                                keyboardType = KeyboardType.Password,
                                passwordVisible = !isHidden,
                                hint = stringResource(ResString.value),
                                onKeyboardAction = KeyboardActions {
                                    keyboardController?.hide()
                                }
                            )
                        }
                        IconButton(
                            modifier = Modifier.padding(end = Size8),
                            onClick = {
                                isHidden = !isHidden
                            }
                        ) {
                            Icon(
                                imageVector = eyeImage,
                                contentDescription = stringResource(ResString.visibility),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            Surface(
                modifier = Modifier
                    .height(Size98)
                    .wrapContentWidth()
                    .padding(Size4)
                    .clickable {
                        onDeleteClick()
                    },
                shape = RoundedCornerShape(
                    topEnd = Size12,
                    bottomEnd = Size12,
                    topStart = Size2,
                    bottomStart = Size2
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ) {
                Icon(
                    modifier = Modifier.padding(horizontal = Size4),
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(ResString.visibility),
                    tint = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
    if (!isEditMode) {
        LaunchedEffect(titleFocusRequester) {
            titleFocusRequester.requestFocus()
        }
    }
}
