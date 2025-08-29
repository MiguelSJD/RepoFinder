package com.miguelsjd.details.presentation.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.miguelsjd.common.presentation.models.UIRepository
import com.miguelsjd.core.components.avatar.Avatar
import com.miguelsjd.core.components.avatar.AvatarDefaults
import com.miguelsjd.core.spacing.Spacing
import com.miguelsjd.details.R as DetailsR

@Composable
internal fun OwnerSection(repository: UIRepository) {
    Avatar(
        style = AvatarDefaults.AvatarStyle.MEDIUM,
        loadUrl = repository.ownerAvatarUrl,
        contentDescription = stringResource(
            DetailsR.string.details_owner_avatar_content_description
        )
    )
    Spacer(Modifier.height(Spacing.small))
    Text(
        text = repository.ownerLogin,
        style = MaterialTheme.typography.titleLarge,
        color = Color.White
    )
}