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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskReminder
import com.github.educationissimple.tasks.presentation.components.items.TaskListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate


fun LazyListScope.tasksSubcolumn(
    title: String,
    tasksContainer: List<Task>,
    onTaskDelete: (Long) -> Unit,
    onUpdateTask: (Task) -> Unit,
    getRemindersForTask: (Long) -> StateFlow<ResultContainer<List<TaskReminder>>>,
    onDeleteReminder: (TaskReminder) -> Unit,
    onCreateReminder: (TaskReminder) -> Unit,
    categories: ResultContainer<List<TaskCategory>>,
    onAddNewCategory: (String) -> Unit,
    isExpanded: Boolean = true,
    onExpandChange: (Boolean) -> Unit = {}
) {

    if (tasksContainer.isEmpty()) return

    // Subcolumn title.
    item {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
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
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Icon(
                if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                modifier = Modifier.size(20.dp)
            )
        }
    }

    // Subcolumn content.
    if (isExpanded) {
        items(
            items = tasksContainer,
            key = { task -> listOf(task.id, task.isCompleted, task.date.toString()) }) { task ->
            TaskListItem(
                task = task,
                onTaskDelete = {
                    onTaskDelete(task.id)
                },
                onTaskUpdate = {
                    onUpdateTask(it)
                },
                categories = categories,
                getReminders = { getRemindersForTask(task.id) },
                onCreateReminder = onCreateReminder,
                onDeleteReminder = onDeleteReminder,
                onAddNewCategory = onAddNewCategory,
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
            "Задачи",
            (0..8).map {
                Task(
                    id = it.toLong(),
                    text = "Задача $it",
                    isCompleted = (it % 2 == 0),
                    date = if (it % 2 == 0) LocalDate.now() else null,
                    priority = Task.Priority.fromValue(it - 5)
                )
            },
            onTaskDelete = {},
            onUpdateTask = {},
            categories = ResultContainer.Done(emptyList()),
            onAddNewCategory = {},
            getRemindersForTask = { MutableStateFlow(ResultContainer.Done(emptyList())) },
            onDeleteReminder = {},
            onCreateReminder = {}
        )
    }
}