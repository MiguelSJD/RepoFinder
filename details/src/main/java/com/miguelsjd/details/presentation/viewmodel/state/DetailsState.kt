package com.miguelsjd.details.presentation.viewmodel.state

import com.miguelsjd.common.presentation.models.UIRepository
import com.miguelsjd.core.viewmodel.UIState

internal data class DetailsState(
    val isLoading: Boolean = false,
    val repository: UIRepository? = null
) : UIState