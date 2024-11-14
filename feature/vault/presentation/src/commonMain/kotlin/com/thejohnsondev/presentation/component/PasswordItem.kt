package com.thejohnsondev.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.thejohnsondev.common.EXPAND_ANIM_DURATION
import com.thejohnsondev.common.utils.hidden
import com.thejohnsondev.domain.models.PasswordUIModel
import com.thejohnsondev.model.vault.AdditionalFieldModel
import com.thejohnsondev.ui.components.ExpandableContent
import com.thejohnsondev.ui.components.LoadedImage
import com.thejohnsondev.ui.components.RoundedButton
import com.thejohnsondev.ui.components.RoundedContainer
import com.thejohnsondev.ui.designsystem.EqualRounded
import com.thejohnsondev.ui.designsystem.Percent50
import com.thejohnsondev.ui.designsystem.Percent70
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size36
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size42
import com.thejohnsondev.ui.designsystem.Size56
import com.thejohnsondev.ui.designsystem.Size6
import com.thejohnsondev.ui.designsystem.Size64
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.themeColorFavorite
import com.thejohnsondev.ui.model.ButtonShape
import com.thejohnsondev.ui.utils.bounceClick
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.feature.vault.presentation.generated.resources.Res
import vaultmultiplatform.feature.vault.presentation.generated.resources.created
import vaultmultiplatform.feature.vault.presentation.generated.resources.delete
import vaultmultiplatform.feature.vault.presentation.generated.resources.edit
import vaultmultiplatform.feature.vault.presentation.generated.resources.ic_password
import vaultmultiplatform.feature.vault.presentation.generated.resources.modified
import vaultmultiplatform.feature.vault.presentation.generated.resources.more_info

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PasswordItem(
    modifier: Modifier = Modifier,
    item: PasswordUIModel,
    isReordering: Boolean = false,
    isDragging: Boolean = false,
    isExpanded: Boolean = false,
    isFavorite: Boolean = false,
    onClick: (PasswordUIModel) -> Unit,
    onCopySensitiveClick: (String) -> Unit,
    onCopyClick: (String) -> Unit,
    onFavoriteClick: (PasswordUIModel) -> Unit,
    onDeleteClick: (PasswordUIModel) -> Unit,
    onEditClick: (PasswordUIModel) -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val transitionState = remember {
        MutableTransitionState(isExpanded).apply {
            targetState = !isExpanded
        }
    }
    val transition = updateTransition(transitionState, label = "")
    val cardBgColor by transition.animateColor({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    }
    val draggingCardBgColor by transition.animateColor({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isDragging) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    }
    val contentColor by transition.animateColor({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isExpanded) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
    }
    val favoriteColor by transition.animateColor({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isFavorite) themeColorFavorite else if (isExpanded) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
    }
    val draggingContentColor by transition.animateColor({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isDragging) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
    }
    val cardPaddingHorizontal by transition.animateDp({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isExpanded) Size8 else Size16
    }
    val imageSize by transition.animateDp({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isDragging) Size56 else Size42
    }


    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = cardPaddingHorizontal, bottom = Size8, end = cardPaddingHorizontal),
        shape = EqualRounded.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (isReordering) draggingCardBgColor else cardBgColor
        )
    ) {
        Column(
            modifier = if (isReordering) {
                Modifier
            } else {
                Modifier
                    .combinedClickable(
                        onClick = {
                            onClick(item)
                        },
                        onLongClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onCopyClick(item.title)
                        })
            },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box {
                Row(
                    modifier = Modifier
                        .padding(Size16)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier.size(imageSize),
                            color = Color.White,
                            shape = EqualRounded.small
                        ) {
                            LoadedImage(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(Size4),
                                imageUrl = item.organizationLogo ?: "",
                                errorDrawableResource = Res.drawable.ic_password,
                                placeholderDrawableResource = Res.drawable.ic_password,
                                placeholderDrawableTintColor = MaterialTheme.colorScheme.inversePrimary,
                                backgroundColor = Color.White,
                                showLoading = true
                            )
                        }
                        Column {
                            Text(
                                modifier = Modifier
                                    .padding(start = Size16)
                                    .fillMaxWidth(Percent70),
                                text = item.organization,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isReordering) draggingContentColor else contentColor,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                            Text(
                                modifier = Modifier
                                    .padding(start = Size16)
                                    .fillMaxWidth(Percent70),
                                text = item.title,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isReordering) draggingContentColor else contentColor,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(
                            modifier = Modifier
                                .size(Size36)
                                .bounceClick(),
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                onFavoriteClick(item)
                            }) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Star else Icons.Default.StarOutline,
                                contentDescription = null,
                                tint = favoriteColor
                            )
                        }
                        IconButton(
                            modifier = Modifier
                                .size(Size36)
                                .bounceClick(),
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                onCopySensitiveClick(item.password)
                            }) {
                            Icon(
                                imageVector = if (isReordering) Icons.Default.DragHandle else Icons.Default.ContentCopy,
                                contentDescription = null,
                                tint = if (isReordering) draggingContentColor else contentColor
                            )
                        }
                    }

                }
                Surface(
                    modifier = Modifier
                        .height(imageSize)
                        .width(Size6)
                        .align(Alignment.CenterStart),
                    shape = RoundedCornerShape(topEnd = Size64, bottomEnd = Size64),
                    color = Color(0xff0a2f2c5),
                ) {
                }
            }
            ExpandableContent(
                visible = isExpanded
            ) {
                ExpandedContent(
                    passwordModel = item,
                    contentColor = contentColor,
                    onCopyClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onCopySensitiveClick(it)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    },
                    onEditClick = {
                        onEditClick(it)
                    }
                )
            }
        }
    }

}

@Composable
fun ExpandedContent(
    passwordModel: PasswordUIModel,
    contentColor: Color,
    onCopyClick: (String) -> Unit,
    onDeleteClick: (PasswordUIModel) -> Unit,
    onEditClick: (PasswordUIModel) -> Unit
) {
    var isHidden by remember {
        mutableStateOf(true)
    }
    var isInfoHidden by remember {
        mutableStateOf(true)
    }
    val haptic = LocalHapticFeedback.current
    val password = if (isHidden) passwordModel.password.hidden() else passwordModel.password
    val eyeImage = if (isHidden) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RoundedContainer(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = Size16, end = Size16)
                .clickable {
                    isHidden = !isHidden
                },
            color = MaterialTheme.colorScheme.surfaceVariant,
            isFirstItem = true,
            isLastItem = passwordModel.additionalFields.isEmpty()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = Size12, vertical = Size16), text = password,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Icon(
                    modifier = Modifier.padding(end = Size8),
                    imageVector = eyeImage,
                    contentDescription = null
                )
            }
        }
        Column {
            passwordModel.additionalFields.forEachIndexed { index, it ->
                RoundedContainer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = Size16, end = Size16, top = Size4),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    isFirstItem = false,
                    isLastItem = index == passwordModel.additionalFields.size - 1
                ) {
                    AdditionalFieldItem(additionalField = it) {
                        onCopyClick(it)
                    }
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            RoundedButton(
                modifier = Modifier
                    .weight(Percent50)
                    .padding(start = Size16, end = Size4, bottom = Size8, top = Size16)
                    .bounceClick(),
                text = stringResource(Res.string.edit),
                imageVector = Icons.Filled.Edit,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                buttonShape = ButtonShape.START_ROUNDED,
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onEditClick(passwordModel)
                }
            )
            RoundedButton(
                modifier = Modifier
                    .weight(Percent50)
                    .padding(start = Size4, end = Size16, bottom = Size8, top = Size16)
                    .bounceClick(),
                text = stringResource(Res.string.delete),
                imageVector = Icons.Filled.Delete,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                ),
                buttonShape = ButtonShape.END_ROUNDED,
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onDeleteClick(passwordModel)
                }
            )
        }
        Row(
            modifier = Modifier.padding(start = Size8, end = Size16, bottom = Size8)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = {
                    isInfoHidden = !isInfoHidden
                }) {
                Icon(
                    imageVector = Icons.Default.Info,
                    tint = contentColor,
                    contentDescription = null
                )
            }
            AnimatedVisibility(visible = isInfoHidden) {
                Text(
                    text = stringResource(Res.string.more_info),
                    color = contentColor,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            AnimatedVisibility(visible = !isInfoHidden) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                ) {
                    if (!passwordModel.modifiedTime.isNullOrBlank()) {
                        Row(
                            modifier = Modifier
                                .padding(bottom = Size8),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(Size16),
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = contentColor
                            )
                            Text(
                                modifier = Modifier
                                    .padding(start = Size8),
                                text = "${stringResource(Res.string.modified)}${passwordModel.modifiedTime.orEmpty()}",
                                color = contentColor,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(Size16),
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = Size8),
                            text = "${stringResource(Res.string.created)}${passwordModel.createdTime}",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdditionalFieldItem(
    additionalField: AdditionalFieldModel,
    onLongClick: (String) -> Unit
) {
    var isHidden by remember {
        mutableStateOf(true)
    }
    val value = if (isHidden) additionalField.value.hidden() else additionalField.value
    val eyeImage = if (isHidden) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    isHidden = !isHidden
                },
                onLongClick = {
                    onLongClick(additionalField.value)
                }
            ),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = Size8, end = Size8, top = Size12, bottom = Size4),
                    text = additionalField.title
                )
                Text(
                    modifier = Modifier
                        .padding(start = Size8, end = Size8, top = Size4, bottom = Size12),
                    text = value
                )
            }
            Icon(
                modifier = Modifier.padding(end = Size8),
                imageVector = eyeImage,
                contentDescription = null
            )
        }
    }
}