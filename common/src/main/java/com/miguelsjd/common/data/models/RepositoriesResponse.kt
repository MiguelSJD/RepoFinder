package com.miguelsjd.common.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RepositoriesResponse(
    @SerialName("items")
    val repositories: List<RepositoryResponse>
)
