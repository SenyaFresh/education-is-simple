package com.github.educationissimple.tasks.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.presentation.components.TasksColumn
import com.github.educationissimple.tasks.presentation.events.TasksEvent

@Composable
fun TasksScreen(
    previousTasks: ResultContainer<List<Task>>,
    todayTasks: ResultContainer<List<Task>>,
    futureTasks: ResultContainer<List<Task>>,
    completedTasks: ResultContainer<List<Task>>,
    onTasksEvent: (TasksEvent) -> Unit,
) {
    var isAddingTask by rememberSaveable { mutableStateOf(false) }
    var taskText by rememberSaveable { mutableStateOf("") }

    Column {
        TasksColumn("Прошлые задачи", previousTasks.unwrap())
        TasksColumn("Задачи на сегодня", todayTasks.unwrap())
        TasksColumn("Будущие задачи", futureTasks.unwrap())
        TasksColumn("Выполненные сегодня задачи", completedTasks.unwrap())
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
fun TasksScreenPreview() {
    TasksScreen(
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