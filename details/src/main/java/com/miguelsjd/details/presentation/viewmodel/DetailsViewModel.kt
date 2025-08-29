package com.miguelsjd.details.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.miguelsjd.common.domain.usecase.GetRepositoryUseCase
import com.miguelsjd.common.presentation.mapper.UIRepositoryMapper
import com.miguelsjd.core.viewmodel.ViewModel
import com.miguelsjd.details.presentation.viewmodel.event.DetailsEvent
import com.miguelsjd.details.presentation.viewmodel.intent.DetailsIntent
import com.miguelsjd.details.presentation.viewmodel.state.DetailsState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import com.miguelsjd.details.R as DetailsR

internal class DetailsViewModel(
    private val repositoryId: Int,
    private val getRepositoryUseCase: GetRepositoryUseCase,
    private val uiRepositoryMapper: UIRepositoryMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel<DetailsState, DetailsEvent, DetailsIntent>(
    initialState = DetailsState()
) {
    init {
        onStart()
    }

    override fun dispatchViewIntent(intent: DetailsIntent) {
        when (intent) {
            DetailsIntent.OnBackClick -> onBackClick()
        }
    }

    private fun onStart() {
        viewModelScope.launch {
            getRepositoryUseCase(repositoryId)
                .flowOn(dispatcher)
                .onStart { setState { state -> state.copy(isLoading = true) } }
                .onCompletion { setState { state -> state.copy(isLoading = false) } }
                .catch {
                    handleFetchRepositoryError()
                }
                .collect { repository ->
                    setState { state ->
                        state.copy(
                            repository = uiRepositoryMapper.map(repository)
                        )
                    }
                }
        }
    }

    private fun handleFetchRepositoryError() {
        setEvent {
            DetailsEvent.ShowErrorSnackbar(
                defaultMessageRes = DetailsR.string.details_ok_button_label,
                actionLabel = DetailsR.string.details_ok_button_label,
                onAction = {
                    setEvent {
                        DetailsEvent.OnBackClick
                    }
                }
            )
        }
    }

    private fun onBackClick() {
        setEvent {
            DetailsEvent.OnBackClick
        }
    }
}