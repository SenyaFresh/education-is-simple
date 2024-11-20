package com.github.educationissimple.data.tasks.di.modules

import com.github.educationissimple.common.di.AppScope
import com.github.educationissimple.data.tasks.repositories.RoomTasksDataRepository
import com.github.educationissimple.data.tasks.repositories.TasksDataRepository
import dagger.Binds
import dagger.Module

/**
 * Dagger module that provides a [TasksDataRepository].
 */
@Module(includes = [TasksDataSourceModule::class, PreferencesDataSourceModule::class])
interface TasksDataRepositoryModule {

    /**
     * Binds the [RoomTasksDataRepository] implementation to [TasksDataRepository].
     *
     * @param roomTasksDataRepository An instance of [RoomTasksDataRepository].
     * @return A [TasksDataRepository] instance.
     */
    @Binds
    @AppScope
    fun bindTasksDataRepository(roomTasksDataRepository: RoomTasksDataRepository): TasksDataRepository

}