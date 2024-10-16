package com.github.educationissimple.tasks.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.composables.ScreenDimming
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.di.TasksDiContainer
import com.github.educationissimple.tasks.di.rememberTasksDiContainer
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.presentation.components.AddTaskFloatingActionButton
import com.github.educationissimple.tasks.presentation.components.AllTasksColumn
import com.github.educationissimple.tasks.presentation.components.CategoriesRow
import com.github.educationissimple.tasks.presentation.components.PopUpTextField
import com.github.educationissimple.tasks.presentation.events.TasksEvent
import com.github.educationissimple.tasks.presentation.viewmodels.TasksViewModel


@Composable
fun TasksScreen(
    diContainer: TasksDiContainer = rememberTasksDiContainer(),
    viewModel: TasksViewModel = viewModel(factory = diContainer.viewModelFactory)
) {
    TasksContent(
        previousTasks = viewModel.previousTasks.collectAsState().value,
        todayTasks = viewModel.todayTasks.collectAsState().value,
        futureTasks = viewModel.futureTasks.collectAsState().value,
        completedTasks = viewModel.completedTasks.collectAsState().value,
        categories = viewModel.categories.collectAsState().value,
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
    onTasksEvent: (TasksEvent) -> Unit,
) {
    var activeCategoryId by rememberSaveable { mutableLongStateOf(0L) }
    var isAddingTask by rememberSaveable { mutableStateOf(false) }
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

    LaunchedEffect(activeCategoryId) {
        onTasksEvent(TasksEvent.ChangeCategory(if (activeCategoryId != 0L) activeCategoryId else null))
    }

    Column {
        CategoriesRow(
            categories = categories,
            activeCategoryId = activeCategoryId,
            onCategoryClick = {
                onTasksEvent(TasksEvent.ChangeCategory(it))
                activeCategoryId = it
            },
            firstItemLabel = stringResource(R.string.all),
            maxLines = 1,
            modifier = Modifier
                .padding(12.dp)
                .horizontalScroll(rememberScrollState())
        )

        AllTasksColumn(
            previousTasks = previousTasks,
            todayTasks = todayTasks,
            futureTasks = futureTasks,
            completedTasks = completedTasks,
            onTaskDelete = onTaskDelete,
            onTaskCompletionChange = onTaskCompletionChange
        )
    }

    if (!isAddingTask) {
        Box(modifier = Modifier.fillMaxSize()) {
            AddTaskFloatingActionButton(
                onClick = { isAddingTask = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp)
            )
        }
    } else {
        BackHandler {
            isAddingTask = false
        }

        LaunchedEffect(isAddingTask) {
            focusRequester.requestFocus()
        }

        ScreenDimming { isAddingTask = false }

        PopUpTextField(
            text = taskText,
            onValueChange = { taskText = it },
            onAddClick = { selectedCategoryId ->
                onTasksEvent(
                    TasksEvent.AddTask(
                        Task(
                            text = taskText,
                            categoryId = if (selectedCategoryId == 0L) null else selectedCategoryId
                        )
                    )
                )
                taskText = ""
            },
            onAddNewCategory = { categoryName ->
                onTasksEvent(TasksEvent.AddCategory(categoryName))
            },
            focusRequester = focusRequester,
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