package com.miguelsjd.home.presentation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.miguelsjd.common.domain.usecase.GetRepositoriesUseCase
import com.miguelsjd.common.presentation.mapper.UIRepositoryMapper
import com.miguelsjd.common.presentation.models.UIRepository
import com.miguelsjd.core.viewmodel.ViewModel
import com.miguelsjd.home.domain.usecase.FetchSettingsUseCase
import com.miguelsjd.home.domain.usecase.SaveSettingsUseCase
import com.miguelsjd.home.presentation.mapper.toUIRepositoriesLanguage
import com.miguelsjd.home.presentation.models.UIRepositoriesLanguage
import com.miguelsjd.home.presentation.viewmodel.event.HomeEvent
import com.miguelsjd.home.presentation.viewmodel.intent.HomeIntent
import com.miguelsjd.home.presentation.viewmodel.state.HomeState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import com.miguelsjd.home.R as HomeR

internal class HomeViewModel(
    private val getRepositoriesUseCase: GetRepositoriesUseCase,
    private val fetchSettingsUseCase: FetchSettingsUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase,
    private val uiRepositoryMapper: UIRepositoryMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel<HomeState, HomeEvent, HomeIntent>(
    initialState = HomeState()
) {

    override fun dispatchViewIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.OnStartUp -> onStartUp()
            HomeIntent.OnLanguageFilterClick -> onLanguageFilterClick()
            HomeIntent.OnLanguageFilterBottomSheetDismiss -> onLanguageFilterBottomSheetDismiss()
            HomeIntent.OnScrollToBottomNextPage -> onScrollToBottomNextPage()
            is HomeIntent.OnLanguageSelected -> onLanguageSelected(intent.selectedLanguage)
            is HomeIntent.OnRepositoryCardClick -> onRepositoryCardClick(intent.repositoryId)
        }
    }

    private fun onStartUp() {
        if (state.value.repositories.isNotEmpty()) return
        val defaultLanguage = UIRepositoriesLanguage.KOTLIN
        viewModelScope.launch {
            fetchSettingsUseCase(
                key = HomeViewModelConstants.selectedLanguageKey,
                defaultValue = defaultLanguage.language
            ).flowOn(dispatcher)
                .onStart { setState { state -> state.copy(isLoading = true) } }
                .catch { error ->
                    handleFetchSelectedLanguageError(error, defaultLanguage)
                }
                .collect { selectedLanguage ->
                    val repositoryLanguage = selectedLanguage.toUIRepositoriesLanguage()
                    setState { state ->
                        state.setSelectedLanguage(
                            selectedLanguage = repositoryLanguage
                        )
                    }
                    getRepositories(language = repositoryLanguage)
                }
        }
    }

    private fun handleFetchSelectedLanguageError(
        error: Throwable,
        defaultLanguage: UIRepositoriesLanguage
    ) {
        showErrorSnackbar(
            message = error.message,
            defaultMessageRes = HomeR.string.home_fetch_selected_language_generic_error_message,
            actionLabel = HomeR.string.home_ok_button_label
        )
        setState { state ->
            state.setSelectedLanguage(
                selectedLanguage = defaultLanguage
            )
        }
        getRepositories(language = defaultLanguage)
    }

    private fun getRepositories(
        page: Int = state.value.page,
        language: UIRepositoriesLanguage = state.value.selectedLanguage,
        repositoriesPerPage: Int = state.value.repositoriesPerPage
    ) {
        viewModelScope.launch {
            getRepositoriesUseCase(
                language = language.language,
                page = page,
                repositoriesPerPage = repositoriesPerPage
            ).flowOn(dispatcher)
                .onStart { setState { state -> state.copy(isLoading = true) } }
                .onCompletion { setState { state -> state.copy(isLoading = false) } }
                .catch { error ->
                    handleFetchRepositoriesError(
                        error = error,
                        page = page,
                        language = language,
                        repositoriesPerPage = repositoriesPerPage
                    )
                }
                .collect { repositories ->
                    setRepositories(
                        pageLoaded = page,
                        newRepositories = repositories.map { repository ->
                            uiRepositoryMapper.map(repository)
                        }
                    )
                }
        }
    }

    private fun handleFetchRepositoriesError(
        error: Throwable,
        page: Int,
        language: UIRepositoriesLanguage,
        repositoriesPerPage: Int
    ) {
        setState { state ->
            state.copy(
                page = page - HomeViewModelConstants.ONE_PAGE
            )
        }
        showErrorSnackbar(
            message = error.message,
            defaultMessageRes =
                HomeR.string.home_fetch_repositories_generic_error_message,
            actionLabel = HomeR.string.home_retry_button_label
        ) {
            getRepositories(
                page = page,
                language = language,
                repositoriesPerPage = repositoriesPerPage
            )
        }
    }

    private fun showErrorSnackbar(
        message: String?,
        @StringRes defaultMessageRes: Int,
        @StringRes actionLabel: Int,
        action: () -> Unit = { }
    ) {
        setEvent {
            HomeEvent.ShowErrorSnackbar(
                message = message,
                defaultMessageRes = defaultMessageRes,
                actionLabel = actionLabel,
                onAction = action
            )
        }
    }

    private fun setRepositories(
        pageLoaded: Int,
        newRepositories: List<UIRepository>
    ) {
        val isFirstPage = pageLoaded == HomeViewModelConstants.FIRST_PAGE
        val updatedRepositories = if (isFirstPage) {
            newRepositories
        } else {
            (state.value.repositories + newRepositories).distinctBy { repository -> repository.id }
        }

        val noNewData = !isFirstPage && updatedRepositories.size == state.value.repositories.size

        if (noNewData) {
            setEvent {
                HomeEvent.ShowErrorSnackbar(
                    defaultMessageRes = HomeR.string.home_all_repositories_fetched_error_message,
                    actionLabel = HomeR.string.home_ok_button_label
                )
            }
        } else {
            setState { state ->
                state.copy(
                    repositories = updatedRepositories,
                    page = pageLoaded
                )
            }
        }
    }


    private fun onLanguageFilterClick() {
        setState { state ->
            state.copy(
                isLanguageFilterBottomSheetVisible = true
            )
        }
    }

    private fun onLanguageFilterBottomSheetDismiss() {
        setState { state ->
            state.copy(
                isLanguageFilterBottomSheetVisible = false
            )
        }
    }

    private fun onScrollToBottomNextPage() {
        if(state.value.repositories.isEmpty() || state.value.isLoading) return
        getRepositories(page = state.value.page + HomeViewModelConstants.ONE_PAGE)
    }

    private fun onLanguageSelected(selectedLanguage: UIRepositoriesLanguage) {
        viewModelScope.launch {
            saveSettingsUseCase(
                key = HomeViewModelConstants.selectedLanguageKey,
                value = selectedLanguage.language
            )
        }
        val isSameLanguage = selectedLanguage == state.value.selectedLanguage
        val page = if (!isSameLanguage) HomeViewModelConstants.FIRST_PAGE else state.value.page
        setState { state ->
            state.setSelectedLanguage(
                selectedLanguage = selectedLanguage,
                page = page
            )
        }
        if (isSameLanguage) return
        getRepositories(
            language = selectedLanguage,
            page = page
        )
    }

    private fun onRepositoryCardClick(repositoryId: Int) {
        setEvent {
            HomeEvent.NavigateToDetails(
                repositoryId = repositoryId
            )
        }
    }
}