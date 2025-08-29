package com.miguelsjd.common.data.remote

import com.miguelsjd.common.data.models.RepositoriesResponse
import kotlinx.coroutines.flow.Flow

internal interface RepositoriesRemoteDataSource {
    fun getRepositories(
        language: String,
        page: Int,
        repositoriesPerPage: Int
    ): Flow<RepositoriesResponse>
}