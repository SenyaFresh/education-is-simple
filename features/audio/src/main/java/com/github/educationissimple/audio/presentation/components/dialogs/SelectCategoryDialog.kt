package com.github.educationissimple.audio.presentation.components.dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.educationissimple.audio.R
import com.github.educationissimple.audio.domain.entities.AudioCategory
import com.github.educationissimple.audio.domain.entities.AudioCategory.Companion.NO_CATEGORY_ID
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.composables.dialogs.SelectOrCreateDialog
import com.github.educationissimple.components.entities.ActionableItem

/**
 * Displays a dialog for selecting or creating a new audio category.
 *
 * The dialog shows a list of available audio categories. The user can either select an existing
 * category or create a new one by entering a name. The dialog includes options to confirm the selection,
 * cancel the dialog, or reload the categories if necessary.
 *
 * @param title The title to be displayed at the top of the dialog.
 * @param categories A container holding the list of audio categories.
 * @param onTryAgain A callback to reload the categories in case of an error.
 * @param onConfirm A callback to confirm the selection of an audio category.
 * @param onCancel A callback to cancel the dialog.
 * @param onAddNewCategory A callback to add a new category by providing a name.
 * @param modifier A modifier to be applied to the dialog's root view.
 * @param initialActiveCategoryId The ID of the category that is initially selected. Defaults to [NO_CATEGORY_ID] if no category is selected.
 */
@Composable
fun SelectCategoryDialog(
    title: String,
    categories: ResultContainer<List<AudioCategory>>,
    onTryAgain: () -> Unit,
    onConfirm: (AudioCategory) -> Unit,
    onCancel: () -> Unit,
    onAddNewCategory: (String) -> Unit,
    modifier: Modifier = Modifier,
    initialActiveCategoryId: Long = NO_CATEGORY_ID
) {
    SelectOrCreateDialog(
        title = title,
        items = categories.map { list -> list.map { ActionableItem(it.id, it.name) } },
        onReloadItems = onTryAgain,
        initialItem = ActionableItem(
            NO_CATEGORY_ID,
            stringResource(R.string.no_category)
        ),
        textFieldPlaceholder = stringResource(R.string.input_new_category_here),
        onConfirm = { onConfirm(AudioCategory(it.id, it.name)) },
        confirmLabel = stringResource(R.string.continue_),
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
                    AudioCategory(id = it.toLong(), name = "Category $it")
                }
            ),
            onConfirm = { },
            onCancel = { },
            onAddNewCategory = { },
            onTryAgain = { }
        )
    }
}