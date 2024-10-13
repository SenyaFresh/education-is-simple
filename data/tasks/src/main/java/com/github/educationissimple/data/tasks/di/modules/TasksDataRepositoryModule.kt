package com.github.educationissimple.data.tasks.di.modules

import com.github.educationissimple.common.di.AppScope
import com.github.educationissimple.data.tasks.repositories.RoomTasksDataRepository
import com.github.educationissimple.data.tasks.repositories.TasksDataRepository
import dagger.Binds
import dagger.Module

@Module(includes = [TasksDataSourceModule::class])
interface TasksDataRepositoryModule {

    @Binds
    @AppScope
    fun bindTasksDataRepository(roomTasksDataRepository: RoomTasksDataRepository): TasksDataRepository

}