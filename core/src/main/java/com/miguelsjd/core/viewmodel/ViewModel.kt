package com.miguelsjd.core.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class ViewModel<State : UIState, Event : UIEvent, Intent : UIIntent>(
    initialState: State
) : androidx.lifecycle.ViewModel() {
    private val _state = MutableStateFlow(initialState)
    private val _event = MutableSharedFlow<Event>()

    val state: StateFlow<State> = _state
    val event: SharedFlow<Event> = _event

    abstract fun dispatchViewIntent(intent: Intent)

    protected fun setState(newState: (State) -> State) {
        _state.update { newState(it) }
    }

    protected fun setEvent(newEvent: () -> Event) {
        viewModelScope.launch {
            _event.emit(newEvent())
        }
    }
}