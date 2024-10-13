package com.github.educationissimple.tasks.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.presentation.ResultContainerComposable
import com.github.educationissimple.tasks.di.TasksDiContainer
import com.github.educationissimple.tasks.di.rememberTasksDiContainer
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.presentation.components.TasksColumn
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
        onTasksEvent = viewModel::onEvent
    )
}


@Composable
fun TasksContent(
    previousTasks: ResultContainer<List<Task>>,
    todayTasks: ResultContainer<List<Task>>,
    futureTasks: ResultContainer<List<Task>>,
    completedTasks: ResultContainer<List<Task>>,
    onTasksEvent: (TasksEvent) -> Unit,
) {
    var isAddingTask by rememberSaveable { mutableStateOf(false) }
    var taskText by rememberSaveable { mutableStateOf("") }

    val onTaskCompletionChange: (Long, Boolean) -> Unit = { taskId, isCompleted ->
        if (isCompleted) {
            onTasksEvent(TasksEvent.CompleteTask(taskId))
        } else {
            onTasksEvent(TasksEvent.CancelTaskCompletion(taskId))
        }
    }

    Column {
        ResultContainerComposable(container = previousTasks, onTryAgain = { }) {
            TasksColumn(
                "Прошлые задачи",
                previousTasks.unwrap(),
                onTaskCompletionChange = onTaskCompletionChange
            )
        }
        ResultContainerComposable(container = todayTasks, onTryAgain = { }) {
            TasksColumn(
                "Задачи на сегодня",
                todayTasks.unwrap(),
                onTaskCompletionChange = onTaskCompletionChange
            )
        }
        ResultContainerComposable(container = futureTasks, onTryAgain = { }) {
            TasksColumn(
                "Будущие задачи",
                futureTasks.unwrap(),
                onTaskCompletionChange = onTaskCompletionChange
            )
        }
        ResultContainerComposable(container = completedTasks, onTryAgain = { }) {
            TasksColumn(
                "Выполненные сегодня задачи",
                completedTasks.unwrap(),
                onTaskCompletionChange = onTaskCompletionChange
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = { isAddingTask = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }


    if (isAddingTask) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clickable { isAddingTask = false },
            color = Color(0x88000000)
        ) {

        }

        Box(Modifier.fillMaxSize()) {
            OutlinedTextField(
                value = taskText,
                onValueChange = { taskText = it },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Neutral.Light.Lightest,
                    focusedBorderColor = Neutral.Light.Darkest,
                    unfocusedContainerColor = Neutral.Light.Lightest,
                    unfocusedBorderColor = Neutral.Light.Darkest,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onTasksEvent(TasksEvent.AddTask(Task(text = taskText)))
                            taskText = ""
                        },
                        colors = IconButtonDefaults.iconButtonColors(contentColor = Neutral.Dark.Lightest)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun TasksContentPreview() {
    TasksContent(
        ResultContainer.Done(
            listOf(
                Task(id = 1, text = "Побегать", isCompleted = false, date = "10-08"),
                Task(id = 3, text = "Полежать", isCompleted = false, date = "10-09")
            )
        ),
        ResultContainer.Done(
            listOf(
                Task(id = 1, text = "Побегать"),
                Task(id = 2, text = "Попрыгать"),
                Task(id = 3, text = "Полежать")
            )
        ),
        ResultContainer.Done(
            listOf()
        ),
        ResultContainer.Done(
            listOf(
                Task(id = 1, text = "Побегать", isCompleted = true, date = "10-08"),
                Task(id = 2, text = "Попрыгать", isCompleted = true),
                Task(id = 3, text = "Полежать", isCompleted = true)
            )
        )
    ) {

    }
}