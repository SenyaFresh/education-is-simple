package com.github.educationissimple.tasks.presentation.components.lists

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.composables.ActionableItemsFlowRow
import com.github.educationissimple.components.entities.ActionableItem
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskCategory.Companion.NO_CATEGORY_ID

@Composable
fun CategoriesRow(
    categories: ResultContainer<List<TaskCategory>>,
    onCategoryClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    firstCategoryLabel: String,
    activeCategoryId: Long = NO_CATEGORY_ID,
    maxLines: Int = Int.MAX_VALUE
) {
    ActionableItemsFlowRow(
        items = categories.map { list -> list.map { ActionableItem(it.id, it.name) } },
        onItemClick = onCategoryClick,
        modifier = modifier,
        activeItemId = activeCategoryId,
        leadingItem = ActionableItem(
            id = NO_CATEGORY_ID,
            name = firstCategoryLabel
        ),
        maxLines = maxLines
    )
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
