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

/**
 * Displays a collapsible list of tasks within a subcolumn, with the ability to expand and collapse the content.
 *
 * This composable function creates a subcolumn for displaying a list of tasks. It includes a title that can be clicked to toggle the visibility of the task list.
 * Each task in the list can be interacted with (deleted, updated) and can have reminders associated with it. The content can be expanded or collapsed based on the [isExpanded] state,
 * which is controlled by the [onExpandChange] callback.
 *
 * - The subcolumn header contains the [title] and an icon (either an up or down arrow) indicating whether the content is expanded or collapsed.
 * - The task items are displayed with a fade-in/fade-out animation when the subcolumn is expanded or collapsed.
 * - For each task, a [TaskListItem] is displayed that provides options to delete, update, and manage reminders.
 * - The task list is shown only if it is not empty.
 *
 * The [tasksContainer] holds the list of tasks to be displayed, and the [getRemindersForTask] function provides the reminders for each individual task.
 * The task's categories and reminder actions (create, delete, and reload) are also handled through the provided callbacks.
 *
 * @param title The title of the subcolumn, displayed above the task list.
 * @param tasksContainer A list of [Task] objects representing the tasks to be displayed in the subcolumn.
 * @param onTaskDelete A callback function invoked when a task is deleted. It receives the task ID as a parameter.
 * @param onUpdateTask A callback function invoked when a task is updated. It receives the updated [Task] object as a parameter.
 * @param getRemindersForTask A function that returns a [StateFlow] containing the list of reminders for a given task.
 * @param onDeleteReminder A callback function invoked when a reminder is deleted. It receives the [TaskReminder] to be deleted.
 * @param onCreateReminder A callback function invoked when a new reminder is created. It receives the [TaskReminder] to be created.
 * @param categories A [ResultContainer] containing the list of [TaskCategory] objects associated with the tasks.
 * @param onAddNewCategory A callback function invoked to add a new category. It receives the name of the category as a parameter.
 * @param onReloadCategories A callback function invoked to reload the categories list.
 * @param onReloadReminders A callback function invoked to reload the reminders list.
 * @param isExpanded A boolean value indicating whether the task list is expanded or collapsed. Default value is `true`.
 * @param onExpandChange A callback function invoked when the expand/collapse state changes. It receives the new state (`true` for expanded, `false` for collapsed).
 */
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
    onReloadCategories: () -> Unit,
    onReloadReminders: () -> Unit,
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
                onReloadCategories = onReloadCategories,
                onReloadReminders = onReloadReminders,
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
            onCreateReminder = {},
            onReloadCategories = {},
            onReloadReminders = {}
        )
    }
}