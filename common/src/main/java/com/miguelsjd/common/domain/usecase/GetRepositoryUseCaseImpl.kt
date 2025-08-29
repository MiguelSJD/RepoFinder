package com.miguelsjd.common.domain.usecase

import com.miguelsjd.common.domain.models.Repository
import com.miguelsjd.common.domain.repository.RepositoryRepository
import kotlinx.coroutines.flow.Flow

internal class GetRepositoryUseCaseImpl(
    private val repository: RepositoryRepository
) : GetRepositoryUseCase {
    override operator fun invoke(id: Int): Flow<Repository> = repository.getRepository(id)
}