package com.miguelsjd.home.presentation.viewmodel.state

import com.miguelsjd.common.presentation.models.UIRepository
import com.miguelsjd.core.viewmodel.UIState
import com.miguelsjd.home.presentation.models.UIRepositoriesLanguage
import com.miguelsjd.home.presentation.viewmodel.HomeViewModelConstants

internal data class HomeState(
    val isLoading: Boolean = false,
    val isLanguageFilterBottomSheetVisible: Boolean = false,
    val appBarTitle: String? = null,
    val page: Int = HomeViewModelConstants.FIRST_PAGE,
    val repositoriesPerPage: Int = HomeViewModelConstants.REPOSITORIES_PER_PAGE,
    val selectedLanguage: UIRepositoriesLanguage = UIRepositoriesLanguage.KOTLIN,
    val repositories: List<UIRepository> = emptyList()
) : UIState {
    fun setSelectedLanguage(
        selectedLanguage: UIRepositoriesLanguage,
        page: Int? = null
    ) = this.copy(
        isLanguageFilterBottomSheetVisible = false,
        selectedLanguage = selectedLanguage,
        appBarTitle = selectedLanguage.language,
        page = page ?: this.page
    )
}
