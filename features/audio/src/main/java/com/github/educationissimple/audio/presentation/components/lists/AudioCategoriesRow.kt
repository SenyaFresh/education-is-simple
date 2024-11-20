package com.github.educationissimple.audio.presentation.components.lists

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.educationissimple.audio.domain.entities.AudioCategory
import com.github.educationissimple.audio.domain.entities.AudioCategory.Companion.NO_CATEGORY_ID
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.composables.lists.ActionableItemsFlowRow
import com.github.educationissimple.components.entities.ActionableItem

/**
 * Composable that displays a horizontal list of audio categories, with the option to select a category
 * and reload the categories if necessary. It also includes an option for a first category label.
 *
 * @param categories The result container that holds the list of audio categories to be displayed.
 * @param onCategoryClick A callback function to be invoked when a category is clicked. It provides the selected category's ID.
 * @param onReloadCategories A callback function to reload the categories.
 * @param modifier A modifier to be applied to the row.
 * @param firstCategoryLabel The label for the first item in the row, usually representing a default or "no category" option.
 * @param activeCategoryId The ID of the currently active (selected) category. Default is [NO_CATEGORY_ID].
 * @param maxLines The maximum number of lines allowed for the categories. Default is [Int.MAX_VALUE], which allows unlimited lines.
 */
@Composable
fun AudioCategoriesRow(
    categories: ResultContainer<List<AudioCategory>>,
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
        onReloadItems = onReloadCategories,
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
fun AudioCategoriesRowPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AudioCategoriesRow(categories = ResultContainer.Done(
                (1..5).map {
                    AudioCategory(it.toLong(), "Category $it")
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