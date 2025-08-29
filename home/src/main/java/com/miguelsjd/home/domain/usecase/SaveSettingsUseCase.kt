package com.miguelsjd.home.domain.usecase

import androidx.datastore.preferences.core.Preferences
import com.miguelsjd.home.domain.repository.SettingsRepository

internal class SaveSettingsUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun <T> invoke(
        key: Preferences.Key<T>,
        value: T
    ) {
        repository.save(key, value)
    }
}