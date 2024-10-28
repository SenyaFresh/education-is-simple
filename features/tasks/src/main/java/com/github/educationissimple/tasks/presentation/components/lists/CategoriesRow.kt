package com.github.educationissimple.tasks.presentation.components.lists

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.presentation.ResultContainerComposable
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskCategory.Companion.NO_CATEGORY_ID
import com.github.educationissimple.tasks.presentation.components.items.TaskCategoryListItem

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

    ResultContainerComposable(container = categories, onTryAgain = { }, modifier = modifier) {
        val displayedCategories = listOf(
            TaskCategory(
                id = NO_CATEGORY_ID,
                name = firstCategoryLabel
            )
        ) + categories.unwrap()

        ContextualFlowRow(
            itemCount = displayedCategories.size,
            maxLines = maxLines,
            horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium),
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium),
        ) { index ->
            val category = displayedCategories[index]
            key(category.id, activeCategoryId) {
                TaskCategoryListItem(
                    category = category,
                    isActive = category.id == activeCategoryId,
                    onCategoryClick = onCategoryClick
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CategoriesRowPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CategoriesRow(categories = ResultContainer.Done(
                (1..5).map {
                    TaskCategory(it.toLong(), "Category $it")
                }
            ),
                onCategoryClick = { },
                firstCategoryLabel = "All",
                maxLines = 1,
                modifier = Modifier
                    .horizontalScroll(rememberScrollState(0))
            )
        }
    }
}
