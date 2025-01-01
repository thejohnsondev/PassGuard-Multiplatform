package com.thejohnsondev.common.base

import com.thejohnsondev.model.Error
import com.thejohnsondev.model.OneTimeEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

class TestViewModel : BaseViewModel() {

    suspend fun publicSendEvent(event: OneTimeEvent) {
        sendEvent(event)
    }

    suspend fun publicLoading() {
        loading()
    }

    suspend fun publicLoaded() {
        showContent()
    }

    suspend fun publicHandleError(error: Error) {
        handleError(error)
    }

    fun publicLaunch(block: suspend CoroutineScope.() -> Unit): Job {
        return launch(block)
    }

    fun publicLaunchLoading(block: suspend CoroutineScope.() -> Unit): Job {
        return launchLoading(block)
    }

    fun publicGetScreenState() = screenState
}