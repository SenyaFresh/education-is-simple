package com.github.educationissimple.tasks.presentation.screens

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.components.composables.buttons.AddFloatingActionButton
import com.github.educationissimple.components.composables.environment.SearchBar
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.di.TasksDiContainer
import com.github.educationissimple.tasks.di.rememberTasksDiContainer
import com.github.educationissimple.tasks.domain.entities.SortType
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskCategory.Companion.NO_CATEGORY_ID
import com.github.educationissimple.tasks.presentation.components.dialogs.TasksSortDialog
import com.github.educationissimple.tasks.presentation.components.environment.PopUpTextField
import com.github.educationissimple.tasks.presentation.components.lists.AllTasksColumn
import com.github.educationissimple.tasks.presentation.components.lists.CategoriesRow
import com.github.educationissimple.tasks.presentation.events.TasksEvent
import com.github.educationissimple.tasks.presentation.viewmodels.TasksViewModel
import java.time.LocalDate

@Composable
fun TasksScreen(
    diContainer: TasksDiContainer = rememberTasksDiContainer(),
    viewModel: TasksViewModel = viewModel(factory = diContainer.viewModelFactory),
    searchEnabled: Boolean,
    onSearchEnabledChange: (Boolean) -> Unit
) {
    TasksContent(
        previousTasks = viewModel.previousTasks.collectAsStateWithLifecycle().value,
        todayTasks = viewModel.todayTasks.collectAsStateWithLifecycle().value,
        futureTasks = viewModel.futureTasks.collectAsStateWithLifecycle().value,
        completedTasks = viewModel.completedTasks.collectAsStateWithLifecycle().value,
        categories = viewModel.categories.collectAsStateWithLifecycle().value,
        searchEnabled = searchEnabled,
        onSearchEnabledChange = onSearchEnabledChange,
        currentSortType = viewModel.sortType.collectAsStateWithLifecycle().value.unwrapOrNull(),
        activeCategoryId = viewModel.activeCategoryId.collectAsStateWithLifecycle().value.unwrapOrNull()
            ?: NO_CATEGORY_ID,
        onTasksEvent = viewModel::onEvent
    )
}

@Composable
fun TasksContent(
    previousTasks: ResultContainer<List<Task>>,
    todayTasks: ResultContainer<List<Task>>,
    futureTasks: ResultContainer<List<Task>>,
    completedTasks: ResultContainer<List<Task>>,
    categories: ResultContainer<List<TaskCategory>>,
    searchEnabled: Boolean,
    onSearchEnabledChange: (Boolean) -> Unit,
    currentSortType: SortType? = null,
    activeCategoryId: Long = NO_CATEGORY_ID,
    onTasksEvent: (TasksEvent) -> Unit,
) {
    var isAddingTask by rememberSaveable { mutableStateOf(false) }
    var showSortTypeDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var taskText by rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    val onTaskCompletionChange: (Long, Boolean) -> Unit = { taskId, isCompleted ->
        if (isCompleted) {
            onTasksEvent(TasksEvent.CompleteTask(taskId))
        } else {
            onTasksEvent(TasksEvent.CancelTaskCompletion(taskId))
        }
    }

    val onTaskDelete: (Long) -> Unit = { taskId ->
        onTasksEvent(TasksEvent.DeleteTask(taskId))
    }

    val onTaskPriorityChange: (Long, Task.Priority) -> Unit = { taskId, priority ->
        onTasksEvent(TasksEvent.ChangeTaskPriority(taskId, priority))
    }

    val onTaskDateChange: (Long, LocalDate) -> Unit = { taskId, date ->
        onTasksEvent(TasksEvent.ChangeTaskDate(taskId, date))
    }

    if (showSortTypeDialog) {
        TasksSortDialog(
            onDismiss = { showSortTypeDialog = false },
            onSortTypeChange = {
                onTasksEvent(TasksEvent.ChangeSortType(it))
            },
            sortType = currentSortType
        )
    }

    LaunchedEffect(searchQuery) {
        onTasksEvent(TasksEvent.ChangeTaskSearchText(searchQuery))
    }

    Column {
        Crossfade(
            targetState = searchEnabled,
            label = "Show search bar",
            animationSpec = tween(100),
            modifier = Modifier.animateContentSize()
        ) { state ->
            if (state) {
                SearchBar(
                    text = searchQuery,
                    onValueChange = { searchQuery = it },
                    onCancelClick = {
                        onSearchEnabledChange(false)
                        searchQuery = ""
                    },
                    label = stringResource(R.string.search_task),
                    modifier = Modifier.padding(LocalSpacing.current.small)
                )
            } else {
                // Выбор категории и меню.
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        enabled = categories is ResultContainer.Done,
                        onClick = { showSortTypeDialog = true },
                        colors = IconButtonDefaults.iconButtonColors(contentColor = Neutral.Dark.Darkest),
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.Sort, contentDescription = null)
                    }

                    CategoriesRow(
                        categories = categories,
                        activeCategoryId = activeCategoryId,
                        onCategoryClick = {
                            onTasksEvent(TasksEvent.ChangeCategory(if (it != NO_CATEGORY_ID) it else null))
                        },
                        firstCategoryLabel = stringResource(R.string.all),
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .padding(end = LocalSpacing.current.small),
                        maxLines = 1
                    )
                }
            }
        }

        // Список задач.
        AllTasksColumn(
            previousTasks = previousTasks,
            todayTasks = todayTasks,
            futureTasks = futureTasks,
            completedTasks = completedTasks,
            onTaskDelete = onTaskDelete,
            onTaskPriorityChange = onTaskPriorityChange,
            onTaskDateChange = onTaskDateChange,
            onTaskCompletionChange = onTaskCompletionChange
        )
    }

    if (!isAddingTask) {
        if (ResultContainer.wrap(
                previousTasks,
                todayTasks,
                futureTasks,
                completedTasks,
                categories
            ) is ResultContainer.Done
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AddFloatingActionButton(
                    onClick = { isAddingTask = true },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(LocalSpacing.current.medium)
                )
            }
        }
    } else {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        PopUpTextField(
            text = taskText,
            onValueChange = { taskText = it },
            onAddClick = { selectedCategoryId, selectedDate, taskPriority ->
                onTasksEvent(
                    TasksEvent.AddTask(
                        Task(
                            text = taskText,
                            categoryId = if (selectedCategoryId == NO_CATEGORY_ID) null else selectedCategoryId,
                            date = selectedDate,
                            priority = taskPriority
                        )
                    )
                )
                taskText = ""
            },
            onAddNewCategory = { categoryName ->
                onTasksEvent(TasksEvent.AddCategory(categoryName))
            },
            focusRequester = focusRequester,
            onDismiss = { isAddingTask = false },
            categories = categories
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun TasksContentPreview() {
    val tasks = (1..8).map {
        Task(
            id = it.toLong(),
            text = "Задача $it",
            date = if (it % 2 == 0) LocalDate.now() else null,
            priority = Task.Priority.fromValue(it)
        )
    }

    TasksContent(
        previousTasks = ResultContainer.Done(tasks.subList(0, 2)),
        todayTasks = ResultContainer.Done(tasks.subList(2, 4)),
        futureTasks = ResultContainer.Done(tasks.subList(4, 6)),
        completedTasks = ResultContainer.Done(
            tasks.subList(6, 8).map { it.copy(isCompleted = true) }),
        categories = ResultContainer.Done(
            (1..5).map {
                TaskCategory(it.toLong(), "Category $it")
            }
        ),
        searchEnabled = false,
        onSearchEnabledChange = {},
        currentSortType = null,
        onTasksEvent = {},
    )
}

@Preview(showSystemUi = true)
@Composable
fun TasksScreenPreviewLoading() {
    TasksContent(
        previousTasks = ResultContainer.Loading,
        todayTasks = ResultContainer.Loading,
        futureTasks = ResultContainer.Loading,
        completedTasks = ResultContainer.Loading,
        categories = ResultContainer.Loading,
        searchEnabled = false,
        onSearchEnabledChange = {},
        currentSortType = null,
        onTasksEvent = {},
    )
}