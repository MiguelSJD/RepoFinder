package com.miguelsjd.core.components.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.miguelsjd.core.components.appbar.AppBarDefaults.APP_BAR_MAX_LINES
import com.miguelsjd.core.components.appbar.AppBarDefaults.AppBarIconButton
import com.miguelsjd.core.components.appbar.AppBarDefaults.AppBarType
import com.miguelsjd.core.components.appbar.AppBarDefaults.appBarHeight
import com.miguelsjd.core.spacing.Spacing

@Composable
fun AppBar(
    type: AppBarType,
    navigationButton: AppBarIconButton? = null,
    actionButton: AppBarIconButton? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                start = Spacing.small,
                end = Spacing.small
            )
            .height(appBarHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        navigationButton.Render()

        type.Render(Modifier.weight(1f))

        actionButton.Render()
    }
}

@Composable
private fun TitleAppBar(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.semantics(
            mergeDescendants = true
        ) {
            heading()
        },
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            modifier = Modifier.padding(vertical = Spacing.medium, horizontal = Spacing.small),
            text = title,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            maxLines = APP_BAR_MAX_LINES,
            overflow = TextOverflow.Ellipsis,
            color = Color.White
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchAppBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    clearButton: AppBarIconButton? = null,
    onSearch: (String) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DockedSearchBar(
            query = query,
            onQueryChange = onQueryChange,
            onSearch = onSearch,
            active = true,
            onActiveChange = { },
            placeholder = { Text("Search…") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                if (query.isNotEmpty() && clearButton != null) {
                    IconButton(
                        onClick = {
                            clearButton.onClick()
                        }
                    ) {
                        Icon(
                            imageVector = clearButton.icon,
                            contentDescription = clearButton.contentDescription
                        )
                    }
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            // Optional: search suggestions below
        }
    }
}

@Composable
fun AppBarIconButton?.Render() {
    this?.let { btn ->
        IconButton(onClick = btn.onClick) {
            Icon(
                imageVector = btn.icon,
                contentDescription = btn.contentDescription,
                tint = Color.White
            )
        }
    }
}

@Composable
fun AppBarType.Render(modifier: Modifier = Modifier) {
    when (this) {
        is AppBarType.Title -> TitleAppBar(
            title = title,
            modifier = modifier
        )
        is AppBarType.Search -> SearchAppBar(
            query = query,
            onQueryChange = onQueryChange,
            onSearch = onSearch,
            modifier = modifier
        )
    }
}