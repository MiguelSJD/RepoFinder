package com.miguelsjd.common.data.remote

import com.miguelsjd.common.data.models.RepositoryResponse
import kotlinx.coroutines.flow.Flow

internal interface RepositoryRemoteDataSource {
    fun getRepository(
        repositoryId: Int
    ): Flow<RepositoryResponse>
}