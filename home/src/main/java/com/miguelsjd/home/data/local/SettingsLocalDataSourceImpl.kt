package com.miguelsjd.home.data.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = DataStoreKeys.SETTINGS_DATA_STORE_NAME)

internal class SettingsLocalDataSourceImpl(
    private val context: Context
) : DataStoreManager {
    override suspend fun <T> save(
        key: Preferences.Key<T>,
        value: T
    ) {
        context.dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    override fun <T> fetchOrDefault(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> = context.dataStore.data.map { prefs ->
        prefs[key] ?: defaultValue
    }
}