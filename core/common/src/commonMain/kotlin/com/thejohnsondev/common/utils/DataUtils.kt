package com.thejohnsondev.common.utils

import com.thejohnsondev.common.PASS_MIN_SIZE
import com.thejohnsondev.model.validation.EmailIncorrectReason
import com.thejohnsondev.model.validation.EmailValidationState
import com.thejohnsondev.model.validation.IncorrectPasswordReason
import com.thejohnsondev.model.validation.PasswordValidationState
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement


fun String.isPasswordValid(): PasswordValidationState {
    val length = this.length
    if (length < PASS_MIN_SIZE) return PasswordValidationState.PasswordIncorrectState(
        IncorrectPasswordReason.BAD_LENGTH
    )

    val containsNumbers = this.any { it.isDigit() }
    if (!containsNumbers) return PasswordValidationState.PasswordIncorrectState(
        IncorrectPasswordReason.NO_NUMBERS
    )

    val containsUpperCase = this.any { it.isUpperCase() }
    if (!containsUpperCase) return PasswordValidationState.PasswordIncorrectState(
        IncorrectPasswordReason.NO_CAPITAL
    )

    val containsLowerCase = this.any { it.isLowerCase() }
    if (!containsLowerCase) return PasswordValidationState.PasswordIncorrectState(
        IncorrectPasswordReason.NO_SMALL
    )

    return PasswordValidationState.PasswordCorrectState
}

fun String.isEmailValid(): EmailValidationState {
    return if (this.isNotEmpty() && Regex(getEmailPattern()).matches(this)) {
        EmailValidationState.EmailCorrectState
    } else {
        EmailValidationState.EmailIncorrectState(EmailIncorrectReason.INCORRECT)
    }
}

private fun getEmailPattern() =
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"


inline fun <reified T> T.toJson(): String {
    return Json.encodeToString(this)
}

inline fun <reified T> String?.fromJson(): T {
    return Json.decodeFromJsonElement(Json.parseToJsonElement(this ?: ""))
}

fun String.hidden(): String {
    return replace(Regex("[\\s\\S]"), "*")
}

fun getPrettyErrorMessage(error: String?): String {
    return error ?: "Unknown error"
}