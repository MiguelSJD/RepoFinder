package com.miguelsjd.common.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OwnerResponse(
    @SerialName("avatar_url")
    val avatarUrl: String,
    @SerialName("id")
    val id: Int,
    @SerialName("login")
    val login: String
)