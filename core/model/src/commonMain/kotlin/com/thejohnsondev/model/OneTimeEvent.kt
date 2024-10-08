package com.thejohnsondev.model

open class OneTimeEvent {
    object None: OneTimeEvent()
    class InfoToast(val message: String): OneTimeEvent()
    class InfoSnackbar(val message: String): OneTimeEvent()
    class SuccessNavigation(val message: String? = null) : OneTimeEvent()
}
