package com.github.educationissimple.tasks.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.github.educationissimple.tasks.presentation.viewmodels.TasksViewModel
import javax.inject.Inject

/**
 * A container for dependencies related to tasks functionality.
 *
 * This class holds the required dependencies, such as the [TasksViewModel.Factory],
 * which are injected using Dagger. It serves as a central point for managing tasks-related,
 * dependencies in the composable functions.
 */
@Stable
class TasksDiContainer {
    /**
     * The factory for creating instances of [TasksViewModel].
     */
    @Inject lateinit var viewModelFactory: TasksViewModel.Factory
}

/**
 * A composable function that remembers and provides an instance of [TasksDiContainer].
 *
 * This function ensures that the [TasksDiContainer] is created once and injected with the
 * necessary dependencies using Dagger. It is useful for managing and providing tasks-related
 * dependencies in composables.
 *
 * @return The [TasksDiContainer] instance with injected dependencies.
 */
@Composable
fun rememberTasksDiContainer() : TasksDiContainer {
    return remember {
        TasksDiContainer().also {
            DaggerTasksComponent.builder().deps(TasksDepsProvider.deps).build().inject(it)
        }
    }
}