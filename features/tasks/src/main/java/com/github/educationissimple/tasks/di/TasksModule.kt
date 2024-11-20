package com.github.educationissimple.tasks.di

import com.github.educationissimple.common.di.Feature
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import dagger.Module
import dagger.Provides

/**
 * Dagger module that provides dependencies related to tasks functionality.
 */
@Module
class TasksModule {

    /**
     * Provides an instance of [TasksRepository] for tasks-related data operations.
     *
     * @param deps The [TasksDeps] interface, which contains the required tasks dependencies.
     * @return An instance of [TasksRepository].
     */
    @Provides
    @Feature
    fun provideTasksRepository(deps: TasksDeps): TasksRepository = deps.tasksRepository

}