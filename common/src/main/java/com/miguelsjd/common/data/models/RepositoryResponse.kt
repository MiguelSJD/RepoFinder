package com.miguelsjd.common.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RepositoryResponse(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("description")
    val description: String? = null,
    @SerialName("forks")
    val forks: Int,
    @SerialName("html_url")
    val githubUrl: String,
    @SerialName("id")
    val id: Int,
    @SerialName("language")
    val language: String? = null,
    @SerialName("license")
    val license: RepositoryLicenseResponse? = null,
    @SerialName("name")
    val name: String,
    @SerialName("open_issues")
    val openIssues: Int,
    @SerialName("owner")
    val owner: OwnerResponse,
    @SerialName("stargazers_count")
    val stars: Int,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("watchers_count")
    val watchers: Int
)
