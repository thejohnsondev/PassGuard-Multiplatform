package com.thejohnsondev.landing.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thejohnsondev.ui.components.VaultLogo
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size128
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size64
import com.thejohnsondev.ui.designsystem.Size80
import com.thejohnsondev.ui.utils.ResDrawable
import org.jetbrains.compose.resources.painterResource
import vaultmultiplatform.core.ui.generated.resources.ic_shield_outline
import vaultmultiplatform.core.ui.generated.resources.ic_vault_108_gradient

@Composable
fun HomeScreen(
    paddingValues: PaddingValues = PaddingValues()
) {
    Surface(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .wrapContentHeight(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column {
            for (i in 0..10) {
                Box(
                    modifier = Modifier
                        .height(400.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(height = 400.dp, width = 800.dp)
                            .background(
                                listOf(
                                    Color.Red,
                                    Color.Green,
                                    Color.Blue,
                                    Color.Yellow,
                                ).random().copy(alpha = 0.7f)
                            )
                    )
                    Text("Item #$i", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TitleContent()
        }
    }
}

@Composable
private fun TitleContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .padding(top = Size64),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(ResDrawable.ic_vault_108_gradient),
                contentDescription = "Logo",
                modifier = Modifier.size(Size80)
            )
            VaultLogo(
                modifier = Modifier
                    .padding(start = Size16)
            )
        }
        Text(
            modifier = Modifier
                .padding(top = Size64),
            text = "A simple, secure and easy to use \npassword manager.",
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 42.sp,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            lineHeight = 46.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            modifier = Modifier
                .padding(top = Size12),
            text = "The only password manager you will ever need.",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1000.dp)
        )
    }
}

    }
}