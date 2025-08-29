package com.miguelsjd.details.presentation.viewmodel.intent

import com.miguelsjd.core.viewmodel.UIIntent

internal sealed class DetailsIntent : UIIntent {
    data object OnBackClick : DetailsIntent()
}