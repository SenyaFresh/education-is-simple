package com.github.educationissimple.components.composables.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.composables.buttons.AddFloatingActionButton
import com.github.educationissimple.components.composables.dialogs.AddTextDialog
import com.github.educationissimple.components.composables.items.ActionableListItem
import com.github.educationissimple.components.composables.items.LoadingActionableListItem
import com.github.educationissimple.components.entities.ActionableItem
import com.github.educationissimple.presentation.ResultContainerComposable
import com.github.educationissimple.presentation.locals.LocalSpacing

@Composable
fun ActionableItemsListWithAdding(
    items: ResultContainer<List<ActionableItem>>,
    addLabel: String,
    addPlaceholder: String,
    onDelete: (Long) -> Unit,
    onAdd: (String) -> Unit
) {
    var isAddingNewItem by remember { mutableStateOf(false) }

    ResultContainerComposable(
        container = items,
        onTryAgain = { },
        onLoading = {
            Column(
                verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.semiMedium),
                modifier = Modifier.padding(20.dp)
            ) {
                repeat(5) {
                    LoadingActionableListItem(withDelete = true)
                }
            }
        }
    ) {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.semiMedium),
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                items = items.unwrap(),
                key = { actionableItem -> actionableItem.id }) { actionableItem ->
                ActionableListItem(
                    label = actionableItem.name,
                    onDelete = { onDelete(actionableItem.id) },
                    modifier = Modifier.animateItem()
                )
            }
        }

        if (isAddingNewItem) {
            AddTextDialog(
                title = addLabel,
                placeholder = addPlaceholder,
                onConfirm = { categoryName ->
                    onAdd(categoryName)
                    isAddingNewItem = false
                },
                onCancel = { isAddingNewItem = false }
            )
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                AddFloatingActionButton(
                    onClick = { isAddingNewItem = true },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(LocalSpacing.current.medium)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CategoriesContentPreview() {
    val items = (1..5).map {
        ActionableItem(it.toLong(), "Category $it")
    }
    ActionableItemsListWithAdding(
        ResultContainer.Done(items),
        "Add category",
        "Category name",
        onDelete = { },
        onAdd = { }
    )
}

@Preview(showSystemUi = true)
@Composable
fun CategoriesContentLoadingPreview() {
    ActionableItemsListWithAdding(
        ResultContainer.Loading,
        "Add category",
        "Category name",
        onDelete = { },
        onAdd = { }
    )
}