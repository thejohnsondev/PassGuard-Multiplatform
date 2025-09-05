package com.thejohnsondev.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.thejohnsondev.ui.designsystem.Percent100
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size20
import com.thejohnsondev.ui.designsystem.Size360
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.utils.padding
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun OnboardingScreen(
    windowWidthSizeClass: WindowWidthSizeClass,
    goToSelectVaultType: () -> Unit,
    goToHome: () -> Unit,
) {
    val pages = remember {
        OnboardingPageModel.pages
    }
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { pages.size })
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Size16)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(Percent100)
                    .fillMaxWidth()
            ) { page ->
                OnBoardItem(pages[page])
            }
        }
    }
}

@Composable
fun OnBoardItem(page: OnboardingPageModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = vectorResource(page.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(Size360)
                .padding(bottom = Size20)
        )
        Text(
            text = stringResource(page.titleStringRes),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(page.descriptionStringRes),
            modifier = Modifier.padding(horizontal = Size16, vertical = Size8),
            style = MaterialTheme.typography.titleMedium
        )
    }
}