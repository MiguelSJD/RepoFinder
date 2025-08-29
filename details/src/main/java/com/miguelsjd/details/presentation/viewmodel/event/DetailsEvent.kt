package com.miguelsjd.details.presentation.viewmodel.event

import androidx.annotation.StringRes
import com.miguelsjd.core.viewmodel.UIEvent

internal sealed class DetailsEvent : UIEvent {
    data object OnBackClick : DetailsEvent()

    data class ShowErrorSnackbar(
        val message: String? = null,
        @StringRes val defaultMessageRes: Int,
        @StringRes val actionLabel: Int,
        val onAction: () -> Unit = { },
        val onDismiss: () -> Unit = { },
    ) : DetailsEvent()
}