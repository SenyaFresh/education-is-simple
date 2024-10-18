package com.github.educationissimple.tasks.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ContextualFlowRowOverflow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.presentation.ResultContainerComposable
import com.github.educationissimple.tasks.domain.entities.TaskCategory

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriesRow(
    categories: ResultContainer<List<TaskCategory>>,
    onCategoryClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    firstItemLabel: String,
    activeCategoryId: Long = 0,
    maxLines: Int = Int.MAX_VALUE
) {

    ResultContainerComposable(container = categories, onTryAgain = { }) {
        val categoriesList = listOf(TaskCategory(id = 0, name = firstItemLabel)) + categories.unwrap()

        ContextualFlowRow(
            itemCount = categoriesList.size,
            maxLines = maxLines,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
        ) { index ->
            val category = categoriesList[index]
            key(activeCategoryId) {
                TaskCategoryCard(
                    category = category,
                    isActive = category.id == activeCategoryId,
                    onCategoryClick = onCategoryClick
                )
            }
        }
    }
}