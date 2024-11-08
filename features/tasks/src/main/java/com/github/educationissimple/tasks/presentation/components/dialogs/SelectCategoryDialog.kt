package com.github.educationissimple.tasks.presentation.components.dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.composables.dialogs.SelectOrCreateDialog
import com.github.educationissimple.components.entities.ActionableItem
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskCategory.Companion.NO_CATEGORY_ID

@Composable
fun SelectCategoryDialog(
    title: String,
    categories: ResultContainer<List<TaskCategory>>,
    onConfirm: (TaskCategory) -> Unit,
    onCancel: () -> Unit,
    onAddNewCategory: (String) -> Unit,
    modifier: Modifier = Modifier,
    initialActiveCategoryId: Long = NO_CATEGORY_ID
) {
    SelectOrCreateDialog(
        title = title,
        items = categories.map { list -> list.map { ActionableItem(it.id, it.name) } },
        initialItem = ActionableItem(
            NO_CATEGORY_ID,
            stringResource(R.string.no_category)
        ),
        textFieldPlaceholder = stringResource(R.string.input_new_category_here) ,
        onConfirm = { onConfirm(TaskCategory(it.id, it.name)) },
        confirmLabel = stringResource(R.string.confirm),
        onCancel = onCancel,
        cancelLabel = stringResource(R.string.cancel),
        onAddNewItem = onAddNewCategory,
        modifier = modifier,
        initialActiveItemId = initialActiveCategoryId
    )
}


@Preview(showSystemUi = true)
@Composable
fun SelectCategoryDialogPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        SelectCategoryDialog(
            title = "Выберите категорию",
            categories = ResultContainer.Done(
                (1..10).map {
                    TaskCategory(id = it.toLong(), name = "Category $it")
                }
            ),
            onConfirm = { },
            onCancel = { },
            onAddNewCategory = { }
        )
    }
}