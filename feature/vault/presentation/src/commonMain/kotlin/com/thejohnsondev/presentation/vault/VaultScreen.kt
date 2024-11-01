package com.thejohnsondev.presentation.vault

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun VaultScreen(
    viewModel: VaultViewModel,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Vault Screen")
        Button(
            onClick = {
                viewModel.logout()
                onClick()
            },
        ) {
            Text("Logout")
        }

    }
}