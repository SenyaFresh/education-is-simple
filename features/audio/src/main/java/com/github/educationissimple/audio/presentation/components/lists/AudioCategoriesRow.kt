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

@Composable
fun AudioCategoriesRow(
    categories: ResultContainer<List<AudioCategory>>,
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
fun AudioCategoriesRowPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AudioCategoriesRow(categories = ResultContainer.Done(
                (1..5).map {
                    AudioCategory(it.toLong(), "Category $it")
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