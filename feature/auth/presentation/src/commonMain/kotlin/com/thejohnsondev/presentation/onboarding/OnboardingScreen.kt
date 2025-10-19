package com.thejohnsondev.presentation.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.lerp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thejohnsondev.analytics.Analytics
import com.thejohnsondev.common.model.ScreenState.Companion.isLoading
import com.thejohnsondev.ui.components.button.BackArrowButton
import com.thejohnsondev.ui.components.button.RoundedButton
import com.thejohnsondev.ui.components.text.PRIVACY_POLICY_TAG
import com.thejohnsondev.ui.components.text.PrivacyPolicyAcceptText
import com.thejohnsondev.ui.components.text.TERMS_OF_USE_TAG
import com.thejohnsondev.ui.designsystem.EquallyRounded
import com.thejohnsondev.ui.designsystem.Percent100
import com.thejohnsondev.ui.designsystem.Percent50
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size24
import com.thejohnsondev.ui.designsystem.Size300
import com.thejohnsondev.ui.designsystem.Size32
import com.thejohnsondev.ui.designsystem.Size36
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size580
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.animateItemParallaxFromEndToCenter
import com.thejohnsondev.ui.utils.animateItemToBackgroundWithBlur
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.isCompact
import com.thejohnsondev.ui.utils.padding
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import vaultmultiplatform.core.ui.generated.resources.btn_next
import vaultmultiplatform.core.ui.generated.resources.onboarding_create_vault
import vaultmultiplatform.core.ui.generated.resources.privacy_policy_link
import vaultmultiplatform.core.ui.generated.resources.terms_of_use_link
import kotlin.math.absoluteValue

private const val SLIDE_ANIMATION_DURATION = 500

@Composable
fun OnboardingScreen(
    windowWidthSizeClass: WindowWidthSizeClass,
    viewModel: OnboardingViewModel,
    goToSelectVaultType: () -> Unit,
    goToHome: () -> Unit,
    goBack: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        Analytics.trackScreen("onboarding_screen")
    }

    LaunchedEffect(Unit) {
        viewModel.getEventFlow().collect { event ->
            when (event) {
                is OnboardingViewModel.NavigateToHome -> goToHome()
            }
        }
    }

    Content(
        windowSize = windowWidthSizeClass,
        state = state.value,
        onAction = viewModel::perform,
        goBack = goBack
    )
}

@Composable
private fun Content(
    windowSize: WindowWidthSizeClass,
    state: OnboardingViewModel.State,
    onAction: (OnboardingViewModel.Action) -> Unit,
    goBack: () -> Unit
) {
    val pages = remember {
        OnboardingPageModel.pages
    }
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { pages.size })
    Scaffold { paddingValues ->
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Size16),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                HorizontalPager(
                    modifier = Modifier
                        .weight(Percent100)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    state = pagerState,
                    snapPosition = SnapPosition.Center,
                    beyondViewportPageCount = 1
                ) { page ->
                    OnBoardItem(pages[page], pagerState)
                }
                OnboardingDotsIndicator(
                    pagerState = pagerState,
                    pageCount = pages.size
                )
                NextButtonSection(
                    windowSize = windowSize,
                    paddingValues = paddingValues,
                    state = state,
                    onAction = onAction,
                    pagerState = pagerState,
                    pages = pages
                )
            }
            BackArrowButton(
                modifier = Modifier
                    .padding(
                        start = Size16, top = Size16
                    )
                    .statusBarsPadding(),
                onClick = goBack
            )
        }
    }
}

@Composable
fun OnBoardItem(page: OnboardingPageModel, pagerState: PagerState) {
    Column(
        modifier = Modifier.fillMaxSize()
            .animateItemToBackgroundWithBlur(
                pageNumber = page.pageNumber,
                pagerState = pagerState
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        page.imageRes?.let {
            Image(
                imageVector = vectorResource(page.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(Size300)
                    .animateItemParallaxFromEndToCenter(
                        pageNumber = page.pageNumber,
                        pagerState = pagerState,
                        parallaxFactor = 10f,
                        applyScaleAnimation = true
                    )
            )
        }
        Text(
            modifier = Modifier
                .padding(top = Size24)
                .animateItemParallaxFromEndToCenter(
                    pageNumber = page.pageNumber,
                    pagerState = pagerState,
                    parallaxFactor = 6f
                ),
            text = stringResource(page.titleStringRes),
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .padding(horizontal = Size16, top = Size16)
                .animateItemParallaxFromEndToCenter(
                    pageNumber = page.pageNumber,
                    pagerState = pagerState,
                    parallaxFactor = 8f
                ),
            text = stringResource(page.descriptionStringRes),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun OnboardingDotsIndicator(
    pagerState: PagerState,
    pageCount: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Size36),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pageCount) { index ->
                val pageOffset = (
                        (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction).absoluteValue
                val dotWidth =
                    lerp(start = Size8, stop = Size32, fraction = 1f - pageOffset.coerceIn(0f, 1f))
                val dotHeight =
                    lerp(start = Size8, stop = Size16, fraction = 1f - pageOffset.coerceIn(0f, 1f))

                Box(
                    modifier = Modifier
                        .padding(Size4)
                        .width(dotWidth)
                        .height(dotHeight)
                        .background(
                            color = if (pageOffset < Percent50)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.secondary,
                            shape = EquallyRounded.extraLarge
                        )
                )
            }
        }
    }
}

@Composable
private fun NextButtonSection(
    state: OnboardingViewModel.State,
    paddingValues: PaddingValues,
    windowSize: WindowWidthSizeClass,
    onAction: (OnboardingViewModel.Action) -> Unit,
    pagerState: PagerState,
    pages: List<OnboardingPageModel>
) {
    val isLastPage = pagerState.currentPage == pages.size - 1
    val coroutineScope = rememberCoroutineScope()
    val text = PrivacyPolicyAcceptText()
    val uriHandler = LocalUriHandler.current
    val privacyPolicyUrl = stringResource(ResString.privacy_policy_link)
    val termsOfUseUrl = stringResource(ResString.terms_of_use_link)
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(isLastPage) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        modifier = Modifier
                            .semantics {
                                contentDescription = "Accept Privacy Policy"
                            },
                        checked = state.isPrivacyPolicyAccepted,
                        onCheckedChange = {
                            onAction(OnboardingViewModel.Action.AcceptPrivacyPolicy(it))
                        }
                    )
                    ClickableText(text = text) { offset ->
                        text.getStringAnnotations(
                            tag = PRIVACY_POLICY_TAG,
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let {
                            uriHandler.openUri(privacyPolicyUrl)
                        }
                        text.getStringAnnotations(
                            tag = TERMS_OF_USE_TAG,
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let {
                            uriHandler.openUri(termsOfUseUrl)
                        }
                    }
                }
            }
            RoundedButton(
                modifier = Modifier
                    .padding(bottom = paddingValues.calculateBottomPadding())
                    .applyIf(windowSize.isCompact()) {
                        Modifier.fillMaxWidth()
                    }
                    .applyIf(!windowSize.isCompact()) {
                        Modifier.width(Size580)
                    }
                    .padding(Size16),
                text = stringResource(
                    if (isLastPage) ResString.onboarding_create_vault else ResString.btn_next
                ),
                onClick = {
                    if (!isLastPage) {
                        val nextPage = pagerState.currentPage + 1
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(
                                page = nextPage,
                                animationSpec = tween(
                                    durationMillis = SLIDE_ANIMATION_DURATION
                                )
                            )
                        }
                    } else {
                        onAction(OnboardingViewModel.Action.CreateLocalVault)
                    }
                },
                enabled = !isLastPage || (isLastPage && state.isPrivacyPolicyAccepted),
                loading = state.screenState.isLoading(),
            )
        }
    }
}