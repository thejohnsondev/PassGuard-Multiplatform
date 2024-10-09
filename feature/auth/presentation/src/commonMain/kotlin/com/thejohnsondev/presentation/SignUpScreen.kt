package com.thejohnsondev.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.thejohnsondev.ui.designsystem.getUbuntuFontFamily

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Text", fontFamily = getUbuntuFontFamily(), fontWeight = FontWeight.Bold)
        }
    }
}