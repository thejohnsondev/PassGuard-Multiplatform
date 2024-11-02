package com.thejohnsondev.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.vector.ImageVector
import com.thejohnsondev.ui.designsystem.Percent70
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size2
import com.thejohnsondev.ui.designsystem.Size24
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size48
import com.thejohnsondev.ui.model.ButtonShape
import com.thejohnsondev.ui.model.ButtonStyle
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.bounceClick
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.core.ui.generated.resources.Res
import vaultmultiplatform.core.ui.generated.resources.buttons
import vaultmultiplatform.core.ui.generated.resources.cd_app_logo

@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    text: String = stringResource(Res.string.buttons),
    imageVector: ImageVector? = null,
    loading: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ),
    buttonShape: ButtonShape = ButtonShape.ROUNDED,
    buttonStyle: ButtonStyle = ButtonStyle.REGULAR,
    disableBounceAnimation: Boolean = false
) {
    val buttonColor =
        if (enabled && !loading) colors.containerColor else colors.containerColor.copy(alpha = Percent70)
    val contentColor = if (enabled) colors.contentColor else colors.contentColor.copy(alpha = Percent70)
    val appliedShape = RoundedCornerShape(
        topStart = buttonShape.topStart,
        topEnd = buttonShape.topEnd,
        bottomStart = buttonShape.bottomStart,
        bottomEnd = buttonShape.bottomEnd
    )
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(Size48)
            .applyIf(!disableBounceAnimation && enabled && !loading) {
                bounceClick()
            }
            .clip(appliedShape)
            .clickable {
                if (enabled && !loading) {
                    onClick()
                }
            }
            .applyIf(buttonStyle == ButtonStyle.OUTLINE) {
                border(
                    width = Size2,
                    color = buttonColor,
                    shape = appliedShape
                )
            },
        color = when (buttonStyle) {
            ButtonStyle.REGULAR -> buttonColor
            ButtonStyle.OUTLINE -> Color.Transparent
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
                imageVector?.let { safeImageVector ->
                    Icon(
                        modifier = Modifier
                            .padding(end = Size4)
                            .size(Size16),
                        imageVector = safeImageVector,
                        contentDescription = stringResource(Res.string.cd_app_logo),
                        tint = if (enabled) colors.contentColor else colors.contentColor.copy(
                            alpha = Percent70
                        )
                    )
                }
                Text(
                    text = text,
                    color = when(buttonStyle) {
                        ButtonStyle.REGULAR -> contentColor
                        ButtonStyle.OUTLINE -> buttonColor
                    },
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }
    }
}