package com.thejohnsondev.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.thejohnsondev.common.utils.Logger
import com.thejohnsondev.common.utils.getFirebaseErrorMessage
import com.thejohnsondev.model.DisplayableMessageValue
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.HttpError
import com.thejohnsondev.model.NetworkError
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.model.UnknownError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val eventFlow = Channel<OneTimeEvent>()
    protected val screenState: MutableStateFlow<ScreenState> =
        MutableStateFlow(ScreenState.None)
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            handleError(UnknownError(throwable))
        }
    }

    fun getEventFlow() = eventFlow.receiveAsFlow()

    protected suspend fun BaseViewModel.sendEvent(event: OneTimeEvent)  {
        eventFlow.send(event)
    }

    protected suspend fun BaseViewModel.loading()  {
        screenState.emit(ScreenState.Loading)
    }

    protected suspend fun BaseViewModel.showContent() {
        screenState.emit(ScreenState.ShowContent)
    }

    protected suspend fun handleError(error: Error) {
        showContent()
        val errorDisplayMessage = when (error) {
            is HttpError -> DisplayableMessageValue.StringValue(
                getFirebaseErrorMessage(error.message)
            )
            is NetworkError -> DisplayableMessageValue.CheckInternetConnection
            is UnknownError -> DisplayableMessageValue.StringValue(error.throwable?.message ?: "Unknown error")
            else -> DisplayableMessageValue.StringValue(error.throwable?.message ?: "Unknown error")
        }
        Logger.e("${this::class.simpleName} error: $error -- $errorDisplayMessage")
        sendEvent(OneTimeEvent.ErrorMessage(errorDisplayMessage))
    }

    protected fun BaseViewModel.launch(block: suspend CoroutineScope.() -> Unit): Job {
        val job = viewModelScope.launch(coroutineExceptionHandler) {
            block.invoke(viewModelScope)
        }
        return job
    }

    protected fun BaseViewModel.launchLoading(block: suspend CoroutineScope.() -> Unit): Job {
        val job = viewModelScope.launch(coroutineExceptionHandler) {
            loading()
            block.invoke(viewModelScope)
        }
        return job
    }

    protected suspend fun <T> Either<Error, T>.onResult(
        onError: ((Error) -> Unit)? = null,
        onSuccess: (T) -> Unit
    ) = fold(
        ifLeft = { error -> onError?.invoke(error) ?: handleError(error) },
        ifRight = { result -> onSuccess(result) }
    )

    protected suspend fun <T> Flow<Either<Error, T>>.onResult(
        onError: ((Error) -> Unit)? = null,
        onSuccess: (T) -> Unit
    ) = first().fold(
        ifLeft = { error -> onError?.invoke(error) ?: handleError(error) },
        ifRight = { result -> onSuccess(result) }
    )

}