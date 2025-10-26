package com.thejohnsondev.landing.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thejohnsondev.ui.utils.testBorder

@Composable
fun HomeScreen() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column {
            for (i in 0..50) {
                Box(
                    modifier = Modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .testBorder(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Item #$i", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}