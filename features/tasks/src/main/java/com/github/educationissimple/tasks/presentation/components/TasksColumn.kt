package com.github.educationissimple.tasks.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.educationissimple.tasks.domain.entities.Task


@Composable
fun TasksColumn(title: String, tasks: List<Task>) {

    var isListVisible by rememberSaveable { mutableStateOf(true) }

    Column(verticalArrangement = Arrangement.Top) {
        Row(
            Modifier
                .padding(start = 12.dp, top = 8.dp, end = 12.dp)
                .clickable { isListVisible = !isListVisible }) {
            Text(text = title, fontWeight = FontWeight.Bold)
            if (isListVisible) {
                Icon(
                    Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            } else {
                Icon(
                    Icons.Filled.KeyboardArrowUp,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        if (isListVisible) {
            LazyColumn(contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 8.dp)) {
                items(
                    items = tasks,
                    key = { task -> task.id }
                ) { task ->
                    TaskCard(
                        isCompleted = task.isCompleted,
                        text = task.text,
                        date = task.date,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun TasksColumnPreview() {
    Column {
        TasksColumn(
            "Задачи на сегодня",
            listOf(
                Task(id = 1, text = "Побегать"),
                Task(id = 2, text = "Попрыгать"),
                Task(id = 3, text = "Полежать")
            )
        )

        TasksColumn(
            "Выполненные сегодня задачи",
            listOf(
                Task(id = 1, text = "Побегать", isCompleted = true, date = "10-08"),
                Task(id = 2, text = "Попрыгать", isCompleted = true),
                Task(id = 3, text = "Полежать", isCompleted = true)
            )
        )
    }

}