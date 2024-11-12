package com.github.educationissimple.tasks.presentation.components.lists

import androidx.compose.animation.core.tween
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
import java.time.LocalDate


fun LazyListScope.tasksSubcolumn(
    title: String,
    tasksContainer: List<Task>,
    onTaskDelete: (Long) -> Unit,
    onUpdateTask: (Task) -> Unit,
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
                .animateItem(
                    fadeInSpec = tween(150),
                    fadeOutSpec = tween(150),
                    placementSpec = tween(150)
                )
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
                    onUpdateTask(task.copy(isCompleted = isCompleted))
                },
                onTaskDelete = {
                    onTaskDelete(task.id)
                },
                onPriorityChange = { priority ->
                    onUpdateTask(task.copy(priority = priority))
                },
                onDateChange = { date ->
                    onUpdateTask(task.copy(date = date))
                },
                modifier = Modifier
                    .padding(top = LocalSpacing.current.small)
                    .animateItem(
                        fadeInSpec = tween(150),
                        fadeOutSpec = tween(150),
                        placementSpec = tween(150)
                    )
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
                    date = if (it % 2 == 0) LocalDate.now() else null,
                    priority = Task.Priority.fromValue(it)
                )
            },
            onTaskDelete = {},
            onUpdateTask = {}
        )
        tasksSubcolumn(
            "Выполненные задачи",
            (5..8).map {
                Task(
                    id = it.toLong(),
                    text = "Задача $it",
                    isCompleted = true,
                    date = if (it % 2 == 0) LocalDate.now() else null,
                    priority = Task.Priority.fromValue(it - 5)
                )
            },
            onTaskDelete = {},
            onUpdateTask = {}
        )
    }
}