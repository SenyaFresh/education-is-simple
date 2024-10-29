package com.github.educationissimple.tasks.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.presentation.ResultContainerComposable
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.di.TasksDiContainer
import com.github.educationissimple.tasks.di.rememberTasksDiContainer
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.presentation.components.CalendarView
import com.github.educationissimple.tasks.presentation.components.lists.TaskSection
import com.github.educationissimple.tasks.presentation.components.lists.tasksSubcolumn
import com.github.educationissimple.tasks.presentation.events.TasksEvent
import com.github.educationissimple.tasks.presentation.viewmodels.TasksViewModel
import java.time.LocalDate

@Composable
fun CalendarScreen(
    diContainer: TasksDiContainer = rememberTasksDiContainer(),
    viewModel: TasksViewModel = viewModel(factory = diContainer.viewModelFactory)
) {
    CalendarContent(
        notCompletedTasks = viewModel.notCompletedTasksWithDate.collectAsStateWithLifecycle().value,
        completedTasks = viewModel.completedTasksWithDate.collectAsStateWithLifecycle().value,
        onTasksEvent = viewModel::onEvent
    )
}

@Composable
fun CalendarContent(
    notCompletedTasks: ResultContainer<List<Task>>,
    completedTasks: ResultContainer<List<Task>>,
    onTasksEvent: (TasksEvent) -> Unit
) {
    val onTaskCompletionChange: (Long, Boolean) -> Unit = { taskId, isCompleted ->
        if (isCompleted) {
            onTasksEvent(TasksEvent.CompleteTask(taskId))
        } else {
            onTasksEvent(TasksEvent.CancelTaskCompletion(taskId))
        }
    }

    val onTaskDelete: (Long) -> Unit = { taskId ->
        onTasksEvent(TasksEvent.DeleteTask(taskId))
    }

    val onTaskPriorityChange: (Long, Task.Priority) -> Unit = { taskId, priority ->
        onTasksEvent(TasksEvent.ChangeTaskPriority(taskId, priority))
    }

    var selectedDate by remember { mutableStateOf<LocalDate>(LocalDate.now()) }
    var taskExpansionStates by remember { mutableStateOf(TaskExpansionStates()) }

    ResultContainerComposable(
        container = ResultContainer.wrap(notCompletedTasks, completedTasks),
        onTryAgain = { }
    ) {
        val taskSections = listOf(
            TaskSection(
                title = stringResource(R.string.not_completed_tasks),
                tasks = notCompletedTasks.unwrap(),
                isExpanded = taskExpansionStates.isNotCompletedTasksExpanded,
                onExpandChange = {
                    taskExpansionStates = taskExpansionStates.copy(isNotCompletedTasksExpanded = it)
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.padding(LocalSpacing.current.small),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(containerColor = Neutral.Light.Light)
            ) {
                CalendarView(
                    selectedDate = selectedDate,
                    onDaySelect = {
                        selectedDate = it
                        onTasksEvent(TasksEvent.ChangeTaskDate(it))
                    },
                    modifier = Modifier.padding(LocalSpacing.current.small).fillMaxWidth()
                )

            }

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
}

data class TaskExpansionStates(
    val isNotCompletedTasksExpanded: Boolean = true,
    val isCompletedTasksExpanded: Boolean = true
)

@Preview(showSystemUi = true)
@Composable
fun CalendarContentPreview() {
    val tasks = (1..4).map {
        Task(
            id = it.toLong(),
            text = "Task $it",
            priority = Task.Priority.fromValue(it % 3),
            date = "10-08"
        )
    }

    CalendarContent(
        notCompletedTasks = ResultContainer.Done(tasks.subList(0, 2)),
        completedTasks = ResultContainer.Done(
            tasks.subList(2, 4).map { it.copy(isCompleted = true) }),
        onTasksEvent = {},
    )
}