package com.miguelsjd.common.data.util

import com.miguelsjd.common.data.models.RepositoryEntity

internal fun RepositoryEntity.isStale(): Boolean {
    val now = System.currentTimeMillis()
    return (now - lastSyncedAt) > CachePolicy.MAX_CACHE_AGE_MS
}