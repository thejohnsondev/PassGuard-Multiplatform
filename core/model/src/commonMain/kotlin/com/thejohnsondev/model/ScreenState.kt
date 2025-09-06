package com.thejohnsondev.model

import com.thejohnsondev.model.Error

open class ScreenState {
    data object None: ScreenState()
    data class Error(val message: String) : ScreenState()
    data object Loading : ScreenState()
    data object ShowContent : ScreenState();

    companion object {
        fun ScreenState.isLoading(): Boolean = this is Loading
        fun ScreenState.isError(): Boolean = this is Error
        fun ScreenState.isShowContent(): Boolean = this is ShowContent
    }
}