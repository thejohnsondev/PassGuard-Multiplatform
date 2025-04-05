package com.thejohnsondev.presentation.signup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.thejohnsondev.common.empty
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.model.validation.EmailValidationState
import com.thejohnsondev.model.validation.PasswordValidationState
import com.thejohnsondev.ui.components.button.BackArrowButton
import com.thejohnsondev.ui.components.ErrorSnackbar
import com.thejohnsondev.ui.components.animation.GlowPulsingBackground
import com.thejohnsondev.ui.components.text.PRIVACY_POLICY_TAG
import com.thejohnsondev.ui.components.text.PrivacyPolicyAcceptText
import com.thejohnsondev.ui.components.button.RoundedButton
import com.thejohnsondev.ui.components.text.TERMS_OF_USE_TAG
import com.thejohnsondev.ui.components.text.PrimaryOutlinedTextField
import com.thejohnsondev.ui.components.VaultLogo
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size2
import com.thejohnsondev.ui.designsystem.Size24
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size580
import com.thejohnsondev.ui.designsystem.Size600
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.Size86
import com.thejohnsondev.ui.designsystem.colorscheme.isLight
import com.thejohnsondev.ui.displaymessage.getAsComposeText
import com.thejohnsondev.ui.displaymessage.getAsText
import com.thejohnsondev.ui.utils.KeyboardManager
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.isCompact
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.core.ui.generated.resources.already_have_an_account
import vaultmultiplatform.core.ui.generated.resources.email
import vaultmultiplatform.core.ui.generated.resources.log_in
import vaultmultiplatform.core.ui.generated.resources.password
import vaultmultiplatform.core.ui.generated.resources.privacy_policy_link
import vaultmultiplatform.core.ui.generated.resources.sign_up
import vaultmultiplatform.core.ui.generated.resources.terms_of_use_link

@Composable
fun SignUpScreen(
    windowSize: WindowWidthSizeClass,
    viewModel: SignUpViewModel,
    goToHome: () -> Unit,
    goToLogin: () -> Unit,
    goBack: () -> Unit,
) {
    val screenState = viewModel.viewState.collectAsState(SignUpViewModel.State())
    val uriHandler = LocalUriHandler.current
    val privacyPolicyUrl = stringResource(ResString.privacy_policy_link)
    val termsOfUseUrl = stringResource(ResString.terms_of_use_link)
    val emailState = rememberSaveable {
        mutableStateOf(String.empty)
    }
    val passwordState = rememberSaveable {
        mutableStateOf(String.empty)
    }
    val emailFocusRequest = remember { FocusRequester() }
    val passwordFocusRequest = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val isKeyboardOpened by KeyboardManager.keyboardAsState()

    LaunchedEffect(true) {
        viewModel.getEventFlow().collect {
            when (it) {
                is OneTimeEvent.ErrorMessage -> {
                    snackbarHostState.showSnackbar(
                        it.message.getAsText(),
                        duration = SnackbarDuration.Short
                    )
                }

                is OneTimeEvent.SuccessNavigation -> goToHome()
            }
        }
    }

    SignUpContent(
        state = screenState.value,
        windowSize = windowSize,
        isKeyboardOpened = isKeyboardOpened,
        emailState = emailState,
        passwordState = passwordState,
        emailFocusRequest = emailFocusRequest,
        passwordFocusRequest = passwordFocusRequest,
        snackbarHostState = snackbarHostState,
        onGoToLogin = goToLogin,
        onGoBack = goBack,
        hideKeyboard = {
            keyboardController?.hide()
        },
        openPrivacyPolicy = {
            uriHandler.openUri(privacyPolicyUrl)
        },
        openTermsOfUse = {
            uriHandler.openUri(termsOfUseUrl)
        },
        onAction = viewModel::perform
    )
}

@Composable
fun SignUpContent(
    state: SignUpViewModel.State,
    windowSize: WindowWidthSizeClass,
    isKeyboardOpened: Boolean,
    emailState: MutableState<String>,
    passwordState: MutableState<String>,
    emailFocusRequest: FocusRequester,
    passwordFocusRequest: FocusRequester,
    snackbarHostState: SnackbarHostState,
    onGoToLogin: () -> Unit,
    onGoBack: () -> Unit,
    hideKeyboard: () -> Unit,
    openPrivacyPolicy: () -> Unit,
    openTermsOfUse: () -> Unit,
    onAction: (SignUpViewModel.Action) -> Unit,
) {
    Scaffold(snackbarHost = {
        SnackbarHost(
            snackbarHostState
        ) { data ->
            ErrorSnackbar(
                modifier = Modifier.padding(bottom = Size86, start = Size16, end = Size16),
                message = data.visuals.message
            )
        }
    }) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize()
                .background(
                    if (MaterialTheme.colorScheme.isLight()) {
                        Color.White
                    } else {
                        Color.Black
                    }
                )
        ) {
            GlowPulsingBackground()
            FieldsSection(
                largeScreenModifier = Modifier
                    .width(Size600)
                    .padding(bottom = Size16)
                    .align(Alignment.Center),
                screenState = state,
                emailState = emailState,
                passwordState = passwordState,
                emailFocusRequest = emailFocusRequest,
                passwordFocusRequest = passwordFocusRequest,
                isKeyboardOpened = isKeyboardOpened,
                paddingValues = paddingValues,
                windowSize = windowSize,
                onGoToLogin = onGoToLogin,
                hideKeyboard = hideKeyboard,
                onAction = onAction
            )
            SignUpButtonSection(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        bottom = if (!windowSize.isCompact()) Size16 else paddingValues.calculateBottomPadding(),
                        start = Size8,
                        end = Size8
                    )
                    .clip(RoundedCornerShape(Size24))
                    .background(MaterialTheme.colorScheme.surface)
                    .applyIf(!windowSize.isCompact()) {
                        Modifier.width(Size580)
                    },
                state = state,
                windowSize = windowSize,
                emailState = emailState,
                passwordState = passwordState,
                hideKeyboard = hideKeyboard,
                onAction = onAction,
                openPrivacyPolicy = openPrivacyPolicy,
                openTermsOfUse = openTermsOfUse
            )
            AnimatedVisibility(!isKeyboardOpened) {
                BackArrowButton(
                    modifier = Modifier.padding(
                        start = Size16, top = paddingValues.calculateTopPadding().plus(
                            Size16
                        )
                    ),
                    onClick = onGoBack
                )
            }
        }
    }
}

@Composable
fun FieldsSection(
    largeScreenModifier: Modifier = Modifier,
    screenState: SignUpViewModel.State,
    emailState: MutableState<String>,
    passwordState: MutableState<String>,
    emailFocusRequest: FocusRequester,
    passwordFocusRequest: FocusRequester,
    isKeyboardOpened: Boolean,
    paddingValues: PaddingValues,
    windowSize: WindowWidthSizeClass,
    onGoToLogin: () -> Unit,
    hideKeyboard: () -> Unit,
    onAction: (SignUpViewModel.Action) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .scrollable(rememberScrollState(), Orientation.Vertical)
            .applyIf(!windowSize.isCompact()) {
                largeScreenModifier
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = !isKeyboardOpened
        ) {
            LogoSection()
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = Size8)
                .clip(RoundedCornerShape(Size24))
                .background(MaterialTheme.colorScheme.surface),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(Size16),
                text = stringResource(ResString.sign_up),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(Size8))
            PrimaryOutlinedTextField(
                modifier = Modifier
                    .focusRequester(emailFocusRequest)
                    .padding(horizontal = Size16),
                textState = emailState,
                onTextChanged = {
                    emailState.value = it
                    onAction(SignUpViewModel.Action.ValidateEmail(it))
                },
                label = stringResource(ResString.email),
                onKeyboardAction = KeyboardActions {
                    passwordFocusRequest.requestFocus()

                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email,
                isError = screenState.emailValidationState !is EmailValidationState.EmailCorrectState,
                errorText = if (screenState.emailValidationState is EmailValidationState.EmailIncorrectState) {
                    screenState.emailValidationState.reason.getAsComposeText()
                } else null
            )
            Spacer(modifier = Modifier.height(Size8))
            PrimaryOutlinedTextField(
                modifier = Modifier
                    .focusRequester(passwordFocusRequest)
                    .padding(horizontal = Size16),
                textState = passwordState,
                onTextChanged = {
                    passwordState.value = it
                    onAction(SignUpViewModel.Action.ValidatePassword(it))
                },
                label = stringResource(ResString.password),
                imeAction = ImeAction.Done,
                onKeyboardAction = KeyboardActions {
                    hideKeyboard()
                },
                keyboardType = KeyboardType.Password,
                isError = screenState.passwordValidationState !is PasswordValidationState.PasswordCorrectState,
                errorText = if (screenState.passwordValidationState is PasswordValidationState.PasswordIncorrectState) {
                    screenState.passwordValidationState.reason.getAsComposeText()
                } else null
            )
            AleradyHaveAccount(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(Size16),
                onGoToLogin = onGoToLogin
            )
        }
    }
}

@Composable
private fun AleradyHaveAccount(
    modifier: Modifier = Modifier,
    onGoToLogin: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = stringResource(ResString.already_have_an_account),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = stringResource(ResString.log_in),
            modifier = Modifier
                .padding(start = Size4)
                .clickable {
                    onGoToLogin()
                },
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun SignUpButtonSection(
    modifier: Modifier = Modifier,
    state: SignUpViewModel.State,
    windowSize: WindowWidthSizeClass,
    emailState: MutableState<String>,
    passwordState: MutableState<String>,
    hideKeyboard: () -> Unit,
    openPrivacyPolicy: () -> Unit,
    openTermsOfUse: () -> Unit,
    onAction: (SignUpViewModel.Action) -> Unit,
) {
    val text = PrivacyPolicyAcceptText()
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .applyIf(!windowSize.isCompact()) {
                Modifier.width(Size580)
            },
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = Size2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = state.isPrivacyPolicyAccepted,
                onCheckedChange = {
                    onAction(SignUpViewModel.Action.AcceptPrivacyPolicy(it))
                })
            ClickableText(text = text) { offset ->
                text.getStringAnnotations(
                    tag = PRIVACY_POLICY_TAG,
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    openPrivacyPolicy()
                }
                text.getStringAnnotations(
                    tag = TERMS_OF_USE_TAG,
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    openTermsOfUse()
                }
            }
        }
        RoundedButton(
            text = stringResource(ResString.sign_up),
            modifier = Modifier.padding(horizontal = Size16, vertical = Size16),
            enabled = state.signUpReady,
            onClick = {
                hideKeyboard()
                onAction(
                    SignUpViewModel.Action.SignUpWithEmail(
                        emailState.value,
                        passwordState.value
                    )
                )
            },
            loading = state.screenState is ScreenState.Loading
        )
    }
}

@Composable
fun LogoSection() {
    VaultLogo(modifier = Modifier.padding(Size16), MaterialTheme.typography.displaySmall.fontSize)
}