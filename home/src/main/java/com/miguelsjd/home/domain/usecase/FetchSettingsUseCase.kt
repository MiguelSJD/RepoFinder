package com.miguelsjd.home.domain.usecase

import androidx.datastore.preferences.core.Preferences
import com.miguelsjd.home.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

internal class FetchSettingsUseCase(
    private val repository: SettingsRepository
) {
    operator fun <T> invoke(
        key: Preferences.Key<T>,
        defaultValue: T
    ) : Flow<T> = repository.fetchOrDefault(key, defaultValue)
}