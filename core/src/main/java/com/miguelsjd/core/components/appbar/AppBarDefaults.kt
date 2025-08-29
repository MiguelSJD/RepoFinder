package com.miguelsjd.core.components.appbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

object AppBarDefaults {
    val appBarHeight = 56.dp
    const val APP_BAR_MAX_LINES = 1

    @Immutable
    sealed class AppBarType {
        data class Title(
            val title: String
        ) : AppBarType()

        data class Search(
            val query: String,
            val onQueryChange: (String) -> Unit,
            val onSearch: (String) -> Unit
        ) : AppBarType()
    }

    @Immutable
    data class AppBarIconButton(
        val icon: ImageVector,
        val contentDescription: String,
        val onClick: () -> Unit
    )
}