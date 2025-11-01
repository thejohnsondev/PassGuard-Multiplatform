package com.thejohnsondev.landing.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.thejohnsondev.common.DEFAULT_ANIM_DURATION
import com.thejohnsondev.ui.components.CountryFlagItem
import com.thejohnsondev.ui.components.MiniSelectableOptionItem
import com.thejohnsondev.ui.components.button.RoundedButton
import com.thejohnsondev.ui.designsystem.Percent10
import com.thejohnsondev.ui.designsystem.Percent100
import com.thejohnsondev.ui.designsystem.Percent50
import com.thejohnsondev.ui.designsystem.Percent70
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size2
import com.thejohnsondev.ui.designsystem.Size24
import com.thejohnsondev.ui.designsystem.Size36
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size64
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.SizeDefault
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.SunnySelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.TealSelectableItemColors
import com.thejohnsondev.ui.model.button.ButtonStyle
import com.thejohnsondev.ui.utils.ResDrawable
import com.thejohnsondev.ui.utils.applyIf
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.HazeMaterials
import org.jetbrains.compose.resources.painterResource
import vaultmultiplatform.core.ui.generated.resources.ic_flag_gb
import vaultmultiplatform.core.ui.generated.resources.ic_vault_108_gradient

@Composable
fun WebNavBar(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean = false,
    isCollapsed: Boolean = false,
    hazeState: HazeState? = null,
    navigateTo: (String) -> Unit,
    onThemeButtonClick: () -> Unit,
    onLanguageButtonClick: () -> Unit,
) {
    val cornerRadius by animateDpAsState(
        if (isCollapsed) Size24 else SizeDefault,
        animationSpec = tween(DEFAULT_ANIM_DURATION)
    )
    val horizontalPadding by animateDpAsState(
        if (isCollapsed) Size24 else Size64,
        animationSpec = tween(DEFAULT_ANIM_DURATION)
    )
    val backgroundAlpha by animateFloatAsState(
        if (isCollapsed) Percent10 else 0f,
        animationSpec = tween(DEFAULT_ANIM_DURATION)
    )
    val contentWidthPercentage by animateFloatAsState(
        if (isCollapsed) Percent100 else Percent70,
        animationSpec = tween(DEFAULT_ANIM_DURATION)
    )
    val containerWidthPercentage by animateFloatAsState(
        if (isCollapsed) Percent50 else Percent100,
        animationSpec = tween(DEFAULT_ANIM_DURATION)
    )
    val borderRadius by animateFloatAsState(
        if (isCollapsed) Percent100 else -Percent100,
        animationSpec = tween(DEFAULT_ANIM_DURATION)
    )

    Box(
        modifier = modifier
            .fillMaxWidth(containerWidthPercentage)
            .clip(RoundedCornerShape(cornerRadius))
            .animateContentSize()
            .applyIf(hazeState != null && isCollapsed) {
                hazeEffect(
                    state = hazeState,
                    style = HazeMaterials.thin()
                )
            }
            .applyIf(isCollapsed) {
                background(MaterialTheme.colorScheme.surface.copy(backgroundAlpha))
            }
            .border(
                width = borderRadius.dp,
                shape = RoundedCornerShape(cornerRadius),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = Percent10)
            ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(contentWidthPercentage)
                .padding(horizontal = horizontalPadding, vertical = Size8)
                .align(Alignment.Center),
        ) {
            Image(
                modifier = Modifier
                    .padding(start = Size12)
                    .size(Size24)
                    .align(Alignment.CenterStart),
                painter = painterResource(ResDrawable.ic_vault_108_gradient),
                contentDescription = "Logo",
            )
            Row(
                modifier = Modifier
                    .align(Alignment.Center),
                horizontalArrangement = Arrangement.spacedBy(Size12)
            ) {
                RoundedButton(
                    onClick = { navigateTo("/home") },
                    text = "Home",
                    buttonStyle = ButtonStyle.TEXT,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                )
                RoundedButton(
                    onClick = { navigateTo("/download") }, text = "Download",
                    buttonStyle = ButtonStyle.TEXT,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                )
                RoundedButton(
                    onClick = { navigateTo("/privacy") },
                    text = "Privacy",
                    buttonStyle = ButtonStyle.TEXT,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MiniSelectableOptionItem(
                    modifier = Modifier
                        .padding(
                            start = Size4,
                            end = Size4,
                        ),
                    optionTitle = if (isDarkTheme) {
                        "Dark theme"
                    } else {
                        "Light theme"
                    },
                    isSelected = false,
                    optionContent = {
                        Box(
                            modifier = Modifier
                                .size(Size36)
                                .clip(RoundedCornerShape(100))
                                .background(
                                    if (isDarkTheme) {
                                        TealSelectableItemColors.getSelectedContainerColor()
                                    } else {
                                        SunnySelectableItemColors.getSelectedContainerColor()
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(Size2)
                                    .size(Size24),
                                contentDescription = null,
                                imageVector = if (isDarkTheme) {
                                    Icons.Default.DarkMode
                                } else {
                                    Icons.Default.LightMode
                                },
                                tint = if (isDarkTheme) {
                                    TealSelectableItemColors.getSelectedContentColor()
                                } else {
                                    SunnySelectableItemColors.getSelectedContentColor()
                                }
                            )
                        }
                    }
                ) {
                    onThemeButtonClick()
                }
                MiniSelectableOptionItem(
                    modifier = Modifier
                        .padding(
                            start = Size4,
                            end = Size4,
                        ),
                    optionTitle = "Language",
                    isSelected = false,
                    optionContent = {
                        CountryFlagItem(
                            modifier = Modifier
                                .size(Size36),
                            flagDrawableResource = ResDrawable.ic_flag_gb
                        )
                    }
                ) {
                    onLanguageButtonClick
                }
            }
        }
    }
}