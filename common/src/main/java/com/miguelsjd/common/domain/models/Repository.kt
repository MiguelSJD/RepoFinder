package com.miguelsjd.common.domain.models

data class Repository(
    val createdAt: String,
    val description: String?,
    val forks: Int,
    val githubUrl: String,
    val id: Int,
    val language: String?,
    val licenseName: String?,
    val name: String,
    val openIssues: Int,
    val ownerAvatarUrl: String,
    val ownerId: Int,
    val ownerLogin: String,
    val stars: Int,
    val updatedAt: String,
    val watchers: Int
)
