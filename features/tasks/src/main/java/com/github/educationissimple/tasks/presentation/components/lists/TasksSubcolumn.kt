package com.github.educationissimple.tasks.presentation.components.lists

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
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.presentation.components.items.TaskListItem


fun LazyListScope.tasksSubcolumn(
    title: String,
    tasksContainer: List<Task>,
    onTaskCompletionChange: (Long, Boolean) -> Unit,
    onTaskDelete: (Long) -> Unit,
    onTaskPriorityChange: (Long, Task.Priority) -> Unit,
    isExpanded: Boolean = true,
    onExpandChange: (Boolean) -> Unit = {}
) {

    if (tasksContainer.isEmpty()) return

    // Subcolumn title.
    item {
        Row(
            Modifier
                .padding(start = 12.dp, top = 8.dp, end = 12.dp)
                .clickable { onExpandChange(!isExpanded) }) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Icon(
                if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                modifier = Modifier.size(16.dp)
            )
        }
    }

    // Subcolumn content.
    if (isExpanded) {
        items(items = tasksContainer, key = { task -> task.id }) { task ->
            TaskListItem(task = task,
                modifier = Modifier.padding(top = 8.dp),
                onTaskCompletionChange = { isCompleted ->
                    onTaskCompletionChange(task.id, isCompleted)
                },
                onTaskDelete = {
                    onTaskDelete(task.id)
                },
                onPriorityChange = { priority ->
                    onTaskPriorityChange(task.id, priority)
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TasksColumnPreview() {
    LazyColumn {
        tasksSubcolumn(
            "Задачи на сегодня",
            listOf(
                Task(id = 1, text = "Побегать"),
                Task(id = 2, text = "Попрыгать"),
                Task(id = 3, text = "Полежать")
            ),
            onTaskCompletionChange = { _, _ -> },
            onTaskDelete = { },
            onTaskPriorityChange = { _, _ -> }
        )
        tasksSubcolumn(
            "Выполненные сегодня задачи",
            listOf(
                Task(id = 4, text = "Побегать", isCompleted = true, date = "10-08"),
                Task(id = 5, text = "Попрыгать", isCompleted = true),
                Task(id = 6, text = "Полежать", isCompleted = true)
            ),
            onTaskCompletionChange = { _, _ -> },
            onTaskDelete = { },
            onTaskPriorityChange = { _, _ -> }
        )
    }
}