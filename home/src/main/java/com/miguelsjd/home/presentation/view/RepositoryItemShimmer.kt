package com.miguelsjd.home.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.miguelsjd.core.components.shimmer.rememberShimmerBrush
import com.miguelsjd.core.spacing.Spacing

@Composable
fun RepositoryItemShimmer() {
    val shimmerBrush = rememberShimmerBrush()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Spacing.small)
            .height(ViewConstants.repositoryItemShimmerHeight)
            .clip(RoundedCornerShape(Spacing.small))
            .background(shimmerBrush)
    ) { }
}
