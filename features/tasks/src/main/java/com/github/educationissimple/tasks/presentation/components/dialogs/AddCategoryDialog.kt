package com.github.educationissimple.tasks.presentation.components.dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.educationissimple.components.composables.dialogs.AddTextDialog
import com.github.educationissimple.tasks.R

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