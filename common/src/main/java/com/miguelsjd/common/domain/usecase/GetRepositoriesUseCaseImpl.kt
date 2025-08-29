package com.miguelsjd.common.domain.usecase

import com.miguelsjd.common.domain.repository.RepositoriesRepository

internal class GetRepositoriesUseCaseImpl(
    private val repository: RepositoriesRepository
) : GetRepositoriesUseCase {
    override suspend operator fun invoke(
        language: String,
        page: Int,
        repositoriesPerPage: Int
    ) = repository.getRepositories(
        language = language,
        page = page,
        repositoriesPerPage = repositoriesPerPage
    )
}