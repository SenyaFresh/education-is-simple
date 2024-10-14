package com.github.educationissimple.tasks.presentation.components

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
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.domain.entities.Task

@Composable
fun AllTasksColumn(
    previousTasks: ResultContainer<List<Task>>,
    todayTasks: ResultContainer<List<Task>>,
    futureTasks: ResultContainer<List<Task>>,
    completedTasks: ResultContainer<List<Task>>,
    onTaskDelete: (Long) -> Unit,
    onTaskCompletionChange: (Long, Boolean) -> Unit
) {
    var isPreviousTasksExpanded by remember { mutableStateOf(true) }
    var isTodayTasksExpanded by remember { mutableStateOf(true) }
    var isFutureTasksExpanded by remember { mutableStateOf(true) }
    var isCompletedTasksExpanded by remember { mutableStateOf(true) }

    LazyColumn(contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 8.dp)) {
        tasksSubcolumn(
            Core.resources.getString(R.string.previous_tasks),
            previousTasks,
            onTaskCompletionChange = onTaskCompletionChange,
            onTaskDelete = onTaskDelete,
            isExpanded = isPreviousTasksExpanded,
            onExpandChange = {
                isPreviousTasksExpanded = it
            }
        )
        tasksSubcolumn(
            Core.resources.getString(R.string.today_tasks),
            todayTasks,
            onTaskCompletionChange = onTaskCompletionChange,
            onTaskDelete = onTaskDelete,
            isExpanded = isTodayTasksExpanded,
            onExpandChange = {
                isTodayTasksExpanded = it
            }
        )
        tasksSubcolumn(
            Core.resources.getString(R.string.future_tasks),
            futureTasks,
            onTaskCompletionChange = onTaskCompletionChange,
            onTaskDelete = onTaskDelete,
            isExpanded = isFutureTasksExpanded,
            onExpandChange = {
                isFutureTasksExpanded = it
            }
        )
        tasksSubcolumn(
            Core.resources.getString(R.string.completed_tasks),
            completedTasks,
            onTaskCompletionChange = onTaskCompletionChange,
            onTaskDelete = onTaskDelete,
            isExpanded = isCompletedTasksExpanded,
            onExpandChange = {
                isCompletedTasksExpanded = it
            }
        )
    }
}