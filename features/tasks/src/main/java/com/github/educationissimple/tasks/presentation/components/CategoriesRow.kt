package com.github.educationissimple.tasks.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.presentation.ResultContainerComposable
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskCategory.Companion.NO_CATEGORY_ID

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriesRow(
    categories: ResultContainer<List<TaskCategory>>,
    onCategoryClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    firstCategoryLabel: String,
    activeCategoryId: Long = NO_CATEGORY_ID,
    maxLines: Int = Int.MAX_VALUE
) {

    ResultContainerComposable(container = categories, onTryAgain = { }) {
        val displayedCategories = listOf(
            TaskCategory(
                id = NO_CATEGORY_ID,
                name = firstCategoryLabel
            )
        ) + categories.unwrap()

        ContextualFlowRow(
            itemCount = displayedCategories.size,
            maxLines = maxLines,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
        ) { index ->
            val category = displayedCategories[index]
            key(category.id, activeCategoryId) {
                TaskCategoryCard(
                    category = category,
                    isActive = category.id == activeCategoryId,
                    onCategoryClick = onCategoryClick
                )
            }
        }
    }
}