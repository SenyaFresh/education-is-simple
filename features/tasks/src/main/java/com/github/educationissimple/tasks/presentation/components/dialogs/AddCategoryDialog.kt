package com.github.educationissimple.tasks.presentation.components.dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.educationissimple.components.composables.dialogs.AddTextDialog
import com.github.educationissimple.tasks.R

/**
 * Displays a dialog for adding a new category.
 *
 * This composable function presents a dialog with a text input field that allows the user to
 * enter a new category name. The dialog includes "Confirm" and "Cancel" actions:
 * - When the user confirms, the provided [onConfirm] callback is triggered with the input text.
 * - When the user cancels, the provided [onCancel] callback is triggered.
 *
 * The dialog's appearance can be customized with the [modifier] parameter.
 *
 * @param onConfirm A callback function to be invoked when the user confirms the action. It takes
 * the entered category name as a parameter.
 * @param onCancel A callback function to be invoked when the user cancels the action.
 * @param modifier A [Modifier] that can be used to customize the layout or appearance of the dialog.
 */
@Composable
fun AddCategoryDialog(
    onConfirm: (String) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AddTextDialog(
        title = stringResource(R.string.add_new_category),
        placeholder = stringResource(R.string.input_new_category_here),
        onConfirm = onConfirm,
        onCancel = onCancel,
        modifier = modifier
    )
}

@Preview(showSystemUi = true)
@Composable
fun AddCategoryDialogPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        AddCategoryDialog(onConfirm = {}, onCancel = {})
    }
}