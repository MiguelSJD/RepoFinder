package com.miguelsjd.common.data.remote

import com.miguelsjd.common.data.models.RepositoryResponse
import com.miguelsjd.common.data.service.CommonService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class RepositoryRemoteDataSourceImpl(
    private val service: CommonService
) : RepositoryRemoteDataSource {
    override fun getRepository(
        repositoryId: Int
    ): Flow<RepositoryResponse> = flow {
        emit(service.getRepository(repositoryId = repositoryId))
    }
}