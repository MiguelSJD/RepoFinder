package com.miguelsjd.common.data.repository

import com.miguelsjd.common.data.cache.RepositoryMemoryCache
import com.miguelsjd.common.data.local.RepositoriesLocalDataSource
import com.miguelsjd.common.data.mapper.toDomain
import com.miguelsjd.common.data.mapper.toEntity
import com.miguelsjd.common.data.remote.RepositoryRemoteDataSource
import com.miguelsjd.common.domain.models.Repository
import com.miguelsjd.common.domain.repository.RepositoryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalCoroutinesApi::class)
internal class RepositoryRepositoryImpl(
    private val remoteDataSource: RepositoryRemoteDataSource,
    private val localDataSource: RepositoriesLocalDataSource,
    private val memoryCache: RepositoryMemoryCache
) : RepositoryRepository {
    override fun getRepository(id: Int): Flow<Repository> {
        memoryCache[id]?.let { repository ->
            return flowOf(repository)
        }

        return localDataSource.getRepository(id).flatMapLatest { local ->
            local?.let { localEntity ->
                flowOf(localEntity.toDomain())
            } ?: remoteDataSource.getRepository(id).map { remote ->
                val updatedEntity = remote.toEntity()
                localDataSource.insertRepository(updatedEntity)
                val repository = updatedEntity.toDomain()
                memoryCache[repository.id] = repository
                repository
            }
        }
    }
}