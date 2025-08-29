package com.miguelsjd.details.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.miguelsjd.common.presentation.models.UIRepository
import com.miguelsjd.core.extensions.formatIsoDateToLocalDay
import com.miguelsjd.core.spacing.Spacing
import com.miguelsjd.details.R as DetailsR

@Composable
internal fun ExtraInfoSection(repository: UIRepository) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.small),
        modifier = Modifier.fillMaxWidth()
    ) {
        InfoRow(
            label = stringResource(DetailsR.string.details_watchers),
            value = repository.watchers.toString()
        )
        InfoRow(
            label = stringResource(DetailsR.string.details_issues),
            value = repository.openIssues.toString()
        )
        repository.licenseName?.let { license ->
            InfoRow(
                label = stringResource(DetailsR.string.details_license),
                value = license
            )
        }
        InfoRow(
            label = stringResource(DetailsR.string.details_created_at),
            value = repository.createdAt.formatIsoDateToLocalDay()
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(label, fontWeight = FontWeight.Bold, color = Color.White)
        Text(value, color = Color.LightGray)
    }
}