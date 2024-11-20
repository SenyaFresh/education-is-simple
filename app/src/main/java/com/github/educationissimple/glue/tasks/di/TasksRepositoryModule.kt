package com.github.educationissimple.glue.tasks.di

import com.github.educationissimple.glue.tasks.repositories.AdapterTasksRepository
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import dagger.Binds
import dagger.Module

/**
 * Module for providing dependencies related to the tasks repository.
 */
@Module
interface TasksRepositoryModule {

    /**
     * Binds the [AdapterTasksRepository] implementation to the [TasksRepository] interface.
     */
    @Binds
    fun bindTasksRepository(adapterTasksRepository: AdapterTasksRepository): TasksRepository

}