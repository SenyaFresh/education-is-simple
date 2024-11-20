package com.github.educationissimple.tasks.presentation.components.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.presentation.ResultContainerComposable
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskReminder
import com.github.educationissimple.tasks.presentation.components.items.LoadingTaskListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

/**
 * Displays all tasks grouped into different categories: previous tasks, today's tasks, future tasks, and completed tasks.
 *
 * This composable function organizes tasks into separate sections with expandable/collapsible lists for each group:
 * "Previous Tasks", "Today's Tasks", "Future Tasks", and "Completed Tasks". Each section can be expanded or collapsed based on user interaction.
 * When a section is expanded, the tasks within that section are displayed. When collapsed, the tasks are hidden.
 * The tasks can also be deleted, updated, and reminders can be created or deleted for each task.
 *
 * Also handles loading states and empty task states by showing appropriate loading indicators or a message indicating no tasks are available.
 *
 * - The task sections (previous, today, future, completed) are shown as separate expandable sections, each with a title and the tasks within it.
 * - The task lists are only visible if the corresponding section is expanded.
 * - Tasks are managed with actions for deletion and updates, and reminders can be added or deleted.
 * - When no tasks are present, a message prompting the user to add tasks is displayed.
 *
 * @param previousTasks A [ResultContainer] containing the list of previous tasks.
 * @param todayTasks A [ResultContainer] containing the list of today's tasks.
 * @param futureTasks A [ResultContainer] containing the list of future tasks.
 * @param completedTasks A [ResultContainer] containing the list of completed tasks.
 * @param onReloadTasks A callback function to reload the tasks.
 * @param categories A [ResultContainer] containing the list of task categories.
 * @param onReloadCategories A callback function to reload the categories.
 * @param onAddNewCategory A callback function to add a new task category.
 * @param onTaskDelete A callback function to delete a task by its ID.
 * @param onUpdateTask A callback function to update a task with the given [Task] object.
 * @param getRemindersForTask A function to retrieve reminders for a specific task by its ID.
 * @param onDeleteReminder A callback function to delete a task reminder.
 * @param onCreateReminder A callback function to create a task reminder.
 * @param onReloadReminders A callback function to reload the reminders.
 */
@Composable
fun AllTasksColumn(
    previousTasks: ResultContainer<List<Task>>,
    todayTasks: ResultContainer<List<Task>>,
    futureTasks: ResultContainer<List<Task>>,
    completedTasks: ResultContainer<List<Task>>,
    onReloadTasks: () -> Unit,
    categories: ResultContainer<List<TaskCategory>>,
    onReloadCategories: () -> Unit,
    onAddNewCategory: (String) -> Unit,
    onTaskDelete: (Long) -> Unit,
    onUpdateTask: (Task) -> Unit,
    getRemindersForTask: (Long) -> StateFlow<ResultContainer<List<TaskReminder>>>,
    onDeleteReminder: (TaskReminder) -> Unit,
    onCreateReminder: (TaskReminder) -> Unit,
    onReloadReminders: () -> Unit
) {
    var taskExpansionStates by remember { mutableStateOf(TaskExpansionStates()) }
    ResultContainerComposable(
        container = ResultContainer.wrap(
            previousTasks,
            todayTasks,
            futureTasks,
            completedTasks
        ),
        onTryAgain = onReloadTasks,
        onLoading = {
            Column(
                modifier = Modifier
                    .padding(LocalSpacing.current.medium)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(
                    LocalSpacing.current.small
                )
            ) {
                repeat(7) {
                    LoadingTaskListItem()
                }
            }
        }
    ) {
        val taskSections = listOf(
            TaskSection(
                title = stringResource(R.string.previous_tasks),
                tasks = previousTasks.unwrap(),
                isExpanded = taskExpansionStates.isPreviousTasksExpanded,
                onExpandChange = {
                    taskExpansionStates = taskExpansionStates.copy(isPreviousTasksExpanded = it)
                }
            ),
            TaskSection(
                title = stringResource(R.string.today_tasks),
                tasks = todayTasks.unwrap(),
                isExpanded = taskExpansionStates.isTodayTasksExpanded,
                onExpandChange = {
                    taskExpansionStates = taskExpansionStates.copy(isTodayTasksExpanded = it)
                }
            ),
            TaskSection(
                title = stringResource(R.string.future_tasks),
                tasks = futureTasks.unwrap(),
                isExpanded = taskExpansionStates.isFutureTasksExpanded,
                onExpandChange = {
                    taskExpansionStates = taskExpansionStates.copy(isFutureTasksExpanded = it)
                }
            ),
            TaskSection(
                title = stringResource(R.string.completed_tasks),
                tasks = completedTasks.unwrap(),
                isExpanded = taskExpansionStates.isCompletedTasksExpanded,
                onExpandChange = {
                    taskExpansionStates =
                        taskExpansionStates.copy(isCompletedTasksExpanded = it)
                }
            )
        )
        if (taskSections.all { it.tasks.isEmpty() }) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.you_didnt_add_any_task_yet),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = stringResource(R.string.add_task_by_pressing),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = LocalSpacing.current.semiMedium,
                    end = LocalSpacing.current.semiMedium,
                    bottom = LocalSpacing.current.small
                )
            ) {
                taskSections.forEach { section ->
                    tasksSubcolumn(
                        section.title,
                        section.tasks,
                        onTaskDelete = onTaskDelete,
                        onUpdateTask = onUpdateTask,
                        isExpanded = section.isExpanded,
                        onExpandChange = section.onExpandChange,
                        categories = categories,
                        onAddNewCategory = onAddNewCategory,
                        getRemindersForTask = getRemindersForTask,
                        onDeleteReminder = onDeleteReminder,
                        onCreateReminder = onCreateReminder,
                        onReloadCategories = onReloadCategories,
                        onReloadReminders = onReloadReminders
                    )
                }
            }
        }
    }
}

data class TaskExpansionStates(
    val isPreviousTasksExpanded: Boolean = true,
    val isTodayTasksExpanded: Boolean = true,
    val isFutureTasksExpanded: Boolean = true,
    val isCompletedTasksExpanded: Boolean = true
)

data class TaskSection(
    val title: String,
    val tasks: List<Task>,
    val isExpanded: Boolean,
    val onExpandChange: (Boolean) -> Unit
)

@Preview(showSystemUi = true)
@Composable
fun AllTasksColumnPreview() {
    val tasks = (1..8).map {
        Task(
            id = it.toLong(),
            text = "Задача $it",
            date = if (it % 2 == 0) LocalDate.now() else null,
            priority = Task.Priority.fromValue(it)
        )
    }

    AllTasksColumn(
        previousTasks = ResultContainer.Done(tasks.subList(0, 2)),
        todayTasks = ResultContainer.Done(tasks.subList(2, 4)),
        futureTasks = ResultContainer.Done(tasks.subList(4, 6)),
        completedTasks = ResultContainer.Done(
            tasks.subList(6, 8).map { it.copy(isCompleted = true) }),
        onTaskDelete = {},
        onUpdateTask = {},
        categories = ResultContainer.Done(emptyList()),
        onAddNewCategory = {},
        getRemindersForTask = { MutableStateFlow(ResultContainer.Done(emptyList())) },
        onDeleteReminder = {},
        onCreateReminder = {},
        onReloadTasks = {},
        onReloadCategories = {},
        onReloadReminders = {}
    )
}
