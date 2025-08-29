package com.miguelsjd.common.data.local

import com.miguelsjd.common.data.models.RepositoryEntity
import kotlinx.coroutines.flow.Flow

internal interface RepositoriesLocalDataSource {
    suspend fun getRepositories(
        language: String,
        page: Int,
        repositoriesPerPage: Int
    ): List<RepositoryEntity>

    fun getRepository(id: Int): Flow<RepositoryEntity?>

    suspend fun insertRepositories(repositories: List<RepositoryEntity>)

    suspend fun insertRepository(repository: RepositoryEntity)
}