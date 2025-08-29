package com.miguelsjd.details.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.miguelsjd.core.spacing.Spacing
import com.miguelsjd.details.R as DetailsR

@Composable
internal fun ActionButtons(
    onOpenGithub: () -> Unit,
    onShare: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Spacing.medium),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF26282F))
            .padding(
                start = Spacing.medium,
                top = Spacing.medium,
                end = Spacing.medium,
                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
            )
    ) {
        OutlinedButton(
            onClick = onOpenGithub,
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Default.OpenInBrowser, contentDescription = null)
            Spacer(Modifier.width(Spacing.small))
            Text(
                stringResource(
                    DetailsR.string.details_open_github_button_label
                )
            )
        }
        OutlinedButton(
            onClick = onShare,
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Default.Share, contentDescription = null)
            Spacer(Modifier.width(Spacing.small))
            Text(
                stringResource(
                    DetailsR.string.details_share_button_label
                )
            )
        }
    }
}