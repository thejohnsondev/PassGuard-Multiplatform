package com.thejohnsondev.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.thejohnsondev.ui.designsystem.EquallyRounded
import com.thejohnsondev.ui.designsystem.Percent100
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size20
import com.thejohnsondev.ui.designsystem.Size360
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.SizeBorder
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.padding
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import vaultmultiplatform.core.ui.generated.resources.btn_next
import vaultmultiplatform.core.ui.generated.resources.onboarding_create_vault

@Composable
fun OnboardingScreen(
    windowWidthSizeClass: WindowWidthSizeClass,
    goToSelectVaultType: () -> Unit,
    goToHome: () -> Unit,
) {
    val pages = remember {
        OnboardingPageModel.pages
    }
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { pages.size })
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Size16),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(Percent100)
                    .fillMaxWidth(),
                snapPosition = SnapPosition.Center,
                verticalAlignment = Alignment.CenterVertically
            ) { page ->
                OnBoardItem(pages[page])
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val isLastPage = pagerState.currentPage == pages.size - 1
                Button(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    content = {
                        Text(
                            text = stringResource(
                                if (isLastPage) ResString.onboarding_create_vault else ResString.btn_next
                            )
                        )
                    },
                    onClick = {
                        if (!isLastPage) {
                            val nextPage = pagerState.currentPage + 1
                            coroutineScope.launch { pagerState.animateScrollToPage(nextPage) }
                        }
                    }
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = Size20)
                        .navigationBarsPadding(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(pages.size) { index ->
                        val isSelected = pagerState.currentPage == index
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .width(if (isSelected) Size20 else Size8)
                                .height(if (isSelected) Size16 else Size8)
                                .border(
                                    width = SizeBorder,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    shape = EquallyRounded.medium
                                )
                                .background(
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                                    shape = EquallyRounded.extraLarge
                                )
                        )
                    }
                }
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
        page.imageRes?.let {
            Image(
                imageVector = vectorResource(page.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(Size360)
                    .padding(bottom = Size20)
            )
        }
        Text(
            text = stringResource(page.titleStringRes),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(page.descriptionStringRes),
            modifier = Modifier.padding(horizontal = Size16, vertical = Size8),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}