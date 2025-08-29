package com.miguelsjd.common.domain.usecase

import com.miguelsjd.common.domain.models.Repository
import kotlinx.coroutines.flow.Flow

interface GetRepositoriesUseCase {
    suspend operator fun invoke(
        language: String,
        page: Int,
        repositoriesPerPage: Int
    ): Flow<List<Repository>>
}