package com.miguelsjd.common.data.mapper

import com.miguelsjd.common.data.models.RepositoriesResponse
import com.miguelsjd.common.data.models.RepositoryEntity
import com.miguelsjd.common.data.models.RepositoryResponse
import com.miguelsjd.common.domain.models.Repository

internal fun RepositoriesResponse.toEntity(): List<RepositoryEntity> {
    val now = System.currentTimeMillis()
    return this.repositories.map { repository ->
        repository.toEntity(current = now)
    }
}

internal fun RepositoryResponse.toEntity(
    current: Long = System.currentTimeMillis()
): RepositoryEntity {
    return RepositoryEntity(
        createdAt = this.createdAt,
        description = this.description,
        forks = this.forks,
        githubUrl = this.githubUrl,
        id = this.id,
        language = this.language,
        licenseName = this.license?.name,
        name = this.name,
        openIssues = this.openIssues,
        ownerAvatarUrl = this.owner.avatarUrl,
        ownerId = this.owner.id,
        ownerLogin = this.owner.login,
        stars = this.stars,
        updatedAt = this.updatedAt,
        watchers = this.watchers,
        lastSyncedAt = current
    )
}

internal fun RepositoryEntity.toDomain() = Repository(
    createdAt = this.createdAt,
    description = this.description,
    forks = this.forks,
    githubUrl = this.githubUrl,
    id = this.id,
    language = this.language,
    licenseName = this.licenseName,
    name = this.name,
    openIssues = this.openIssues,
    ownerAvatarUrl = this.ownerAvatarUrl,
    ownerId = this.ownerId,
    ownerLogin = this.ownerLogin,
    stars = this.stars,
    updatedAt = this.updatedAt,
    watchers = this.watchers
)