package com.miguelsjd.home.data.local

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

internal interface DataStoreManager {
    suspend fun <T> save(
        key: Preferences.Key<T>,
        value: T
    )

    fun <T> fetchOrDefault(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T>
}