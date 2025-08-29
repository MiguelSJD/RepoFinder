package com.miguelsjd.common.data.local

import com.miguelsjd.common.data.db.RepositoriesDao
import com.miguelsjd.common.data.models.RepositoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class RepositoriesLocalDataSourceImpl(
    private val dao: RepositoriesDao
) : RepositoriesLocalDataSource {
    override suspend fun getRepositories(
        language: String,
        page: Int,
        repositoriesPerPage: Int
    ): List<RepositoryEntity> {
        return dao.getRepositoriesByLanguage(
            language = language,
            offset = (page - 1) * repositoriesPerPage,
            limit = repositoriesPerPage
        )
    }

    override fun getRepository(id: Int): Flow<RepositoryEntity?> = flow {
        emit(dao.getRepositoryById(id))
    }

    override suspend fun insertRepositories(repositories: List<RepositoryEntity>) {
        dao.insertRepositories(repositories)
    }

    override suspend fun insertRepository(repository: RepositoryEntity) {
        dao.insertRepository(repository)
    }
}