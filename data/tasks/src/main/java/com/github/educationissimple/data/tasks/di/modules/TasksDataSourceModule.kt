package com.github.educationissimple.data.tasks.di.modules

import com.github.educationissimple.common.di.AppScope
import com.github.educationissimple.data.tasks.sources.RoomTasksDataSource
import com.github.educationissimple.data.tasks.sources.TasksDataSource
import dagger.Binds
import dagger.Module

@Module
interface TasksDataSourceModule {

    @Binds
    @AppScope
    fun bindTasksDataSource(roomTasksDataSource: RoomTasksDataSource): TasksDataSource

}