package com.github.educationissimple.data.tasks.di.modules

import com.github.educationissimple.data.tasks.sources.RoomTasksDataSource
import com.github.educationissimple.data.tasks.sources.TasksDataSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface TasksDataSourceModule {

    @Binds
    @Singleton
    fun bindTasksDataSource(roomTasksDataSource: RoomTasksDataSource): TasksDataSource

}