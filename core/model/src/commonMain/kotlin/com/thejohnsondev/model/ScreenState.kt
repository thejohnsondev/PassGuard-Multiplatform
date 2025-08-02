package com.thejohnsondev.model

open class ScreenState {
    data object None: ScreenState()
    data class Error(val message: String) : ScreenState()
    data object Loading : ScreenState()
    data object ShowContent : ScreenState()
}