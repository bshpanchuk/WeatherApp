package com.bshpanchuk.presentation.mvi

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

abstract class MviViewModel<E : UiEvent, S : UiState, D : Dialog, C : Callback> : ViewModel() {


    abstract val state: S

    private var _dialogState = mutableStateOf<D?>(null)
    val dialogState: State<D?> = _dialogState
    private var _globalProgressState = mutableStateOf<Boolean>(false)
    val globalProgressState: State<Boolean> = _globalProgressState

    val callbackChannel = Channel<C>()

    abstract fun handleUiEvent(uiEvent: E)

    protected fun launch(
        onError: ((Throwable) -> Unit)? = null,
        onComplete: () -> Unit = {},
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
        onError?.invoke(throwable)
    }) {
        try {
            block()
        } finally {
            onComplete()
        }
    }

    protected fun launchWithProgress(
        progress: ((Boolean) -> Unit) = _globalProgressState::value::set,
        onComplete: () -> Unit = {},
        onError: ((Throwable) -> Unit)? = null,
        block: suspend CoroutineScope.() -> Unit
    ) = launch(onError) {
        try {
            progress(true)
            block()
        } finally {
            progress(false)
            onComplete()
        }
    }

    protected fun sendCallback(callback: C) {
        viewModelScope.launch {
            callbackChannel.send(callback)
        }
    }

    protected fun showDialog(dialog: D) {
        _dialogState.value = dialog
    }

    protected fun hideDialog() {
        _dialogState.value = null
    }

}