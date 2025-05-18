package com.thejohnsondev.ui.displaymessage

import androidx.compose.runtime.Composable
import com.thejohnsondev.model.DisplayableMessageValue
import com.thejohnsondev.ui.utils.ResString
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.core.ui.generated.resources.added_success
import vaultmultiplatform.core.ui.generated.resources.check_internet_connection
import vaultmultiplatform.core.ui.generated.resources.copied
import vaultmultiplatform.core.ui.generated.resources.email_error_incorrect
import vaultmultiplatform.core.ui.generated.resources.export_message_successful
import vaultmultiplatform.core.ui.generated.resources.export_message_unsuccessful
import vaultmultiplatform.core.ui.generated.resources.password_error_bad_length
import vaultmultiplatform.core.ui.generated.resources.password_error_no_capital
import vaultmultiplatform.core.ui.generated.resources.password_error_no_numbers
import vaultmultiplatform.core.ui.generated.resources.password_error_no_small
import vaultmultiplatform.core.ui.generated.resources.stub
import vaultmultiplatform.core.ui.generated.resources.unknown_error
import vaultmultiplatform.core.ui.generated.resources.updated_success

@Composable
fun DisplayableMessageValue.getAsComposeText(): String {
    if (this is DisplayableMessageValue.StringValue) {
        return this.value
    }
    val stringResource = getStringResource(this)
    return stringResource(stringResource)
}

suspend fun DisplayableMessageValue.getAsText(): String {
    if (this is DisplayableMessageValue.StringValue) {
        return this.value
    }
    val stringResource = getStringResource(this)
    return getString(stringResource)
}

internal fun getStringResource(value: DisplayableMessageValue): StringResource {
    return when (value) {
        is DisplayableMessageValue.StringValue -> ResString.stub
        is DisplayableMessageValue.UnknownError -> ResString.unknown_error
        is DisplayableMessageValue.PasswordEditSuccess -> ResString.updated_success
        is DisplayableMessageValue.BadLength -> ResString.password_error_bad_length
        is DisplayableMessageValue.NoNumbers -> ResString.password_error_no_numbers
        is DisplayableMessageValue.NoCapital -> ResString.password_error_no_capital
        is DisplayableMessageValue.NoSmall -> ResString.password_error_no_small
        is DisplayableMessageValue.EmailInvalid -> ResString.email_error_incorrect
        is DisplayableMessageValue.CheckInternetConnection -> ResString.check_internet_connection
        is DisplayableMessageValue.PasswordAddedSuccess -> ResString.added_success
        is DisplayableMessageValue.Copied -> ResString.copied
        is DisplayableMessageValue.ExportSuccessful -> ResString.export_message_successful
        is DisplayableMessageValue.ExportUnsuccessful -> ResString.export_message_unsuccessful
    }
}