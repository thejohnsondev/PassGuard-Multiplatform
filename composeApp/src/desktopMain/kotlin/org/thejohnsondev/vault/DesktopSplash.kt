package org.thejohnsondev.vault

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.utils.ResDrawable
import org.jetbrains.compose.resources.vectorResource
import vaultmultiplatform.core.ui.generated.resources.ic_vault_108_gradient

private const val LOGO_SCALE = 1.4f

@Composable
fun DesktopSplash() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Box {
            Image(
                modifier = Modifier.wrapContentSize()
                    .align(Alignment.Center)
                    .scale(LOGO_SCALE),
                imageVector = vectorResource(ResDrawable.ic_vault_108_gradient),
                contentDescription = null // TODO add content description,
            )
        }
    }
}