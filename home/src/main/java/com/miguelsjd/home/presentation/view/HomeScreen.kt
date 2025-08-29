package com.miguelsjd.home.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.miguelsjd.core.components.appbar.AppBar
import com.miguelsjd.core.components.appbar.AppBarDefaults
import com.miguelsjd.core.extensions.OnEventDispatched
import com.miguelsjd.core.spacing.Spacing
import com.miguelsjd.core.theme.RepoFinderTheme
import com.miguelsjd.home.presentation.view.ViewConstants.LOAD_THRESHOLD
import com.miguelsjd.home.R as HomeR
import com.miguelsjd.home.presentation.viewmodel.HomeViewModel
import com.miguelsjd.home.presentation.viewmodel.event.HomeEvent
import com.miguelsjd.home.presentation.viewmodel.intent.HomeIntent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun HomeScreen(
    onRepositoryClick: (Int) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val listState = rememberLazyListState()
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisible ->
                val total = listState.layoutInfo.totalItemsCount
                val reachedBottom = lastVisible != null && lastVisible >= total - LOAD_THRESHOLD
                val isShowingSnackbar = snackbarHostState.currentSnackbarData != null
                if (!isShowingSnackbar && reachedBottom) {
                    viewModel.dispatchViewIntent(HomeIntent.OnScrollToBottomNextPage)
                }
            }
    }

    viewModel.OnEventDispatched { event ->
        when (event) {
            is HomeEvent.NavigateToDetails -> onRepositoryClick(event.repositoryId)
            is HomeEvent.ShowErrorSnackbar -> {
                coroutineScope.launch {
                    showErrorSnackbar(
                        snackbarHostState = snackbarHostState,
                        message = event.message ?: context.getString(event.defaultMessageRes),
                        actionLabel = context.getString(event.actionLabel),
                        onAction = event.onAction,
                        onDismiss = event.onDismiss
                    )
                }
            }
        }
    }
    viewModel.dispatchViewIntent(intent = HomeIntent.OnStartUp)

    RepoFinderTheme {
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState
                )
            },
            topBar = {
                AppBar(
                    type = AppBarDefaults.AppBarType.Title(
                        title = state.value.appBarTitle?.let {
                            stringResource(
                                HomeR.string.home_title,
                                state.value.selectedLanguage.language
                            )
                        } ?: stringResource(HomeR.string.home_title_default)
                    ),
                    actionButton = AppBarDefaults.AppBarIconButton(
                        icon = Icons.Outlined.FilterAlt,
                        onClick = {
                            viewModel.dispatchViewIntent(
                                intent = HomeIntent.OnLanguageFilterClick
                            )
                        },
                        contentDescription = stringResource(
                            HomeR.string.home_filter_button_content_description
                        )
                    )
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF26282F))
                    .padding(innerPadding)
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = Spacing.small)
                ) {
                    items(state.value.repositories) { repository ->
                        RepositoryItem(
                            repository = repository,
                            onClick = {
                                viewModel.dispatchViewIntent(
                                    intent = HomeIntent.OnRepositoryCardClick(
                                        repositoryId = repository.id
                                    )
                                )
                            }
                        )
                    }
                    if (state.value.isLoading) {
                        items(ViewConstants.SHIMMER_AMOUNT) {
                            RepositoryItemShimmer()
                        }
                    }
                }
            }
        }

        if (state.value.isLanguageFilterBottomSheetVisible) {
            LanguageFilterBottomSheet(
                currentLanguage = state.value.selectedLanguage,
                onSelect = { language ->
                    viewModel.dispatchViewIntent(
                        intent = HomeIntent.OnLanguageSelected(
                            selectedLanguage = language
                        )
                    )
                },
                onDismiss = {
                    viewModel.dispatchViewIntent(
                        intent = HomeIntent.OnLanguageFilterBottomSheetDismiss
                    )
                }
            )
        }
    }
}


private suspend fun showErrorSnackbar(
    snackbarHostState: SnackbarHostState,
    message: String,
    actionLabel: String,
    onAction: () -> Unit,
    onDismiss: () -> Unit = { }
) {
    val result = snackbarHostState.showSnackbar(
        message = message,
        actionLabel = actionLabel,
    )
    when (result) {
        SnackbarResult.Dismissed -> onDismiss()
        SnackbarResult.ActionPerformed -> onAction()
    }
}