package com.thejohnsondev.ui.components.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.thejohnsondev.common.PASSWORD_IDLE_ITEM_HEIGHT
import com.thejohnsondev.ui.components.ShimmerEffect
import com.thejohnsondev.ui.designsystem.EqualRounded
import com.thejohnsondev.ui.designsystem.Percent50
import com.thejohnsondev.ui.designsystem.Size10
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.Size80
import com.thejohnsondev.ui.utils.isCompact

@Composable
fun VaultLoading(
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues
) {
    val topPadding = paddingValues.calculateTopPadding()
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(top = topPadding),
    ) {
        ShimmerEffect(
            modifier = Modifier
                .fillMaxWidth()
                .height(Size80)
                .padding(start = Size10, end = Size10, top = Size8, bottom = Size16)
                .clip(EqualRounded.large)
        )
        for (i in 0..10) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(PASSWORD_IDLE_ITEM_HEIGHT.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                if (windowSizeClass.isCompact()) {
                    ShimmerEffect(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(PASSWORD_IDLE_ITEM_HEIGHT.dp)
                            .padding(start = Size8, bottom = Size8, end = Size8)
                            .clip(EqualRounded.medium),
                    )
                } else {
                    for (j in 0..1) {
                        ShimmerEffect(
                            modifier = Modifier
                                .weight(Percent50)
                                .height(PASSWORD_IDLE_ITEM_HEIGHT.dp)
                                .padding(start = Size10, bottom = Size8, end = Size10)
                                .clip(EqualRounded.medium),
                        )
                    }
                }
            }
        }
    }
}