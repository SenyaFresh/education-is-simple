package com.github.educationissimple.tasks.presentation.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.di.TasksDiContainer
import com.github.educationissimple.tasks.di.rememberTasksDiContainer
import com.github.educationissimple.tasks.domain.entities.SortType
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskCategory.Companion.NO_CATEGORY_ID
import com.github.educationissimple.tasks.presentation.components.dialogs.TasksSortDialog
import com.github.educationissimple.tasks.presentation.components.environment.AddTaskFloatingActionButton
import com.github.educationissimple.tasks.presentation.components.environment.PopUpTextField
import com.github.educationissimple.tasks.presentation.components.environment.TasksListActionsDropdownMenu
import com.github.educationissimple.tasks.presentation.components.items.SearchBar
import com.github.educationissimple.tasks.presentation.components.lists.AllTasksColumn
import com.github.educationissimple.tasks.presentation.components.lists.CategoriesRow
import com.github.educationissimple.tasks.presentation.events.TasksEvent
import com.github.educationissimple.tasks.presentation.viewmodels.TasksViewModel


@Composable
fun TasksScreen(
    diContainer: TasksDiContainer = rememberTasksDiContainer(),
    viewModel: TasksViewModel = viewModel(factory = diContainer.viewModelFactory)
) {
    TasksContent(
        previousTasks = viewModel.previousTasks.collectAsStateWithLifecycle().value,
        todayTasks = viewModel.todayTasks.collectAsStateWithLifecycle().value,
        futureTasks = viewModel.futureTasks.collectAsStateWithLifecycle().value,
        completedTasks = viewModel.completedTasks.collectAsStateWithLifecycle().value,
        categories = viewModel.categories.collectAsStateWithLifecycle().value,
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
    currentSortType: SortType? = null,
    activeCategoryId: Long = NO_CATEGORY_ID,
    onTasksEvent: (TasksEvent) -> Unit,
) {
    var isAddingTask by rememberSaveable { mutableStateOf(false) }
    var showSortTypeDialog by remember { mutableStateOf(false) }
    var showSearchBar by remember { mutableStateOf(false) }
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
        if (showSearchBar) {
            SearchBar(
                text = searchQuery,
                onValueChange = { searchQuery = it },
                onCancelClick = {
                    showSearchBar = false
                    searchQuery = ""
                },
                modifier = Modifier.padding(LocalSpacing.current.small)
            )
        } else {
            // Выбор категории и меню.
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val buttonSize by remember { mutableFloatStateOf(52.dp.value) }

                CategoriesRow(
                    categories = categories,
                    activeCategoryId = activeCategoryId,
                    onCategoryClick = {
                        onTasksEvent(TasksEvent.ChangeCategory(if (it != NO_CATEGORY_ID) it else null))
                    },
                    firstCategoryLabel = stringResource(R.string.all),
                    modifier = Modifier
                        .padding(start = LocalSpacing.current.semiMedium)
                        .width(LocalConfiguration.current.screenWidthDp.dp - buttonSize.dp)
                        .horizontalScroll(rememberScrollState()),
                    maxLines = 1
                )

                if (categories is ResultContainer.Done) {
                    TasksListActionsDropdownMenu(
                        buttonSize = buttonSize,
                        onSortTypeItemClick = { showSortTypeDialog = true },
                        onFindItemClick = { showSearchBar = true }
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
            onTaskCompletionChange = onTaskCompletionChange
        )
    }

    if (!isAddingTask) {
        Box(modifier = Modifier.fillMaxSize()) {
            AddTaskFloatingActionButton(
                onClick = { isAddingTask = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(LocalSpacing.current.medium)
            )
        }
    } else {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        PopUpTextField(
            text = taskText,
            onValueChange = { taskText = it },
            onAddClick = { selectedCategoryId ->
                onTasksEvent(
                    TasksEvent.AddTask(
                        Task(
                            text = taskText,
                            categoryId = if (selectedCategoryId == NO_CATEGORY_ID) null else selectedCategoryId
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

// Wont render cause Core.init isn't initialized.
@Preview(showSystemUi = true)
@Composable
fun TasksContentPreview() {
    TasksContent(
        ResultContainer.Done(
            listOf(
                Task(id = 1, text = "Побегать", isCompleted = false, date = "10-08"),
                Task(id = 2, text = "Полежать", isCompleted = false, date = "10-09")
            )
        ),
        ResultContainer.Done(
            listOf(
                Task(id = 3, text = "Побегать"),
                Task(id = 4, text = "Попрыгать"),
                Task(id = 5, text = "Полежать")
            )
        ),
        ResultContainer.Done(
            listOf()
        ),
        ResultContainer.Done(
            listOf(
                Task(id = 6, text = "Побегать", isCompleted = true, date = "10-08"),
                Task(id = 7, text = "Попрыгать", isCompleted = true),
                Task(id = 8, text = "Полежать", isCompleted = true)
            )
        ),
        ResultContainer.Done(
            listOf(
                TaskCategory(id = 1, name = "Work"),
                TaskCategory(id = 2, name = "Home")
            )
        )
    ) {
    }
}