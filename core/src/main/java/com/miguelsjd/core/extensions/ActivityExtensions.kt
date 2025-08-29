package com.miguelsjd.core.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.miguelsjd.core.viewmodel.UIEvent
import com.miguelsjd.core.viewmodel.UIIntent
import com.miguelsjd.core.viewmodel.UIState
import com.miguelsjd.core.viewmodel.ViewModel
import kotlinx.coroutines.launch

inline fun <reified State : UIState,
        reified Event : UIEvent,
        reified Intent : UIIntent> AppCompatActivity.onStateChange(
    viewModel: ViewModel<State, Event, Intent>,
    crossinline handleStates: (State) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.state.collect { state ->
                handleStates(state)
            }
        }
    }
}

inline fun <reified State : UIState,
        reified Event : UIEvent,
        reified Intent : UIIntent> AppCompatActivity.onEventDispatched(
    viewModel: ViewModel<State, Event, Intent>,
    crossinline handleEvent: (Event) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.event.collect { event ->
                handleEvent(event)
            }
        }
    }
}