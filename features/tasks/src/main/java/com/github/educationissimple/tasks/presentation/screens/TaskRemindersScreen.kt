package com.github.educationissimple.tasks.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.composables.lists.ActionableItemsListWithAdding
import com.github.educationissimple.components.entities.ActionableItem
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.di.TasksDiContainer
import com.github.educationissimple.tasks.di.rememberTasksDiContainer
import com.github.educationissimple.tasks.domain.entities.TaskReminder
import com.github.educationissimple.tasks.presentation.events.TasksEvent
import com.github.educationissimple.tasks.presentation.viewmodels.TasksViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TaskRemindersScreen(
    diContainer: TasksDiContainer = rememberTasksDiContainer(),
    viewModel: TasksViewModel = viewModel(factory = diContainer.viewModelFactory)
) {
    TaskRemindersContent(
        reminders = viewModel.reminders.collectAsStateWithLifecycle().value,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun TaskRemindersContent(
    reminders: ResultContainer<List<TaskReminder>>,
    onEvent: (TasksEvent) -> Unit
) {
    ActionableItemsListWithAdding(
        items = reminders.map { list ->
            list.map {
                ActionableItem(
                    it.id, "${it.datetime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}: \"${it.taskText}\""
                )
            }
        },
        addLabel = stringResource(R.string.add_new_category),
        addPlaceholder = stringResource(R.string.input_new_category_here),
        onDelete = { categoryId ->
            onEvent(TasksEvent.DeleteCategory(categoryId))
        },
        onAdd = { categoryName ->
            onEvent(TasksEvent.AddCategory(categoryName))
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun TaskRemindersContentPreview() {
    val reminders = (1..5).map {
        TaskReminder(it.toLong(), it.toLong(), "Task $it", LocalDateTime.now())
    }
    TaskRemindersContent(ResultContainer.Done(reminders), onEvent = { })
}

@Preview(showSystemUi = true)
@Composable
fun TaskRemindersLoadingPreview() {
    TaskRemindersContent(ResultContainer.Loading, onEvent = { })
}