package com.miguelsjd.common.presentation.mapper

import com.miguelsjd.common.domain.models.Repository
import com.miguelsjd.common.presentation.models.UIRepository

interface UIRepositoryMapper {
    fun map(source: Repository): UIRepository
}