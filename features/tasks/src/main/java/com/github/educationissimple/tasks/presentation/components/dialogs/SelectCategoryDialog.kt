package com.github.educationissimple.tasks.presentation.components.dialogs

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.educationissimple.common.Core
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.components.composables.DefaultIconButton
import com.github.educationissimple.components.composables.DefaultPrimaryButton
import com.github.educationissimple.components.composables.DefaultSecondaryButton
import com.github.educationissimple.components.composables.DefaultTextField
import com.github.educationissimple.presentation.ResultContainerComposable
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskCategory.Companion.NO_CATEGORY_ID
import com.github.educationissimple.tasks.presentation.components.lists.CategoriesRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCategoryDialog(
    categories: ResultContainer<List<TaskCategory>>,
    onConfirm: (TaskCategory) -> Unit,
    onCancel: () -> Unit,
    onAddNewCategory: (String) -> Unit,
    modifier: Modifier = Modifier,
    initialActiveCategoryId: Long = NO_CATEGORY_ID
) {
    var newCategoryText by remember { mutableStateOf("") }
    var activeCategoryId by remember { mutableLongStateOf(initialActiveCategoryId) }

    BasicAlertDialog(
        onDismissRequest = onCancel,
        modifier = modifier,
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            tonalElevation = AlertDialogDefaults.TonalElevation,
            shape = RoundedCornerShape(12.dp),
            color = Neutral.Light.Lightest
        ) {
            ResultContainerComposable(
                container = categories,
                onTryAgain = { }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                    modifier = modifier.padding(18.dp)
                ) {
                    Text(
                        text = stringResource(R.string.select_task_category),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    // Categories list.
                    CategoriesRow(
                        categories = categories,
                        onCategoryClick = { activeCategoryId = it },
                        activeCategoryId = activeCategoryId,
                        firstCategoryLabel = stringResource(R.string.no_category),
                        modifier = Modifier
                            .heightIn(max = 160.dp)
                            .verticalScroll(rememberScrollState())
                    )

                    // New category input.
                    DefaultTextField(
                        text = newCategoryText,
                        onValueChange = { newCategoryText = it },
                        label = { Text(stringResource(R.string.input_new_category_here)) },
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
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
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
    }
}

private fun getSelectedCategory(categories: List<TaskCategory>, id: Long): TaskCategory {
    return categories.firstOrNull { it.id == id } ?: TaskCategory(
        NO_CATEGORY_ID,
        Core.resources.getString(R.string.no_category)
    )
}

@Preview(showSystemUi = true)
@Composable
fun SelectCategoryDialogPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        SelectCategoryDialog(
            categories = ResultContainer.Done(
                listOf(
                    TaskCategory(id = 1, name = "Работа"),
                    TaskCategory(id = 2, name = "Дом"),
                    TaskCategory(id = 3, name = "Личное"),
                    TaskCategory(id = 4, name = "Праздники"),
                    TaskCategory(id = 5, name = "Работа"),
                    TaskCategory(id = 6, name = "Дом"),
                    TaskCategory(id = 7, name = "Личное"),
                    TaskCategory(id = 8, name = "Праздники"),
                    TaskCategory(id = 9, name = "Работа"),
                )
            ),
            onConfirm = { },
            onCancel = { },
            onAddNewCategory = { }
        )
    }
}