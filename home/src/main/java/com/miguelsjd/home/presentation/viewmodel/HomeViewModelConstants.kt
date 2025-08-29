package com.miguelsjd.home.presentation.viewmodel

import androidx.datastore.preferences.core.stringPreferencesKey

internal object HomeViewModelConstants {
    const val FIRST_PAGE = 1
    const val ONE_PAGE = 1
    const val REPOSITORIES_PER_PAGE = 30

    val selectedLanguageKey = stringPreferencesKey("selected_language")
}