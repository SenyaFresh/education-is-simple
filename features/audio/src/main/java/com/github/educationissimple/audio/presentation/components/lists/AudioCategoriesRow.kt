package com.github.educationissimple.audio.presentation.components.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.educationissimple.audio.domain.entities.AudioCategory
import com.github.educationissimple.audio.domain.entities.AudioCategory.Companion.NO_CATEGORY_ID
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.composables.ActionableListItem
import com.github.educationissimple.components.composables.LoadingActionableListItem
import com.github.educationissimple.presentation.ResultContainerComposable
import com.github.educationissimple.presentation.locals.LocalSpacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AudioCategoriesRow(
    categories: ResultContainer<List<AudioCategory>>,
    onCategoryClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    firstCategoryLabel: String,
    activeCategoryId: Long = NO_CATEGORY_ID,
    maxLines: Int = Int.MAX_VALUE
) {

    ResultContainerComposable(
        container = categories,
        onTryAgain = { },
        onLoading = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium),
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(vertical = LocalSpacing.current.small)
            ) {
                repeat(5) {
                    LoadingActionableListItem()
                }
            }
        }
    ) {
        val displayedCategories = listOf(
            AudioCategory(
                id = NO_CATEGORY_ID,
                name = firstCategoryLabel
            )
        ) + categories.unwrap()

        ContextualFlowRow(
            itemCount = displayedCategories.size,
            maxLines = maxLines,
            horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium),
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium),
            modifier = modifier
        ) { index ->
            val category = displayedCategories[index]
            key(category.id, activeCategoryId) {
                ActionableListItem(
                    label = category.name,
                    isActive = category.id == activeCategoryId,
                    onClick = { onCategoryClick(category.id) }
                )
            }
        }
    }

}