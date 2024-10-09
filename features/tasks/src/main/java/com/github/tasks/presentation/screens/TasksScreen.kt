package com.github.tasks.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.github.common.ResultContainer
import com.github.components.colors.Neutral
import com.github.tasks.domain.entities.Task

@Composable
fun TasksScreen(
    activeTasks: ResultContainer<List<Task>>,
    completedTasks: ResultContainer<List<Task>>,
    previousTasks: ResultContainer<List<Task>>,
    futureTasks: ResultContainer<List<Task>>,
    onTaskComplete: (Task) -> Unit,
    onTaskCancelCompletion: (Task) -> Unit,
    onTaskAdd: (Task) -> Unit,
    onTaskDelete: (Task) -> Unit,
) {
    var isAddingTask by rememberSaveable { mutableStateOf(true) }
    var taskText by rememberSaveable { mutableStateOf("") }

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
                            onTaskAdd(Task(text = taskText))
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
        ResultContainer.Done(listOf()),
        ResultContainer.Done(listOf()),
        ResultContainer.Done(listOf()),
        ResultContainer.Done(listOf()),
        { },
        { },
        { },
        { })
}