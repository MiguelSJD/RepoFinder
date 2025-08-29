package com.miguelsjd.common.data.service

import com.miguelsjd.common.data.models.RepositoriesResponse
import com.miguelsjd.common.data.models.RepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface CommonService {
    @GET("search/repositories")
    suspend fun getRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("per_page") repositoriesPerPage: Int
    ): RepositoriesResponse

    @GET("repositories/{repositoryId}")
    suspend fun getRepository(
        @Path("repositoryId") repositoryId: Int
    ): RepositoryResponse
}