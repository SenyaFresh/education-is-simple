package com.github.educationissimple.glue.tasks.di

import com.github.educationissimple.glue.tasks.repositories.AdapterTasksRepository
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import dagger.Binds
import dagger.Module

@Module
interface TasksRepositoryModule {

    @Binds
    fun bindTasksRepository(adapterTasksRepository: AdapterTasksRepository): TasksRepository

}