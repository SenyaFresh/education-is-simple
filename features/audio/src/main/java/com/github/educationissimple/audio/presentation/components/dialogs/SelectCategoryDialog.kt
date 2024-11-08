package com.github.educationissimple.audio.presentation.components.dialogs

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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.educationissimple.audio.R
import com.github.educationissimple.audio.domain.entities.AudioCategory
import com.github.educationissimple.audio.domain.entities.AudioCategory.Companion.NO_CATEGORY_ID
import com.github.educationissimple.audio.presentation.components.lists.AudioCategoriesRow
import com.github.educationissimple.common.Core
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.composables.DefaultDialog
import com.github.educationissimple.components.composables.DefaultIconButton
import com.github.educationissimple.components.composables.DefaultPrimaryButton
import com.github.educationissimple.components.composables.DefaultSecondaryButton
import com.github.educationissimple.components.composables.DefaultTextField
import com.github.educationissimple.presentation.locals.LocalSpacing

@Composable
fun SelectCategoryDialog(
    title: String,
    categories: ResultContainer<List<AudioCategory>>,
    onConfirm: (AudioCategory) -> Unit,
    onCancel: () -> Unit,
    onAddNewCategory: (String) -> Unit,
    modifier: Modifier = Modifier,
    initialActiveCategoryId: Long = NO_CATEGORY_ID
) {
    var newCategoryText by remember { mutableStateOf("") }
    var activeCategoryId by remember { mutableLongStateOf(initialActiveCategoryId) }

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
            // Categories list.
            AudioCategoriesRow(
                categories = categories,
                onCategoryClick = { activeCategoryId = it },
                activeCategoryId = activeCategoryId,
                firstCategoryLabel = stringResource(R.string.no_category),
                modifier = Modifier
                    .heightIn(max = 120.dp)
                    .verticalScroll(rememberScrollState())
            )

            // New category input.
            DefaultTextField(
                text = newCategoryText,
                onValueChange = { newCategoryText = it },
                placeholder = { Text(stringResource(R.string.input_new_category_here)) },
                trailingIcon = {
                    DefaultIconButton(onClick = {
                        if (newCategoryText.isNotBlank()) {
                            onAddNewCategory(newCategoryText)
                            newCategoryText = ""
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Add new category",
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
                    label = stringResource(R.string.cancel),
                    onClick = onCancel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                DefaultPrimaryButton(
                    label = stringResource(R.string.continue_),
                    onClick = {
                        onConfirm(
                            getSelectedCategory(categories.unwrap(), activeCategoryId)
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

private fun getSelectedCategory(categories: List<AudioCategory>, id: Long): AudioCategory {
    return categories.firstOrNull { it.id == id } ?: AudioCategory(
        NO_CATEGORY_ID,
        Core.resources.getString(R.string.no_category)
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
            onAddNewCategory = { }
        )
    }
}