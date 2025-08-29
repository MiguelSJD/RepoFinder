package com.miguelsjd.common.presentation.mapper

import com.miguelsjd.common.domain.models.Repository
import com.miguelsjd.common.presentation.models.UIRepository

internal class UIRepositoryMapperImpl : UIRepositoryMapper {
    override fun map(source: Repository): UIRepository = UIRepository(
        createdAt = source.createdAt,
        description = source.description,
        forks = source.forks,
        githubUrl = source.githubUrl,
        id = source.id,
        language = source.language,
        licenseName = source.licenseName,
        name = source.name,
        openIssues = source.openIssues,
        ownerAvatarUrl = source.ownerAvatarUrl,
        ownerId = source.ownerId,
        ownerLogin = source.ownerLogin,
        stars = source.stars,
        updatedAt = source.updatedAt,
        watchers = source.watchers
    )
}

