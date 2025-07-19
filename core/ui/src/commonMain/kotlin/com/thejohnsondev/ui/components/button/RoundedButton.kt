package com.thejohnsondev.ui.components.button

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import com.thejohnsondev.ui.components.loader.Loader
import com.thejohnsondev.ui.designsystem.EquallyRounded
import com.thejohnsondev.ui.designsystem.Percent70
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size2
import com.thejohnsondev.ui.designsystem.Size24
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size48
import com.thejohnsondev.ui.model.button.ButtonStyle
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.bounceClick
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.core.ui.generated.resources.buttons
import vaultmultiplatform.core.ui.generated.resources.cd_app_logo

@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    text: String = stringResource(ResString.buttons),
    imageVector: ImageVector? = null,
    imageComposable: @Composable (() -> Unit)? = null,
    loading: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ),
    buttonShape: Shape = EquallyRounded.medium,
    buttonStyle: ButtonStyle = ButtonStyle.REGULAR,
    disableBounceAnimation: Boolean = false,
) {
    val buttonColor =
        if (enabled && !loading) colors.containerColor else colors.containerColor.copy(alpha = Percent70)
    val contentColor =
        if (enabled) colors.contentColor else colors.contentColor.copy(alpha = Percent70)
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(Size48)
            .applyIf(!disableBounceAnimation && enabled && !loading) {
                bounceClick()
            }
            .clip(buttonShape)
            .clickable {
                if (enabled && !loading) {
                    onClick()
                }
            }
            .applyIf(buttonStyle == ButtonStyle.OUTLINE) {
                border(
                    width = Size2,
                    color = buttonColor,
                    shape = buttonShape
                )
            },
        color = when (buttonStyle) {
            ButtonStyle.REGULAR -> buttonColor
            ButtonStyle.OUTLINE -> Color.Transparent
            ButtonStyle.TEXT -> Color.Transparent
        },
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = Size16),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (loading) {
                Loader(
                    modifier = Modifier.size(Size24),
                    iconTintColor = if (enabled) colors.contentColor else colors.contentColor.copy(
                        alpha = Percent70
                    )
                )
            } else {
                Box(
                    modifier = Modifier
                        .padding(end = Size4)
                ) {
                    imageComposable?.let {
                        it()
                    }
                    imageVector?.let { safeImageVector ->
                        Icon(
                            modifier = Modifier
                                .size(Size24),
                            imageVector = safeImageVector,
                            contentDescription = stringResource(ResString.cd_app_logo),
                            tint = if (enabled) colors.contentColor else colors.contentColor.copy(
                                alpha = Percent70
                            )
                        )
                    }
                }
                Text(
                    text = text,
                    color = when (buttonStyle) {
                        ButtonStyle.REGULAR -> contentColor
                        ButtonStyle.OUTLINE -> buttonColor
                        ButtonStyle.TEXT -> contentColor
                    },
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }
    }
}