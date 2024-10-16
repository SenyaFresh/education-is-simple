package com.github.educationissimple.tasks.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.tasks.domain.entities.Task


fun LazyListScope.tasksSubcolumn(
    title: String,
    tasksContainer: ResultContainer<List<Task>>,
    onTaskCompletionChange: (Long, Boolean) -> Unit,
    onTaskDelete: (Long) -> Unit,
    isExpanded: Boolean = true,
    onExpandChange: (Boolean) -> Unit = {}
) {

    if (tasksContainer == ResultContainer.Loading ||
        tasksContainer is ResultContainer.Error ||
        tasksContainer.unwrap().isEmpty()) {
        return
    }

    item {
        Row(
            Modifier
                .padding(start = 12.dp, top = 8.dp, end = 12.dp)
                .clickable { onExpandChange(!isExpanded) }) {
            Text(text = title, fontWeight = FontWeight.Bold)
            if (isExpanded) {
                Icon(
                    Icons.Filled.KeyboardArrowUp,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            } else {
                Icon(
                    Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }

    if (isExpanded) {
        items(items = tasksContainer.unwrap(), key = { task -> task.id }) { task ->
            TaskCard(isCompleted = task.isCompleted,
                text = task.text,
                date = task.date,
                modifier = Modifier.padding(top = 8.dp),
                onTaskCompletionChange = {
                    onTaskCompletionChange(task.id, it)
                },
                onTaskDelete = {
                    onTaskDelete(task.id)
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TasksColumnPreview() {
    LazyColumn() {
        tasksSubcolumn(
            "Задачи на сегодня",
            ResultContainer.Done(
                listOf(
                    Task(id = 1, text = "Побегать"),
                    Task(id = 2, text = "Попрыгать"),
                    Task(id = 3, text = "Полежать")
                )
            ),
            onTaskCompletionChange = { _, _ -> },
            onTaskDelete = { }
        )
        tasksSubcolumn(
            "Выполненные сегодня задачи",
            ResultContainer.Done(
                listOf(
                    Task(id = 4, text = "Побегать", isCompleted = true, date = "10-08"),
                    Task(id = 5, text = "Попрыгать", isCompleted = true),
                    Task(id = 6, text = "Полежать", isCompleted = true)
                )
            ),
            onTaskCompletionChange = { _, _ -> },
            onTaskDelete = { }
        )
    }
}