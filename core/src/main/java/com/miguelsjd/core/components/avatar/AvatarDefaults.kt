package com.miguelsjd.core.components.avatar

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object AvatarDefaults {

    private val smallAvatarSize = 48.dp
    private val mediumAvatarSize = 96.dp

    enum class AvatarStyle(
        val size: Dp
    ) {
        SMALL(
            size = smallAvatarSize
        ),
        MEDIUM(
            size = mediumAvatarSize
        )
    }
}