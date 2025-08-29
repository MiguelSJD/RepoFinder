package com.miguelsjd.home.presentation.viewmodel.event

import androidx.annotation.StringRes
import com.miguelsjd.core.viewmodel.UIEvent

internal sealed class HomeEvent : UIEvent {
    data class ShowErrorSnackbar(
        val message: String? = null,
        @StringRes val defaultMessageRes: Int,
        @StringRes val actionLabel: Int,
        val onAction: () -> Unit = { },
        val onDismiss: () -> Unit = { },
    ) : HomeEvent()

    data class NavigateToDetails(val repositoryId: Int) : HomeEvent()
}