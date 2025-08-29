package com.miguelsjd.home.data.repository

import androidx.datastore.preferences.core.Preferences
import com.miguelsjd.home.data.local.DataStoreManager
import com.miguelsjd.home.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

internal class SettingsRepositoryImpl(
    private val dataStoreManager: DataStoreManager
) : SettingsRepository {
    override suspend fun <T> save(key: Preferences.Key<T>, value: T) {
        dataStoreManager.save(key, value)
    }

    override fun <T> fetchOrDefault(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return dataStoreManager.fetchOrDefault(key, defaultValue)
    }
}