package com.miguelsjd.common.domain.usecase

import com.miguelsjd.common.domain.models.Repository
import kotlinx.coroutines.flow.Flow

interface GetRepositoryUseCase {
    operator fun invoke(id: Int): Flow<Repository>
}