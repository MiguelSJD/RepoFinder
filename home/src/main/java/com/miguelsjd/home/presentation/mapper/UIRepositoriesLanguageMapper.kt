package com.miguelsjd.home.presentation.mapper

import com.miguelsjd.home.presentation.models.UIRepositoriesLanguage

internal fun String.toUIRepositoriesLanguage(): UIRepositoriesLanguage {
    return UIRepositoriesLanguage.entries.find { repositoryLanguage ->
        this@toUIRepositoriesLanguage == repositoryLanguage.language
    } ?: UIRepositoriesLanguage.KOTLIN
}
