package com.github.educationissimple.components.composables.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.github.educationissimple.components.composables.buttons.DefaultIconButton
import com.github.educationissimple.components.composables.buttons.DefaultPrimaryButton
import com.github.educationissimple.components.composables.buttons.DefaultSecondaryButton
import com.github.educationissimple.components.composables.inputs.DefaultTextField
import com.github.educationissimple.components.composables.lists.ActionableItemsFlowRow
import com.github.educationissimple.components.entities.ActionableItem
import com.github.educationissimple.presentation.locals.LocalSpacing

@Composable
fun SelectOrCreateDialog(
    title: String,
    items: ResultContainer<List<ActionableItem>>,
    initialItem: ActionableItem,
    textFieldPlaceholder: String,
    onReloadItems: () -> Unit,
    onConfirm: (ActionableItem) -> Unit,
    confirmLabel: String,
    onCancel: () -> Unit,
    cancelLabel: String,
    onAddNewItem: (String) -> Unit,
    modifier: Modifier = Modifier,
    initialActiveItemId: Long? = null
) {
    var newItemText by remember { mutableStateOf("") }
    var activeItemId by remember { mutableStateOf(initialActiveItemId) }

    DefaultDialog(
        onDismiss = onCancel,
        title = title,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium),
            modifier = modifier
                .padding(LocalSpacing.current.extraSmall)
                .fillMaxWidth()
        ) {
            ActionableItemsFlowRow(
                items = items,
                onItemClick = { activeItemId = it },
                onReloadItems = onReloadItems,
                activeItemId = activeItemId,
                modifier = Modifier
                    .heightIn(max = 120.dp)
                    .verticalScroll(rememberScrollState()),
                leadingItem = initialItem
            )

            DefaultTextField(
                text = newItemText,
                onValueChange = { newItemText = it },
                placeholder = { Text(textFieldPlaceholder) },
                trailingIcon = {
                    DefaultIconButton(onClick = {
                        if (newItemText.isNotBlank()) {
                            onAddNewItem(newItemText)
                            newItemText = ""
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Add new item",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            )

            // Cancel and Confirm buttons.
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.small)
            ) {
                DefaultSecondaryButton(
                    label = cancelLabel,
                    onClick = onCancel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                DefaultPrimaryButton(
                    label = confirmLabel,
                    onClick = {
                        onConfirm(
                            getSelectedCategory(items.unwrap(), activeItemId) ?: initialItem
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}

private fun getSelectedCategory(categories: List<ActionableItem>, id: Long?): ActionableItem? {
    return categories.firstOrNull { it.id == id }
}

@Preview(showSystemUi = true)
@Composable
fun SelectOrCreateDialogPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        SelectOrCreateDialog(
            title = "Выберите категорию",
            items = ResultContainer.Done(
                (1..10).map {
                    ActionableItem(id = it.toLong(), name = "Category $it")
                }
            ),
            onConfirm = { },
            onCancel = { },
            onReloadItems = { },
            onAddNewItem = { },
            initialItem = ActionableItem(
                id = 0,
                name = "Без категории"
            ),
            textFieldPlaceholder = "Введите название категории",
            confirmLabel = "Продолжить",
            cancelLabel = "Отмена"
        )
    }
}