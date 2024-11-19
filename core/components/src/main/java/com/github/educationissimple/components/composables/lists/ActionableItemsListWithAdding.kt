package com.github.educationissimple.components.composables.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    onReloadItems: () -> Unit,
    emptyListMessage: String,
    onDelete: (Long) -> Unit,
    addLabel: String,
    addPlaceholder: String,
    onAdd: ((String) -> Unit)? = null
) {
    var isAddingNewItem by remember { mutableStateOf(false) }

    ResultContainerComposable(
        container = items,
        onTryAgain = onReloadItems,
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
        if (items.unwrap().isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = emptyListMessage,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(LocalSpacing.current.medium)
                )
            }
        } else {
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
        }

        if (onAdd != null) {
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
}

@Preview(showSystemUi = true)
@Composable
fun CategoriesContentPreview() {
    val items = (1..5).map {
        ActionableItem(it.toLong(), "Category $it")
    }
    ActionableItemsListWithAdding(
        ResultContainer.Done(items),
        addLabel = "Add category",
        addPlaceholder = "Category name",
        onDelete = { },
        onAdd = { },
        emptyListMessage = "List is empty",
        onReloadItems = { },
    )
}

@Preview(showSystemUi = true)
@Composable
fun CategoriesContentLoadingPreview() {
    ActionableItemsListWithAdding(
        ResultContainer.Loading,
        addLabel = "Add category",
        addPlaceholder = "Category name",
        onDelete = { },
        onAdd = { },
        emptyListMessage = "List is empty",
        onReloadItems = { },
    )
}