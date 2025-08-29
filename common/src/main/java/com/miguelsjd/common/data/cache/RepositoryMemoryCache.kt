package com.miguelsjd.common.data.cache

import com.miguelsjd.common.domain.models.Repository

internal class RepositoryMemoryCache {
    private val cache = mutableMapOf<Int, Repository>()

    operator fun get(id: Int): Repository? = cache[id]

    operator fun set(id: Int, repository: Repository) {
        cache[id] = repository
    }

    fun putAll(repositories: List<Repository>) {
        repositories.forEach { cache[it.id] = it }
    }
}
