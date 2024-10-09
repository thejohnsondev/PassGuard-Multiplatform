package org.thejohnsondev.vault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import org.koin.android.ext.android.getKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val deviceThemeConfig: DeviceThemeConfig = getKoin().get()
        setContent {
            Root(deviceThemeConfig)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    Root(
        DeviceThemeConfig(LocalContext.current)
    )
}