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
import com.github.educationissimple.tasks.domain.entities.TaskCategoryId

/**
 * Displays a dialog for selecting a category or creating a new one.
 *
 * The dialog presents a list of existing categories and an option to create a new category.
 * The user can select a category, cancel the action, or confirm the selection.
 *
 * @param title The title of the dialog, usually indicating the purpose of the action (e.g., "Select Category").
 * @param categories A [ResultContainer] containing a list of available categories. The dialog displays these categories for the user to choose from.
 * @param onConfirm A callback function triggered when the user confirms their category selection. It receives the selected category as a [TaskCategory] object.
 * @param onCancel A callback function triggered when the user cancels the action and dismisses the dialog.
 * @param onAddNewCategory A callback function to handle the creation of a new category. It receives the name of the new category as a `String`.
 * @param onReloadCategories A callback function triggered to reload the list of categories.
 * @param modifier A modifier to customize the appearance and layout of the dialog.
 * @param initialActiveCategoryId The initially selected category ID. Defaults to `NO_CATEGORY_ID` if no initial category is provided.
 */

@Composable
fun SelectCategoryDialog(
    title: String,
    categories: ResultContainer<List<TaskCategory>>,
    onConfirm: (TaskCategory) -> Unit,
    onCancel: () -> Unit,
    onAddNewCategory: (String) -> Unit,
    onReloadCategories: () -> Unit,
    modifier: Modifier = Modifier,
    initialActiveCategoryId: TaskCategoryId = NO_CATEGORY_ID
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
        onReloadItems = onReloadCategories,
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
            onAddNewCategory = { },
            onReloadCategories = { }
        )
    }
}