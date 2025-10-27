package com.thejohnsondev.landing.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
            for (i in 0..50) {
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
    }
}