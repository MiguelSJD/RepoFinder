package com.miguelsjd.details.presentation.view

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.miguelsjd.core.components.appbar.AppBar
import com.miguelsjd.core.components.appbar.AppBarDefaults
import com.miguelsjd.core.extensions.OnEventDispatched
import com.miguelsjd.core.spacing.Spacing
import com.miguelsjd.core.theme.RepoFinderTheme
import com.miguelsjd.details.presentation.viewmodel.DetailsViewModel
import com.miguelsjd.details.presentation.viewmodel.event.DetailsEvent
import com.miguelsjd.details.presentation.viewmodel.intent.DetailsIntent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun DetailsScreen(
    repositoryId: Int,
    onBackClick: () -> Unit,
    viewModel: DetailsViewModel = koinViewModel {
        parametersOf(repositoryId)
    }
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    viewModel.OnEventDispatched { event ->
        when (event) {
            DetailsEvent.OnBackClick -> onBackClick()
            is DetailsEvent.ShowErrorSnackbar -> {
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
                        title = state.value.repository?.name.orEmpty()
                    ),
                    navigationButton = AppBarDefaults.AppBarIconButton(
                        icon = Icons.AutoMirrored.Outlined.ArrowBack,
                        onClick = { viewModel.dispatchViewIntent(DetailsIntent.OnBackClick) },
                        contentDescription = "Back"
                    )
                )
            },
            bottomBar = {
                state.value.repository?.let { repository ->
                    ActionButtons(
                        onOpenGithub = {
                            context.openUrl(repository.githubUrl)
                        },
                        onShare = {
                            context.shareText(repository.githubUrl, "Share repository")
                        }
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF1E1F25))
                    .padding(innerPadding)
            ) {
                state.value.repository?.let { repository ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(Spacing.medium),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OwnerSection(repository)
                        Spacer(Modifier.height(Spacing.large))
                        RepositoryCard(repository)
                        Spacer(Modifier.height(Spacing.large))
                        ExtraInfoSection(repository)
                    }
                }
            }
        }
    }
}

private fun Context.openUrl(url: String) {
    runCatching {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    }
}

private fun Context.shareText(text: String, title: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, title)
    startActivity(shareIntent)
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

