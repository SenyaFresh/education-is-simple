package com.github.educationissimple.tasks.presentation.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.educationissimple.components.composables.DefaultDialog
import com.github.educationissimple.components.composables.DefaultPrimaryButton
import com.github.educationissimple.components.composables.DefaultSecondaryButton
import com.github.educationissimple.components.composables.DefaultTextField
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.R

@Composable
fun AddCategoryDialog(
    onConfirm: (String) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var newCategoryText by remember { mutableStateOf("") }

    DefaultDialog(
        onDismiss = onCancel,
        title = stringResource(R.string.add_new_category),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium),
            modifier = modifier
                .padding(LocalSpacing.current.extraSmall)
                .fillMaxWidth()
        ) {
            // New category input.
            DefaultTextField(
                text = newCategoryText,
                onValueChange = { newCategoryText = it },
                placeholder = { Text(stringResource(R.string.input_new_category_here)) }
            )

            // Cancel and Confirm buttons.
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.small)
            ) {
                DefaultSecondaryButton(
                    label = stringResource(R.string.cancel),
                    onClick = onCancel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                DefaultPrimaryButton(
                    label = stringResource(R.string.confirm),
                    onClick = {
                        if (newCategoryText.isNotBlank()) {
                            onConfirm(newCategoryText)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AddCategoryDialogPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        AddCategoryDialog(onConfirm = {}, onCancel = {})
    }
}