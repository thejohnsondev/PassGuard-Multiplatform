package com.thejohnsondev.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.thejohnsondev.common.utils.Logger
import com.thejohnsondev.common.utils.getPrettyErrorMessage
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.HttpError
import com.thejohnsondev.model.LoadingState
import com.thejohnsondev.model.NetworkError
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.UnknownError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val eventChannel = Channel<OneTimeEvent>()
    protected val _loadingState: MutableStateFlow<LoadingState> =
        MutableStateFlow(LoadingState.Loaded)
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            handleError(UnknownError(throwable))
        }
    }

    fun getEventFlow() = eventChannel.receiveAsFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, OneTimeEvent.None)

    protected suspend fun BaseViewModel.sendEvent(event: OneTimeEvent)  {
        loaded()
        eventChannel.send(event)
    }

    protected suspend fun BaseViewModel.loading()  {
        _loadingState.emit(LoadingState.Loading)
    }

    protected suspend fun BaseViewModel.loaded()  {
        _loadingState.emit(LoadingState.Loaded)
    }

    protected suspend fun handleError(error: Error) {
        loaded()
        val errorMessage = when (error) {
            is HttpError -> error.message
            is NetworkError -> "Please, check your internet connection"
            is UnknownError -> error.throwable?.message
            else -> error.throwable?.message
        }
        Logger.e("${this::class.simpleName} error: ${error::class.simpleName} $errorMessage")
        sendEvent(OneTimeEvent.InfoMessage(getPrettyErrorMessage(errorMessage)))
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