package com.thejohnsondev.model

sealed interface DisplayableMessageValue {
    data object UnknownError : DisplayableMessageValue
    data class StringValue(val value: String) : DisplayableMessageValue
    data object PasswordEditSuccess : DisplayableMessageValue
    data object PasswordAddedSuccess : DisplayableMessageValue
    data object BadLength : DisplayableMessageValue
    data object NoNumbers : DisplayableMessageValue
    data object NoCapital : DisplayableMessageValue
    data object NoSmall : DisplayableMessageValue
    data object EmailInvalid : DisplayableMessageValue
    data object CheckInternetConnection : DisplayableMessageValue
}