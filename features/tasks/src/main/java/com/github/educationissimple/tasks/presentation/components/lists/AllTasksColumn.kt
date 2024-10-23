package com.github.educationissimple.tasks.presentation.components.lists

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.github.educationissimple.common.Core
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
    var isPreviousTasksExpanded by remember { mutableStateOf(true) }
    var isTodayTasksExpanded by remember { mutableStateOf(true) }
    var isFutureTasksExpanded by remember { mutableStateOf(true) }
    var isCompletedTasksExpanded by remember { mutableStateOf(true) }

    ResultContainerComposable(
        container = ResultContainer.wrap(
            previousTasks,
            todayTasks,
            futureTasks,
            completedTasks
        ),
        onTryAgain = { }
    ) {
        LazyColumn(contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 8.dp)) {
            tasksSubcolumn(
                Core.resources.getString(R.string.previous_tasks),
                previousTasks.unwrap(),
                onTaskCompletionChange = onTaskCompletionChange,
                onTaskDelete = onTaskDelete,
                onTaskPriorityChange = onTaskPriorityChange,
                isExpanded = isPreviousTasksExpanded,
                onExpandChange = {
                    isPreviousTasksExpanded = it
                }
            )
            tasksSubcolumn(
                Core.resources.getString(R.string.today_tasks),
                todayTasks.unwrap(),
                onTaskCompletionChange = onTaskCompletionChange,
                onTaskDelete = onTaskDelete,
                onTaskPriorityChange = onTaskPriorityChange,
                isExpanded = isTodayTasksExpanded,
                onExpandChange = {
                    isTodayTasksExpanded = it
                }
            )
            tasksSubcolumn(
                Core.resources.getString(R.string.future_tasks),
                futureTasks.unwrap(),
                onTaskCompletionChange = onTaskCompletionChange,
                onTaskDelete = onTaskDelete,
                onTaskPriorityChange = onTaskPriorityChange,
                isExpanded = isFutureTasksExpanded,
                onExpandChange = {
                    isFutureTasksExpanded = it
                }
            )
            tasksSubcolumn(
                Core.resources.getString(R.string.completed_tasks),
                completedTasks.unwrap(),
                onTaskCompletionChange = onTaskCompletionChange,
                onTaskDelete = onTaskDelete,
                onTaskPriorityChange = onTaskPriorityChange,
                isExpanded = isCompletedTasksExpanded,
                onExpandChange = {
                    isCompletedTasksExpanded = it
                }
            )
        }
    }
}