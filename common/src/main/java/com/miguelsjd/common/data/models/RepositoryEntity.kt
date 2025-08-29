package com.miguelsjd.common.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
internal data class RepositoryEntity(
    val createdAt: String,
    val description: String?,
    val forks: Int,
    val githubUrl: String,
    @PrimaryKey val id: Int,
    val language: String?,
    val licenseName: String?,
    val name: String,
    val openIssues: Int,
    val ownerAvatarUrl: String,
    val ownerId: Int,
    val ownerLogin: String,
    val stars: Int,
    val updatedAt: String,
    val watchers: Int,
    val lastSyncedAt: Long
)
