package com.miguelsjd.home.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.miguelsjd.core.spacing.Spacing
import com.miguelsjd.home.R as HomeR
import com.miguelsjd.home.presentation.models.UIRepositoriesLanguage

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun LanguageFilterBottomSheet(
    currentLanguage: UIRepositoriesLanguage,
    onSelect: (UIRepositoriesLanguage) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            Modifier
                .navigationBarsPadding()
                .padding(Spacing.medium)
        ) {
            Text(
                stringResource(HomeR.string.home_filter_bottom_sheet_title),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(Spacing.small))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Spacing.small),
                verticalArrangement = Arrangement.spacedBy(Spacing.small)
            ) {
                UIRepositoriesLanguage.entries.forEach { language ->
                    FilterChip(
                        language = language,
                        currentLanguage = currentLanguage,
                        onSelect = onSelect
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterChip(
    language: UIRepositoriesLanguage,
    currentLanguage: UIRepositoriesLanguage,
    onSelect: (UIRepositoriesLanguage) -> Unit
) {
    val isSelected = language == currentLanguage
    FilterChip(
        selected = isSelected,
        onClick = { onSelect(language) },
        label = { Text(language.language) },
        leadingIcon = {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(
                        HomeR.string.home_filter_bottom_sheet_selected_content_description
                    )
                )
            }
        }
    )
}