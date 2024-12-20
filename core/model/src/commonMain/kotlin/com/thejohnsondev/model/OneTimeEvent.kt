package com.thejohnsondev.model

open class OneTimeEvent {
    object None: OneTimeEvent()
    class InfoMessage(val message: String): OneTimeEvent()
    object SuccessNavigation : OneTimeEvent()
}
