package com.thejohnsondev.presentation.check_auth_state

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavOptions
import com.thejohnsondev.common.navigation.Screens
import com.thejohnsondev.ui.components.Loader

@Composable
fun CheckAuthStateScreen(
    viewModel: CheckAuthStateViewModel,
    goToScreen: (String, NavOptions) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Loader()
    }
    val firstScreenRoute = viewModel.firstScreenRoute.collectAsState()
    firstScreenRoute.value ?: return
    goToScreen(
        firstScreenRoute.value!!,
        NavOptions.Builder()
            .setPopUpTo(Screens.CheckAuthScreen.name, true)
            .build()
    )
}