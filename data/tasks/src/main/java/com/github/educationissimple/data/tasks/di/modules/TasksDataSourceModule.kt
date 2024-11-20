package com.github.educationissimple.data.tasks.di.modules

import com.github.educationissimple.common.di.AppScope
import com.github.educationissimple.data.tasks.sources.RoomTasksDataSource
import com.github.educationissimple.data.tasks.sources.TasksDataSource
import dagger.Binds
import dagger.Module

/**
 * Dagger module that provides a [TasksDataSource].
 */
@Module
interface TasksDataSourceModule {

    /**
     * Binds the [RoomTasksDataSource] implementation to [TasksDataSource].
     *
     * @param roomTasksDataSource An instance of [RoomTasksDataSource].
     * @return A [TasksDataSource] instance.
     */
    @Binds
    @AppScope
    fun bindTasksDataSource(roomTasksDataSource: RoomTasksDataSource): TasksDataSource

}