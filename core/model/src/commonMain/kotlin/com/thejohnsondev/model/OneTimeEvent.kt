package com.thejohnsondev.model

open class OneTimeEvent {
    object None: OneTimeEvent()
    class InfoMessage(val message: String): OneTimeEvent()
    class SuccessNavigation(val message: String? = null) : OneTimeEvent()
}
