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
import com.github.educationissimple.components.composables.lists.ActionableItemsFlowRow
import com.github.educationissimple.components.entities.ActionableItem
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskCategory.Companion.NO_CATEGORY_ID

/**
 * Displays a flow row of categories, where each category is an actionable item that can be clicked to filter tasks.
 * The row can be scrolled horizontally and includes an option to reload the categories list.
 *
 * This composable function creates a flow row of categories, with the ability to interact with each category.
 * The row is displayed with a leading category label (such as "All"), followed by a list of categories. The categories can be clicked to trigger an action
 * via the [onCategoryClick] callback.
 *
 * - The categories are displayed as actionable items, where each item has a clickable label representing the category name.
 * - The active category is highlighted based on the [activeCategoryId].
 * - The [firstCategoryLabel] is shown as the first item in the row, which might represent an option like "All Categories."
 * - A refresh or reload option is available via the [onReloadCategories] callback, typically used to update the list of categories.
 * - The [maxLines] parameter controls the maximum number of visible rows for the categories. If there are too many categories, it will scroll horizontally.
 *
 * @param categories A [ResultContainer] containing the list of [TaskCategory] objects to be displayed as categories.
 * @param onCategoryClick A callback function invoked when a category is clicked. It receives the category ID as a parameter.
 * @param onReloadCategories A callback function invoked to reload the categories list.
 * @param modifier A [Modifier] to customize the appearance and layout of the row.
 * @param firstCategoryLabel The label to be displayed for the first category, often representing the "All Categories" option.
 * @param activeCategoryId The ID of the currently active category, used to highlight the active item in the list.
 * @param maxLines The maximum number of lines visible for the category row. Default is [Int.MAX_VALUE], which means unlimited visible lines.
 */
@Composable
fun CategoriesRow(
    categories: ResultContainer<List<TaskCategory>>,
    onCategoryClick: (Long) -> Unit,
    onReloadCategories: () -> Unit,
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
        onReloadItems = onReloadCategories,
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
                onReloadCategories = { },
                firstCategoryLabel = "All",
                maxLines = 1,
                modifier = Modifier
                    .horizontalScroll(rememberScrollState(0))
            )
        }
    }
}
