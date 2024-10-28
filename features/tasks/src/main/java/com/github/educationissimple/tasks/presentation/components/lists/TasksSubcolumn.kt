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
import com.github.educationissimple.presentation.locals.LocalSpacing
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
                .padding(
                    start = LocalSpacing.current.medium,
                    top = LocalSpacing.current.semiMedium,
                    end = LocalSpacing.current.medium
                )
                .clickable { onExpandChange(!isExpanded) }
                .animateItem()
        ) {
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
            TaskListItem(
                task = task,
                onTaskCompletionChange = { isCompleted ->
                    onTaskCompletionChange(task.id, isCompleted)
                },
                onTaskDelete = {
                    onTaskDelete(task.id)
                },
                onPriorityChange = { priority ->
                    onTaskPriorityChange(task.id, priority)
                },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .animateItem()
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
            (1..4).map {
                Task(
                    id = it.toLong(),
                    text = "Задача $it",
                    date = if (it % 2 == 0) "10-08" else null,
                    priority = Task.Priority.fromValue(it)
                )
            },
            onTaskCompletionChange = { _, _ -> },
            onTaskDelete = { },
            onTaskPriorityChange = { _, _ -> }
        )
        tasksSubcolumn(
            "Выполненные задачи",
            (5..8).map {
                Task(
                    id = it.toLong(),
                    text = "Задача $it",
                    isCompleted = true,
                    date = if (it % 2 == 0) "10-08" else null,
                    priority = Task.Priority.fromValue(it - 5)
                )
            },
            onTaskCompletionChange = { _, _ -> },
            onTaskDelete = { },
            onTaskPriorityChange = { _, _ -> }
        )
    }
}