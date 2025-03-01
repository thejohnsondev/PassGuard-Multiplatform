package com.thejohnsondev.model

open class OneTimeEvent {
    data object None: OneTimeEvent()
    data class SuccessMessage(val message: DisplayableMessageValue): OneTimeEvent()
    data class InfoMessage(val message: DisplayableMessageValue): OneTimeEvent()
    data class ErrorMessage(val message: DisplayableMessageValue): OneTimeEvent()
    data class SuccessNavigation(val message: DisplayableMessageValue? = null) : OneTimeEvent()
}
