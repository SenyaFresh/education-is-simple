package com.github.educationissimple.tasks.presentation.components.environment

import android.Manifest
import android.os.Build
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.composables.buttons.DefaultIconButton
import com.github.educationissimple.components.composables.inputs.DefaultTextField
import com.github.educationissimple.components.composables.items.ActionableListItem
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskCategory.Companion.NO_CATEGORY_ID
import com.github.educationissimple.tasks.domain.entities.TaskReminder
import com.github.educationissimple.tasks.presentation.components.dialogs.ChangeDateDialog
import com.github.educationissimple.tasks.presentation.components.dialogs.DeniedNotificationsPermissionDialog
import com.github.educationissimple.tasks.presentation.components.dialogs.PickDateTimeDialog
import com.github.educationissimple.tasks.presentation.components.dialogs.SelectCategoryDialog
import com.github.educationissimple.tasks.presentation.components.dialogs.TaskPriorityDialog
import com.github.educationissimple.tasks.presentation.components.items.TaskPropertyItem
import com.github.educationissimple.tasks.presentation.components.lists.RemindersList
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun TaskSheet(
    task: Task,
    categories: ResultContainer<List<TaskCategory>>,
    reminders: ResultContainer<List<TaskReminder>>,
    onAddNewCategory: (String) -> Unit,
    isSheetOpen: Boolean,
    onTaskUpdate: (Task) -> Unit,
    onCreateReminder: (TaskReminder) -> Unit,
    onDeleteReminder: (TaskReminder) -> Unit,
    onDismiss: () -> Unit,
    onReloadCategories: () -> Unit,
    onReloadReminders: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var updatedTask by remember { mutableStateOf(task) }
    var showTaskDescriptionTextField by remember { mutableStateOf(false) }
    var showDateDialog by remember { mutableStateOf(false) }
    var showPriorityDialog by remember { mutableStateOf(false) }
    var showCategoriesDialog by remember { mutableStateOf(false) }
    var showReminders by remember { mutableStateOf(false) }
    var showReminderCreatorDialog by remember { mutableStateOf(false) }
    var showNotificationsPermissionDialog by remember { mutableStateOf(false) }

    val notificationsPermissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        null
    }

    if (showDateDialog) {
        ChangeDateDialog(
            onConfirm = {
                updatedTask = updatedTask.copy(date = it)
                showDateDialog = false
            },
            onDismiss = {
                showDateDialog = false
            },
            initialDate = updatedTask.date ?: LocalDate.now()
        )
    }

    if (showPriorityDialog) {
        TaskPriorityDialog(
            priority = updatedTask.priority,
            onDismiss = { showPriorityDialog = false },
            onPriorityChange = {
                updatedTask = updatedTask.copy(priority = it)
            }
        )
    }

    if (showCategoriesDialog) {
        SelectCategoryDialog(
            title = stringResource(R.string.select_task_category),
            categories = categories,
            onConfirm = {
                showCategoriesDialog = false
                updatedTask = updatedTask.copy(categoryId = it.id)
            },
            onCancel = {
                showCategoriesDialog = false
            },
            onReloadCategories = onReloadCategories,
            onAddNewCategory = onAddNewCategory,
            initialActiveCategoryId = updatedTask.categoryId ?: NO_CATEGORY_ID
        )
    }

    if (showReminderCreatorDialog) {
        when (notificationsPermissionState?.status) {
            is PermissionStatus.Denied -> {
                showNotificationsPermissionDialog = true
            }
            else -> {
                PickDateTimeDialog(
                    onConfirm = {
                        onCreateReminder(TaskReminder(taskId = task.id, taskText = task.text, datetime = it))
                        showReminderCreatorDialog = false
                    },
                    onDismiss = { showReminderCreatorDialog = false },
                )
            }
        }
    }

    if (showNotificationsPermissionDialog) {
        DeniedNotificationsPermissionDialog(
            notificationsPermissionState = notificationsPermissionState!!,
            onDismiss = {
                showNotificationsPermissionDialog = false
                showReminderCreatorDialog = false
            }
        )
    }

    LaunchedEffect(isSheetOpen) {
        if (isSheetOpen) {
            sheetState.expand()
        } else {
            sheetState.hide()
        }
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalSpacing.current.medium),
            horizontalAlignment = Alignment.Start
        ) {
            Row {
                ActionableListItem(
                    label = categories.unwrapOrNull()
                        ?.find { it.id == updatedTask.categoryId }?.name
                        ?: stringResource(R.string.no_category),
                    onClick = { showCategoriesDialog = true }
                )
                Spacer(modifier = Modifier.weight(1f))
                if (task != updatedTask) {
                    DefaultIconButton(
                        onClick = {
                            onTaskUpdate(updatedTask)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Checkbox(
                        checked = updatedTask.isCompleted,
                        onCheckedChange = { updatedTask = task.copy(isCompleted = it) },
                        modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                    )
                    DefaultTextField(
                        text = updatedTask.text,
                        onValueChange = { updatedTask = task.copy(text = it) },
                        containerColor = Color.Transparent,
                        textStyle = TextStyle.Default.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset((-12).dp, 0.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(LocalSpacing.current.semiMedium))
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column {
                    Box(
                        modifier = Modifier.animateContentSize()
                    ) {
                        Column {
                            TaskPropertyItem(
                                iconVector = Icons.Default.Description,
                                label = stringResource(R.string.Description),
                                rightArrowOpened = showTaskDescriptionTextField,
                                onPropertyClick = {
                                    showTaskDescriptionTextField = !showTaskDescriptionTextField
                                }
                            )

                            if (showTaskDescriptionTextField) {
                                DefaultTextField(
                                    text = updatedTask.description ?: "",
                                    onValueChange = { updatedTask = task.copy(description = it) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(LocalSpacing.current.small)
                                        .height(100.dp),
                                    placeholder = { Text(stringResource(R.string.add_task_description), fontSize = 14.sp) }
                                )
                            }
                        }
                    }
                    TaskPropertyItem(
                        iconVector = Icons.Default.CalendarMonth,
                        label = stringResource(R.string.task_date),
                        onPropertyClick = {
                            showDateDialog = true
                        }
                    )
                    TaskPropertyItem(
                        iconVector = Icons.AutoMirrored.Filled.StarHalf,
                        label = stringResource(R.string.task_priority),
                        onPropertyClick = {
                            showPriorityDialog = true
                        }
                    )
                    Box(
                        modifier = Modifier.animateContentSize()
                    ) {
                        Column {
                            TaskPropertyItem(
                                iconVector = Icons.Default.Notifications,
                                rightArrowOpened = showReminders,
                                label = stringResource(R.string.reminders),
                                onPropertyClick = {
                                    showReminders = !showReminders
                                }
                            )

                            if (showReminders) {
                                RemindersList(
                                    reminders = reminders,
                                    onDelete = onDeleteReminder,
                                    onCreate = { showReminderCreatorDialog = true },
                                    onReloadReminders = onReloadReminders
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
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
        categories = ResultContainer.Done(emptyList()),
        reminders = ResultContainer.Done(emptyList()),
        isSheetOpen = true,
        onTaskUpdate = {},
        onAddNewCategory = {},
        onDismiss = {},
        onCreateReminder = {},
        onDeleteReminder = {},
        onReloadCategories = {},
        onReloadReminders = {}
    )
}