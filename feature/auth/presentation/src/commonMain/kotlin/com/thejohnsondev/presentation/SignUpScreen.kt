package com.thejohnsondev.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import com.thejohnsondev.common.EMPTY
import com.thejohnsondev.ui.components.VaultLogo
import com.thejohnsondev.ui.designsystem.Size24
import com.thejohnsondev.ui.utils.keyboardAsState
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.feature.auth.presentation.generated.resources.Res
import vaultmultiplatform.feature.auth.presentation.generated.resources.privacy_policy_link
import vaultmultiplatform.feature.auth.presentation.generated.resources.terms_of_use_link

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel
) {
    val screenState = signUpViewModel.viewState.collectAsState(SignUpViewModel.State())
    val uriHandler = LocalUriHandler.current
    val privacyPolicyUrl = stringResource(Res.string.privacy_policy_link)
    val termsOfUseUrl = stringResource(Res.string.terms_of_use_link)
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

    // TODO continue with the screen content

}

@Composable
fun LogoSection() {
    VaultLogo(modifier = Modifier.padding(Size24), MaterialTheme.typography.displaySmall.fontSize)
}