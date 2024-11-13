package com.github.educationissimple.tasks.presentation.components.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.presentation.ResultContainerComposable
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskReminder
import com.github.educationissimple.tasks.presentation.components.items.LoadingTaskListItem
import java.time.LocalDate

@Composable
fun AllTasksColumn(
    previousTasks: ResultContainer<List<Task>>,
    todayTasks: ResultContainer<List<Task>>,
    futureTasks: ResultContainer<List<Task>>,
    completedTasks: ResultContainer<List<Task>>,
    categories: ResultContainer<List<TaskCategory>>,
    onAddNewCategory: (String) -> Unit,
    onTaskDelete: (Long) -> Unit,
    onUpdateTask: (Task) -> Unit,
    getRemindersForTask: (Long) -> State<ResultContainer<List<TaskReminder>>>,
    onDeleteReminder: (TaskReminder) -> Unit,
    onCreateReminder: (TaskReminder) -> Unit,
) {
    var taskExpansionStates by remember { mutableStateOf(TaskExpansionStates()) }
    ResultContainerComposable(
        container = ResultContainer.wrap(
            previousTasks,
            todayTasks,
            futureTasks,
            completedTasks
        ),
        onTryAgain = { },
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
                    onCreateReminder = onCreateReminder
                )
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
        getRemindersForTask = { mutableStateOf(ResultContainer.Done(emptyList())) },
        onDeleteReminder = {},
        onCreateReminder = {}
    )
}
