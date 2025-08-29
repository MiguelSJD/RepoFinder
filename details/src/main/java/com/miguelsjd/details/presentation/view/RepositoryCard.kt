package com.miguelsjd.details.presentation.view

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.CallSplit
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.miguelsjd.common.presentation.models.UIRepository
import com.miguelsjd.core.extensions.formatIsoDateToLocalDay
import com.miguelsjd.core.spacing.Spacing
import com.miguelsjd.details.R as DetailsR

@Composable
internal fun RepositoryCard(repository: UIRepository) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Spacing.medium),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF383C46))
    ) {
        Column(
            modifier = Modifier.padding(Spacing.medium)
        ) {
            Text(
                text = repository.name,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            repository.description?.let { description ->
                Spacer(Modifier.height(Spacing.extraSmall))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray
                )
            }

            Spacer(Modifier.height(Spacing.small))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                InfoChip(
                    icon = Icons.Filled.Star,
                    text = stringResource(DetailsR.string.details_stars_amount, repository.stars)
                )
                InfoChip(
                    icon = Icons.AutoMirrored.Filled.CallSplit,
                    text = stringResource(DetailsR.string.details_forks_amount, repository.forks)
                )
                repository.language?.let { language ->
                    InfoChip(
                        icon = Icons.Default.Code,
                        text = language
                    )
                }
            }

            Spacer(Modifier.height(Spacing.small))

            Text(
                text = stringResource(
                    DetailsR.string.details_updated,
                    repository.updatedAt.formatIsoDateToLocalDay()
                ),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun InfoChip(
    icon: ImageVector,
    contentDescription: String? = null,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(Color(0xFF3A3D46))
            .padding(horizontal = Spacing.small, vertical = Spacing.extraSmall)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription ?: text,
            tint = Color.White,
            modifier = Modifier.size(Spacing.medium)
        )
        Spacer(Modifier.width(Spacing.extraSmall))
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.bodySmall
        )
    }
}