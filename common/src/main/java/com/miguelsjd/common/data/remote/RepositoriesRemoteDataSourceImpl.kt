package com.miguelsjd.common.data.remote

import com.miguelsjd.common.data.models.RepositoriesResponse
import com.miguelsjd.common.data.service.CommonService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val SORT = "stars"
private const val LANGUAGE_QUERY = "language:"

internal class RepositoriesRemoteDataSourceImpl(
    private val service: CommonService
) : RepositoriesRemoteDataSource {
    override fun getRepositories(
        language: String,
        page: Int,
        repositoriesPerPage: Int
    ): Flow<RepositoriesResponse> {
        val query = "$LANGUAGE_QUERY$language"
        return flow {
            emit(
                service.getRepositories(
                    query = query,
                    sort = SORT,
                    page = page,
                    repositoriesPerPage = repositoriesPerPage
                )
            )
        }
    }
}