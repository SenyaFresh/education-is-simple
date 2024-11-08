package com.github.educationissimple.components.composables.lists

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.composables.items.ActionableListItem
import com.github.educationissimple.components.composables.items.LoadingActionableListItem
import com.github.educationissimple.components.entities.ActionableItem
import com.github.educationissimple.presentation.ResultContainerComposable
import com.github.educationissimple.presentation.locals.LocalSpacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ActionableItemsFlowRow(
    items: ResultContainer<List<ActionableItem>>,
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    activeItemId: Long? = null,
    leadingItem: ActionableItem? = null,
    maxLines: Int = Int.MAX_VALUE
) {

    ResultContainerComposable(
        container = items,
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
        val displayedItems = (leadingItem?.let {
            listOf(
                leadingItem
            )
        } ?: emptyList()) + items.unwrap()
        ContextualFlowRow(
            itemCount = displayedItems.size,
            maxLines = maxLines,
            horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium),
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium),
            modifier = modifier
        ) { index ->
            val category = displayedItems[index]
            key(category.id, activeItemId) {
                ActionableListItem(
                    label = category.name,
                    isActive = category.id == activeItemId,
                    onClick = { onItemClick(category.id) }
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
            ActionableItemsFlowRow(items = ResultContainer.Done(
                (1..5).map {
                    ActionableItem(it.toLong(), "Category $it")
                }
            ),
                onItemClick = { },
                maxLines = 1,
                modifier = Modifier
                    .horizontalScroll(rememberScrollState(0))
            )
        }
    }
}