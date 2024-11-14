package com.github.educationissimple.tasks.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskReminder
import com.github.educationissimple.tasks.presentation.components.inputs.CalendarView
import com.github.educationissimple.tasks.presentation.components.lists.TaskSection
import com.github.educationissimple.tasks.presentation.components.lists.tasksSubcolumn
import com.github.educationissimple.tasks.presentation.events.TasksEvent
import com.github.educationissimple.tasks.presentation.viewmodels.TasksViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

@Composable
fun CalendarScreen(
    diContainer: TasksDiContainer = rememberTasksDiContainer(),
    viewModel: TasksViewModel = viewModel(factory = diContainer.viewModelFactory)
) {
    CalendarContent(
        notCompletedTasks = viewModel.notCompletedTasksWithDate.collectAsStateWithLifecycle().value,
        completedTasks = viewModel.completedTasksWithDate.collectAsStateWithLifecycle().value,
        categories = viewModel.categories.collectAsStateWithLifecycle().value,
        getRemindersForTask = viewModel::getRemindersForTask,
        onTasksEvent = viewModel::onEvent
    )
}

@Composable
fun CalendarContent(
    notCompletedTasks: ResultContainer<List<Task>>,
    completedTasks: ResultContainer<List<Task>>,
    categories: ResultContainer<List<TaskCategory>>,
    getRemindersForTask: (Long) -> StateFlow<ResultContainer<List<TaskReminder>>>,
    onTasksEvent: (TasksEvent) -> Unit
) {
    val onTaskDelete: (Long) -> Unit = { taskId ->
        onTasksEvent(TasksEvent.DeleteTask(taskId))
    }

    val onUpdateTask: (Task) -> Unit = { task ->
        onTasksEvent(TasksEvent.UpdateTask(task))
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
        Column {
            Card(
                modifier = Modifier.padding(LocalSpacing.current.small),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(containerColor = Neutral.Light.Light)
            ) {
                CalendarView(
                    selectedDate = selectedDate,
                    onDaySelect = {
                        selectedDate = it
                        onTasksEvent(TasksEvent.ChangeTasksSelectionDate(it))
                    },
                    modifier = Modifier
                        .padding(LocalSpacing.current.small)
                        .fillMaxWidth()
                )
            }

            if (taskSections.all { it.tasks.isEmpty() }) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(R.string.no_tasks_for_selected_date),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(LocalSpacing.current.medium)
                    )
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
                            categories = categories,
                            onAddNewCategory = { onTasksEvent(TasksEvent.AddCategory(it)) },
                            onExpandChange = section.onExpandChange,
                            getRemindersForTask = getRemindersForTask,
                            onCreateReminder = { onTasksEvent(TasksEvent.AddTaskReminder(it)) },
                            onDeleteReminder = { onTasksEvent(TasksEvent.DeleteTaskReminder(it)) }
                        )
                    }
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
            date = LocalDate.now()
        )
    }

    CalendarContent(
        notCompletedTasks = ResultContainer.Done(tasks.subList(0, 2)),
        completedTasks = ResultContainer.Done(
            tasks.subList(2, 4).map { it.copy(isCompleted = true) }),
        onTasksEvent = {},
        categories = ResultContainer.Done(emptyList()),
        getRemindersForTask = { MutableStateFlow(ResultContainer.Done(emptyList())) }
    )
}