package com.miguelsjd.common.domain.repository

import com.miguelsjd.common.domain.models.Repository
import kotlinx.coroutines.flow.Flow

internal interface RepositoriesRepository {
    suspend fun getRepositories(
        language: String,
        page: Int,
        repositoriesPerPage: Int
    ): Flow<List<Repository>>
}