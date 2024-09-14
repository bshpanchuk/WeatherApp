package com.bshpanchuk.presentation.mvi

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

abstract class MviViewModel<E : UiEvent, S : UiState, D : Dialog, C : Callback> (
    dafaultState: S
) : ViewModel() {


    protected val state: MutableState<S> = mutableStateOf(dafaultState)
    val uiState: State<S> = state

    private var _dialogState = mutableStateOf<D?>(null)
    val dialogState: State<D?> = _dialogState

    val callbackChannel = Channel<C>()

    abstract fun handleUiEvent(uiEvent: E)

    protected fun updateState(block: S.() -> S) {
        state.value = block.invoke(state.value)
    }

    protected fun launch(
        progress: ((Boolean) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onComplete: () -> Unit = {},
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
        onError?.invoke(throwable)
    }) {
        try {
            progress?.invoke(true)
            block()
        } finally {
            progress?.invoke(false)
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