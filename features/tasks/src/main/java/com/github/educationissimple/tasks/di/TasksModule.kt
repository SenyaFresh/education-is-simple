package com.github.educationissimple.tasks.di

import com.github.educationissimple.common.di.Feature
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import dagger.Module
import dagger.Provides

@Module
class TasksModule {

    @Provides
    @Feature
    fun provideTasksRepository(deps: TasksDeps): TasksRepository = deps.tasksRepository

}