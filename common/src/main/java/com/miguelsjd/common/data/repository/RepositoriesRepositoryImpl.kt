package com.miguelsjd.common.data.repository

import com.miguelsjd.common.data.cache.RepositoryMemoryCache
import com.miguelsjd.common.data.local.RepositoriesLocalDataSource
import com.miguelsjd.common.data.mapper.toDomain
import com.miguelsjd.common.data.mapper.toEntity
import com.miguelsjd.common.data.remote.RepositoriesRemoteDataSource
import com.miguelsjd.common.data.util.isStale
import com.miguelsjd.common.domain.models.Repository
import com.miguelsjd.common.domain.repository.RepositoriesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

internal class RepositoriesRepositoryImpl(
    private val remoteDataSource: RepositoriesRemoteDataSource,
    private val localDataSource: RepositoriesLocalDataSource,
    private val memoryCache: RepositoryMemoryCache
) : RepositoriesRepository {

    override suspend fun getRepositories(
        language: String,
        page: Int,
        repositoriesPerPage: Int
    ): Flow<List<Repository>> {
        val local = localDataSource.getRepositories(
            language = language,
            page = page,
            repositoriesPerPage = repositoriesPerPage
        )
        return if (local.isEmpty() || local.firstOrNull()?.isStale() != false) {
            remoteDataSource.getRepositories(
                language = language,
                page = page,
                repositoriesPerPage = repositoriesPerPage
            ).map { repositoryResponse ->
                val entityList = repositoryResponse.toEntity()
                localDataSource.insertRepositories(entityList)
                entityList.map { entity ->
                    val repository = entity.toDomain()
                    memoryCache[repository.id] = repository
                    repository
                }
            }.catch { exception ->
                if (local.isEmpty()) {
                    throw exception
                } else {
                    emit(local.map { it.toDomain() })
                }
            }
        } else {
            flow {
                emit(local.map { entity -> entity.toDomain() })
            }
        }
    }
}