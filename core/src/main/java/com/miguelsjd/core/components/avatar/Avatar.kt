package com.miguelsjd.core.components.avatar

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImage
import com.miguelsjd.core.components.avatar.AvatarDefaults.AvatarStyle

@Composable
fun Avatar(
    style: AvatarStyle,
    loadUrl: String,
    contentDescription: String
) {
    AsyncImage(
        model = loadUrl,
        contentDescription = contentDescription,
        modifier = Modifier
            .size(style.size)
            .clip(CircleShape)
    )
}