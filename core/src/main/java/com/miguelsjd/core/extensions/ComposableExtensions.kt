package com.miguelsjd.core.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.miguelsjd.core.viewmodel.UIEvent
import com.miguelsjd.core.viewmodel.UIIntent
import com.miguelsjd.core.viewmodel.UIState
import com.miguelsjd.core.viewmodel.ViewModel

@Composable
inline fun <reified State : UIState,
        reified Event : UIEvent,
        reified Intent : UIIntent>
        ViewModel<State, Event, Intent>.OnEventDispatched(
    crossinline handleEvent: (Event) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            event.collect { e -> handleEvent(e) }
        }
    }
}
