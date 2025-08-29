package com.miguelsjd.home.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.miguelsjd.common.presentation.models.UIRepository
import com.miguelsjd.core.components.avatar.Avatar
import com.miguelsjd.core.components.avatar.AvatarDefaults
import com.miguelsjd.core.spacing.Spacing
import com.miguelsjd.home.R as HomeR

@Composable
fun RepositoryItem(
    repository: UIRepository,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.small, vertical = Spacing.extraSmall)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(Spacing.small),
        elevation = CardDefaults.cardElevation(Spacing.extraSmall),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF383C46))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Avatar(
                style = AvatarDefaults.AvatarStyle.SMALL,
                loadUrl = repository.ownerAvatarUrl,
                contentDescription = stringResource(
                    HomeR.string.home_owner_avatar_content_description
                ),
            )

            Spacer(Modifier.width(Spacing.small))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = repository.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = repository.ownerLogin,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(Spacing.extraSmall))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.small),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatItem(
                        icon = Icons.Filled.Star,
                        contentDescription = stringResource(
                            HomeR.string.home_stars_amount_content_description,
                            repository.stars.toString()
                        ),
                        value = repository.stars.toString()
                    )
                    repository.language?.let { language ->
                        StatItem(
                            icon = Icons.Default.Code,
                            contentDescription = language,
                            value = language
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StatItem(
    icon: ImageVector,
    contentDescription: String,
    value: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(Spacing.medium)
        )
        Spacer(Modifier.width(Spacing.extraSmall))
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}