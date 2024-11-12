package com.github.educationissimple.tasks.presentation.components.environment

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.components.composables.buttons.DefaultIconButton
import com.github.educationissimple.components.composables.inputs.DefaultTextField
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.presentation.components.dialogs.ChangeDateDialog
import com.github.educationissimple.tasks.presentation.components.dialogs.SelectCategoryDialog
import com.github.educationissimple.tasks.presentation.components.dialogs.TaskPriorityDialog
import com.github.educationissimple.tasks.presentation.components.items.TaskPropertyItem
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskSheet(
    task: Task,
    categories: ResultContainer<List<TaskCategory>>,
    isSheetOpen: Boolean,
    onTaskChange: (Task) -> Unit,
    onAddNewCategory: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var taskLabel by remember { mutableStateOf(task.text) }
    var taskDescription by remember { mutableStateOf(task.description ?: "") }
    var showTaskDescriptionTextField by remember { mutableStateOf(false) }
    var showDateDialog by remember { mutableStateOf(false) }
    var showPriorityDialog by remember { mutableStateOf(false) }
    var showCategoriesDialog by remember { mutableStateOf(false) }

    if (showDateDialog) {
        ChangeDateDialog(
            onConfirm = {
                onTaskChange(task.copy(date = it))
                showDateDialog = false
            },
            onDismiss = {
                showDateDialog = false
            }
        )
    }

    if (showPriorityDialog) {
        TaskPriorityDialog(
            priority = task.priority,
            onDismiss = { showPriorityDialog = false },
            onPriorityChange = {
                onTaskChange(task.copy(priority = it))
            }
        )
    }

    if (showCategoriesDialog) {
        SelectCategoryDialog(
            title = stringResource(R.string.select_task_category),
            categories = categories,
            onConfirm = {
                showCategoriesDialog = false
                onTaskChange(task.copy(categoryId = it.id))
            },
            onCancel = {
                showCategoriesDialog = false
            },
            onAddNewCategory = onAddNewCategory,
            initialActiveCategoryId = task.id
        )
    }

    LaunchedEffect(isSheetOpen) {
        if (isSheetOpen) {
            sheetState.expand()
        } else {
            sheetState.hide()
        }
    }

    ModalBottomSheet(sheetState = sheetState, onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalSpacing.current.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                DefaultTextField(
                    text = taskLabel,
                    onValueChange = { taskLabel = it },
                    trailingIcon = {
                        if (taskLabel != task.text) {
                            DefaultIconButton(onClick = { onTaskChange(task.copy(text = taskLabel)) }) {
                                Icon(Icons.Default.CheckCircleOutline, contentDescription = null)
                            }
                        }
                    },
                    leadingIcon = {
                        Checkbox(
                            checked = task.isCompleted,
                            onCheckedChange = { onTaskChange(task.copy(isCompleted = it)) },
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = Neutral.Light.Darkest,
                                checkedColor = Highlight.Darkest
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(120.dp))
            HorizontalDivider(thickness = 1.dp, color = Neutral.Dark.Lightest)
            Box(
                modifier = Modifier.animateContentSize()
            ) {
                Column {
                    TaskPropertyItem(
                        iconVector = Icons.Default.Description,
                        label = if (task.description == null) "Добавить описание" else "Изменить описание",
                        rightArrowOpened = showTaskDescriptionTextField,
                        onPropertyClick = {
                            showTaskDescriptionTextField = !showTaskDescriptionTextField
                        }
                    )

                    if (showTaskDescriptionTextField) {
                        DefaultTextField(
                            text = taskDescription,
                            onValueChange = { taskDescription = it },
                            trailingIcon = {
                                if (taskDescription != (task.description ?: "")) {
                                    DefaultIconButton(onClick = { onTaskChange(task.copy(description = taskDescription)) }) {
                                        Icon(
                                            Icons.Default.CheckCircleOutline,
                                            contentDescription = null
                                        )
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            HorizontalDivider(thickness = 1.dp, color = Neutral.Dark.Lightest)
            TaskPropertyItem(
                iconVector = Icons.Default.CalendarMonth,
                label = "Изменить дату задачи",
                onPropertyClick = {
                    showDateDialog = true
                }
            )
            HorizontalDivider(thickness = 1.dp, color = Neutral.Dark.Lightest)
            TaskPropertyItem(
                iconVector = Icons.AutoMirrored.Filled.StarHalf,
                label = "Изменить приоритет задачи",
                onPropertyClick = {
                    showPriorityDialog = true
                }
            )
            HorizontalDivider(thickness = 1.dp, color = Neutral.Dark.Lightest)
            TaskPropertyItem(
                iconVector = Icons.Default.Category,
                label = "Изменить категорию задачи",
                onPropertyClick = {
                    showCategoriesDialog = true
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TaskSheetPreview() {
    TaskSheet(
        task = Task(
            id = 1,
            text = "Task",
            isCompleted = false,
            date = LocalDate.now(),
            priority = Task.Priority.fromValue(1)
        ),
        categories = ResultContainer.Done(listOf()),
        isSheetOpen = true,
        onTaskChange = {},
        onAddNewCategory = {},
        onDismiss = {}
    )
}