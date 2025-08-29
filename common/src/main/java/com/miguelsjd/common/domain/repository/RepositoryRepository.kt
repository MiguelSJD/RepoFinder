package com.miguelsjd.common.domain.repository

import com.miguelsjd.common.domain.models.Repository
import kotlinx.coroutines.flow.Flow

interface RepositoryRepository {
    fun getRepository(id: Int): Flow<Repository>
}