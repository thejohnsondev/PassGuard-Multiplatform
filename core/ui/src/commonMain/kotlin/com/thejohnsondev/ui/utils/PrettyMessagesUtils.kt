package com.thejohnsondev.ui.utils

import androidx.compose.runtime.Composable
import com.thejohnsondev.model.validation.EmailIncorrectReason
import com.thejohnsondev.model.validation.IncorrectPasswordReason
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.core.ui.generated.resources.Res
import vaultmultiplatform.core.ui.generated.resources.email_error_incorrect
import vaultmultiplatform.core.ui.generated.resources.password_error_bad_length
import vaultmultiplatform.core.ui.generated.resources.password_error_no_capital
import vaultmultiplatform.core.ui.generated.resources.password_error_no_numbers

@Composable
fun getPasswordErrorMessage(reason: IncorrectPasswordReason): String {
    return when(reason) {
        IncorrectPasswordReason.BAD_LENGTH -> stringResource(Res.string.password_error_bad_length)
        IncorrectPasswordReason.NO_NUMBERS -> stringResource(Res.string.password_error_no_numbers)
        IncorrectPasswordReason.NO_CAPITAL -> stringResource(Res.string.password_error_no_capital)
        IncorrectPasswordReason.NO_SMALL -> stringResource(Res.string.password_error_no_capital)
    }

}

@Composable
fun getEmailErrorMessage(reason: EmailIncorrectReason): String {
    return when(reason) {
        EmailIncorrectReason.INCORRECT -> stringResource(Res.string.email_error_incorrect)
    }
}