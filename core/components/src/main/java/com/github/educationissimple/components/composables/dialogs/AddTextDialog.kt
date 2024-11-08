package com.github.educationissimple.components.composables.dialogs

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
import com.github.educationissimple.components.R
import com.github.educationissimple.components.composables.buttons.DefaultPrimaryButton
import com.github.educationissimple.components.composables.buttons.DefaultSecondaryButton
import com.github.educationissimple.components.composables.inputs.DefaultTextField
import com.github.educationissimple.presentation.locals.LocalSpacing

@Composable
fun AddTextDialog(
    title: String,
    placeholder: String,
    onConfirm: (String) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var textInput by remember { mutableStateOf("") }

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
            // New category input.
            DefaultTextField(
                text = textInput,
                onValueChange = { textInput = it },
                placeholder = { Text(placeholder) }
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
                        if (textInput.isNotBlank()) {
                            onConfirm(textInput)
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
fun AddTextDialogPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        AddTextDialog(
            title = "Добавить что-то",
            placeholder = "Введите что-то сюда",
            onConfirm = {},
            onCancel = {}
        )
    }
}