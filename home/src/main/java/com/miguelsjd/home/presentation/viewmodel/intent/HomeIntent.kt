package com.miguelsjd.home.presentation.viewmodel.intent

import com.miguelsjd.core.viewmodel.UIIntent
import com.miguelsjd.home.presentation.models.UIRepositoriesLanguage

internal sealed class HomeIntent : UIIntent {
    data object OnStartUp : HomeIntent()
    data object OnLanguageFilterClick: HomeIntent()
    data object OnLanguageFilterBottomSheetDismiss: HomeIntent()
    data object OnScrollToBottomNextPage : HomeIntent()

    data class OnLanguageSelected(
        val selectedLanguage: UIRepositoriesLanguage
    ) : HomeIntent()

    data class OnRepositoryCardClick(
        val repositoryId: Int
    ) : HomeIntent()
}