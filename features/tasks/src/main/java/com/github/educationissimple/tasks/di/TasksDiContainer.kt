package com.github.educationissimple.tasks.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import com.github.educationissimple.tasks.presentation.viewmodels.TasksViewModel
import javax.inject.Inject

@Stable
class TasksDiContainer {
    @Inject lateinit var tasksRepository: TasksRepository
    @Inject lateinit var viewModelFactory: TasksViewModel.Factory
}

@Composable
fun rememberTasksDiContainer() : TasksDiContainer {
    return remember {
        TasksDiContainer().also {
            DaggerTasksComponent.builder().deps(TasksDepsProvider.deps).build().inject(it)
        }
    }
}