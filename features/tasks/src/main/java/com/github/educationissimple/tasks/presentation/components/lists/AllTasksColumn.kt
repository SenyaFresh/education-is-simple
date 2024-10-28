package com.github.educationissimple.tasks.presentation.components.lists

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.presentation.ResultContainerComposable
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.domain.entities.Task

@Composable
fun AllTasksColumn(
    previousTasks: ResultContainer<List<Task>>,
    todayTasks: ResultContainer<List<Task>>,
    futureTasks: ResultContainer<List<Task>>,
    completedTasks: ResultContainer<List<Task>>,
    onTaskDelete: (Long) -> Unit,
    onTaskPriorityChange: (Long, Task.Priority) -> Unit,
    onTaskCompletionChange: (Long, Boolean) -> Unit
) {
    var taskExpansionStates by remember { mutableStateOf(TaskExpansionStates()) }

    ResultContainerComposable(
        container = ResultContainer.wrap(
            previousTasks,
            todayTasks,
            futureTasks,
            completedTasks
        ),
        onTryAgain = { }
    ) {
        val taskSections =
            listOf(
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

        LazyColumn(contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 8.dp)) {
            taskSections.forEach { section ->
                tasksSubcolumn(
                    section.title,
                    section.tasks,
                    onTaskCompletionChange = onTaskCompletionChange,
                    onTaskDelete = onTaskDelete,
                    onTaskPriorityChange = onTaskPriorityChange,
                    isExpanded = section.isExpanded,
                    onExpandChange = section.onExpandChange
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
            date = if (it % 2 == 0) "10-08" else null,
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
        onTaskPriorityChange = { _, _ -> },
        onTaskCompletionChange = { _, _ -> }
    )
}
