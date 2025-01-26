package com.thejohnsondev.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.thejohnsondev.common.EXPAND_ANIM_DURATION
import com.thejohnsondev.common.utils.hidden
import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.ui.components.ExpandableContent
import com.thejohnsondev.ui.components.LoadedImage
import com.thejohnsondev.ui.components.RoundedContainer
import com.thejohnsondev.ui.components.RoundedIconButton
import com.thejohnsondev.ui.designsystem.EqualRounded
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size2
import com.thejohnsondev.ui.designsystem.Size22
import com.thejohnsondev.ui.designsystem.Size24
import com.thejohnsondev.ui.designsystem.Size32
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size40
import com.thejohnsondev.ui.designsystem.Size42
import com.thejohnsondev.ui.designsystem.Size56
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.SizeMinus
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorFavorite
import com.thejohnsondev.ui.model.PasswordUIModel
import com.thejohnsondev.ui.model.getImageVector
import com.thejohnsondev.ui.utils.bounceClick
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.feature.vault.presentation.generated.resources.Res
import vaultmultiplatform.feature.vault.presentation.generated.resources.created
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
    val itemTransitionState = remember {
        MutableTransitionState(isExpanded).apply {
            targetState = !isExpanded
        }
    }
    val borderTransitionState = remember {
        MutableTransitionState(!item.showUpdateAnimation).apply {
            targetState = item.showUpdateAnimation
        }
    }
    val itemTransition = rememberTransition(itemTransitionState, label = "")
    val borderTransition = rememberTransition(borderTransitionState, label = "")
    val cardBgColor by itemTransition.animateColor({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isExpanded) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerHigh
    }
    val draggingCardBgColor by itemTransition.animateColor({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isDragging) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    }
    val contentColor by itemTransition.animateColor({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isExpanded) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
    }
    val favoriteColor by itemTransition.animateColor({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isFavorite) themeColorFavorite else if (isExpanded) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
    }
    val draggingContentColor by itemTransition.animateColor({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isDragging) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
    }
    val cardPaddingHorizontal by itemTransition.animateDp({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isExpanded) Size4 else Size8
    }
    val imageSize by itemTransition.animateDp({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (isDragging) Size56 else Size42
    }
    val borderWidth by borderTransition.animateDp({
        tween(durationMillis = EXPAND_ANIM_DURATION)
    }, label = "") {
        if (item.showUpdateAnimation) Size2 else SizeMinus
    }
    val modifiedItemBorderColor = MaterialTheme.colorScheme.primary

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = cardPaddingHorizontal, bottom = Size8, end = cardPaddingHorizontal)
            .border(
                width = borderWidth,
                color = modifiedItemBorderColor,
                shape = EqualRounded.medium
            ),
        shape = EqualRounded.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (isReordering) draggingCardBgColor else cardBgColor
        )
    ) {
        Column(
            modifier = if (isReordering) {
                Modifier.fillMaxWidth()
            } else {
                Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = {
                            onClick(item)
                        },
                        onLongClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onCopyClick(item.userName)
                        })
            },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .padding(top = Size12, bottom = Size12, end = Size12)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = Size12)
                        .size(imageSize + Size8)
                ) {
                    Surface(
                        modifier = Modifier
                            .size(imageSize)
                            .align(Alignment.Center),
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
                    Box(
                        modifier = Modifier
                            .size(Size22)
                            .align(Alignment.BottomEnd)
                            .padding(top = Size4, start = Size4)
                            .clip(RoundedCornerShape(Size4))
                            .background(item.category.colors.getUnselectedContainerColor()),
                    ) {
                        val icon = item.category.categoryIcon.getImageVector()
                        icon?.let {
                            Icon(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(Size12),
                                imageVector = icon,
                                contentDescription = null,
                                tint = item.category.colors.getUnselectedContentColor()
                            )
                        }
                    }
                }
                Column(modifier = Modifier.weight(1f)){
                    Text(
                        modifier = Modifier
                            .padding(start = Size16)
                            .fillMaxWidth(),
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isReordering) draggingContentColor else contentColor,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = Size16)
                            .fillMaxWidth(),
                        text = item.userName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isReordering) draggingContentColor else contentColor,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }

                IconButton(
                    modifier = Modifier
                        .size(Size32)
                        .bounceClick(),
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onFavoriteClick(item)
                    }
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Star else Icons.Default.StarOutline,
                        contentDescription = null,
                        tint = favoriteColor
                    )
                }
                IconButton(
                    modifier = Modifier
                        .size(Size32)
                        .bounceClick(),
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onCopySensitiveClick(item.password)
                    }
                ) {
                    Icon(
                        imageVector = if (isReordering) Icons.Default.DragHandle else Icons.Default.ContentCopy,
                        contentDescription = null,
                        tint = if (isReordering) draggingContentColor else contentColor
                    )
                }
            }
        }

        ExpandableContent(visible = isExpanded) {
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

@Composable
fun ExpandedContent(
    passwordModel: PasswordUIModel,
    contentColor: Color,
    onCopyClick: (String) -> Unit,
    onDeleteClick: (PasswordUIModel) -> Unit,
    onEditClick: (PasswordUIModel) -> Unit
) {
    var isHidden by remember {  // TODO add a UI setting to make it visible by default
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
                .padding(start = Size16, end = Size16),
            color = MaterialTheme.colorScheme.surfaceContainerLow,
            isTopRounded = true,
            isBottomRounded = passwordModel.additionalFields.isEmpty()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isHidden = !isHidden
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = Size12, vertical = Size16)
                        .weight(1f),
                    text = password,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Icon(
                    modifier = Modifier.padding(end = Size8)
                        .clip(RoundedCornerShape(Size8))
                        .clickable {
                            isHidden = !isHidden
                        },
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
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    isTopRounded = false,
                    isBottomRounded = index == passwordModel.additionalFields.size - 1
                ) {
                    AdditionalFieldItem(additionalField = it) {
                        onCopyClick(it)
                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            MoreInfo(
                modifier = Modifier
                    .weight(1f),
                contentColor = contentColor,
                passwordModel = passwordModel
            )
            RoundedIconButton(
                modifier = Modifier
                    .padding(start = Size16, end = Size4, bottom = Size16, top = Size16)
                    .size(Size40),
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onEditClick(passwordModel)
                },
                imageVector = Icons.Filled.Edit,
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                iconColor = MaterialTheme.colorScheme.primaryContainer
            )
            RoundedIconButton(
                modifier = Modifier
                    .padding(start = Size4, end = Size16, bottom = Size16, top = Size16)
                    .size(Size40),
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onDeleteClick(passwordModel)
                },
                imageVector = Icons.Filled.Delete,
                containerColor = MaterialTheme.colorScheme.errorContainer,
                iconColor = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}

@Composable
private fun MoreInfo(
    modifier: Modifier = Modifier,
    contentColor: Color,
    passwordModel: PasswordUIModel
) {
    var isInfoHidden by remember {
        mutableStateOf(true)
    }
    Row(
        modifier = modifier.padding(start = Size8, end = Size16),
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
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = Size8),
                        text = "${stringResource(Res.string.created)}${passwordModel.createdTime}",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdditionalFieldItem(
    additionalField: AdditionalFieldDto,
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
        color = MaterialTheme.colorScheme.surfaceContainerLow
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = Size8, end = Size8, top = Size12, bottom = Size4),
                    text = additionalField.title,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    modifier = Modifier
                        .padding(start = Size8, end = Size8, top = Size4, bottom = Size12),
                    text = value,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Icon(
                modifier = Modifier.padding(end = Size8)
                    .size(Size24)
                    .clip(RoundedCornerShape(Size8))
                    .clickable {
                        isHidden = !isHidden
                    },
                imageVector = eyeImage,
                contentDescription = null
            )
        }
    }
}