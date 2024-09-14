package com.bshpanchuk.presentation.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
inline fun <T> HandleEvent(channel: Channel<T>, crossinline onEvent: (T) -> Unit) {
    LaunchedEffect(key1 = Unit) {
        channel
            .receiveAsFlow()
            .onEach {
                onEvent(it)
            }
            .collect()
    }
}