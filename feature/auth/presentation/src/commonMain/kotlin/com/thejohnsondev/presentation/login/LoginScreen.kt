package com.thejohnsondev.presentation.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.thejohnsondev.common.EMPTY
import com.thejohnsondev.model.LoadingState
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.validation.EmailValidationState
import com.thejohnsondev.model.validation.PasswordValidationState
import com.thejohnsondev.ui.components.ErrorSnackbar
import com.thejohnsondev.ui.components.GlowPulsingBackground
import com.thejohnsondev.ui.components.RoundedButton
import com.thejohnsondev.ui.components.TextField
import com.thejohnsondev.ui.components.VaultLogo
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size24
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size580
import com.thejohnsondev.ui.designsystem.Size600
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.Size86
import com.thejohnsondev.ui.designsystem.isLight
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.getEmailErrorMessage
import com.thejohnsondev.ui.utils.getPasswordErrorMessage
import com.thejohnsondev.ui.utils.isCompact
import com.thejohnsondev.ui.utils.keyboardAsState
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.feature.auth.presentation.generated.resources.Res
import vaultmultiplatform.feature.auth.presentation.generated.resources.do_not_have_account
import vaultmultiplatform.feature.auth.presentation.generated.resources.email
import vaultmultiplatform.feature.auth.presentation.generated.resources.log_in
import vaultmultiplatform.feature.auth.presentation.generated.resources.password
import vaultmultiplatform.feature.auth.presentation.generated.resources.sign_up

@Composable
fun LoginScreen(
    windowSize: WindowWidthSizeClass,
    viewModel: LoginViewModel,
    goToHome: () -> Unit,
    goToSignUp: () -> Unit
) {
    val screenState = viewModel.viewState.collectAsState(LoginViewModel.State())
    val emailState = rememberSaveable {
        mutableStateOf(EMPTY)
    }
    val passwordState = rememberSaveable {
        mutableStateOf(EMPTY)
    }
    val emailFocusRequest = remember { FocusRequester() }
    val passwordFocusRequest = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val isKeyboardOpened by keyboardAsState()

    LaunchedEffect(true) {
        viewModel.getEventFlow().collect {
            when (it) {
                is OneTimeEvent.InfoMessage -> {
                    snackbarHostState.showSnackbar(it.message, duration = SnackbarDuration.Short)
                }

                is OneTimeEvent.SuccessNavigation -> goToHome()
            }
        }
    }


    LoginContent(
        state = screenState.value,
        windowSize = windowSize,
        isKeyboardOpened = isKeyboardOpened,
        emailState = emailState,
        passwordState = passwordState,
        emailFocusRequest = emailFocusRequest,
        passwordFocusRequest = passwordFocusRequest,
        snackbarHostState = snackbarHostState,
        onGoToSignUp = goToSignUp,
        hideKeyboard = {
            keyboardController?.hide()
        },
        onAction = viewModel::perform
    )

}

@Composable
fun LoginContent(
    state: LoginViewModel.State,
    windowSize: WindowWidthSizeClass,
    isKeyboardOpened: Boolean,
    emailState: MutableState<String>,
    passwordState: MutableState<String>,
    emailFocusRequest: FocusRequester,
    passwordFocusRequest: FocusRequester,
    snackbarHostState: SnackbarHostState,
    onGoToSignUp: () -> Unit,
    hideKeyboard: () -> Unit,
    onAction: (LoginViewModel.Action) -> Unit
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
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = if (MaterialTheme.colorScheme.isLight()) {
                Color.White
            } else {
                Color.Black
            }
        ) {
            Box {
                Box {
                    GlowPulsingBackground()
                }
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .scrollable(rememberScrollState(), Orientation.Vertical)
                        .applyIf(!windowSize.isCompact()) {
                            Modifier
                                .width(Size600)
                                .padding(bottom = Size16)
                                .align(Alignment.Center)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedVisibility(
                        visible = !isKeyboardOpened
                    ) {
                        Column {
                            LogoSection()
                        }
                    }
                    FieldsSection(
                        screenState = state,
                        emailState = emailState,
                        passwordState = passwordState,
                        emailFocusRequest = emailFocusRequest,
                        passwordFocusRequest = passwordFocusRequest,
                        onGoToSignUp = onGoToSignUp,
                        hideKeyboard = hideKeyboard,
                        onAction = onAction
                    )
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(
                            bottom = if (!windowSize.isCompact()) Size16 else paddingValues.calculateBottomPadding(),
                            start = Size8,
                            end = Size8
                        )
                        .clip(RoundedCornerShape(Size24))
                ) {
                    LoginButtonSection(
                        screenState = state,
                        windowSize = windowSize,
                        emailState = emailState,
                        passwordState = passwordState,
                        hideKeyboard = hideKeyboard,
                        onAction = onAction
                    )
                }
            }
        }
    }
}

@Composable
fun FieldsSection(
    screenState: LoginViewModel.State,
    emailState: MutableState<String>,
    passwordState: MutableState<String>,
    emailFocusRequest: FocusRequester,
    passwordFocusRequest: FocusRequester,
    onGoToSignUp: () -> Unit,
    hideKeyboard: () -> Unit,
    onAction: (LoginViewModel.Action) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = Size8),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(Size24)
    ) {
        Column {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(Size16),
                text = stringResource(Res.string.log_in),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(Size8))
            TextField(
                modifier = Modifier
                    .focusRequester(emailFocusRequest)
                    .padding(horizontal = Size16),
                textState = emailState,
                onTextChanged = {
                    emailState.value = it
                    onAction(LoginViewModel.Action.ValidateEmail(it))
                },
                label = stringResource(Res.string.email),
                onKeyboardAction = KeyboardActions {
                    passwordFocusRequest.requestFocus()

                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email,
                isError = screenState.emailValidationState !is EmailValidationState.EmailCorrectState,
                errorText = if (screenState.emailValidationState is EmailValidationState.EmailIncorrectState) getEmailErrorMessage(
                    screenState.emailValidationState.reason
                )
                else null
            )
            Spacer(modifier = Modifier.height(Size8))
            TextField(
                modifier = Modifier
                    .focusRequester(passwordFocusRequest)
                    .padding(horizontal = Size16),
                textState = passwordState,
                onTextChanged = {
                    passwordState.value = it
                    onAction(LoginViewModel.Action.ValidatePassword(it))
                },
                label = stringResource(Res.string.password),
                imeAction = ImeAction.Done,
                onKeyboardAction = KeyboardActions {
                    hideKeyboard()
                },
                keyboardType = KeyboardType.Password,
                isError = screenState.passwordValidationState !is PasswordValidationState.PasswordCorrectState,
                errorText = if (screenState.passwordValidationState is PasswordValidationState.PasswordIncorrectState) getPasswordErrorMessage(
                    screenState.passwordValidationState.reason
                )
                else null
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(Size16)
            ) {
                Text(
                    text = stringResource(Res.string.do_not_have_account),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(Res.string.sign_up),
                    modifier = Modifier
                        .padding(start = Size4)
                        .clickable {
                            onGoToSignUp()
                        },
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun LoginButtonSection(
    screenState: LoginViewModel.State,
    windowSize: WindowWidthSizeClass,
    emailState: MutableState<String>,
    passwordState: MutableState<String>,
    hideKeyboard: () -> Unit,
    onAction: (LoginViewModel.Action) -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .applyIf(!windowSize.isCompact()) {
                Modifier.width(Size580)
            },
        verticalArrangement = Arrangement.Bottom
    ) {
        RoundedButton(
            text = stringResource(Res.string.log_in),
            modifier = Modifier.padding(horizontal = Size16, vertical = Size16),
            enabled = screenState.loginReady,
            onClick = {
                hideKeyboard()
                onAction(
                    LoginViewModel.Action.LoginWithEmail(
                        emailState.value,
                        passwordState.value
                    )
                )
            },
            loading = screenState.loadingState is LoadingState.Loading
        )
    }
}

@Composable
fun LogoSection() {
    VaultLogo(modifier = Modifier.padding(Size24), MaterialTheme.typography.displaySmall.fontSize)
}