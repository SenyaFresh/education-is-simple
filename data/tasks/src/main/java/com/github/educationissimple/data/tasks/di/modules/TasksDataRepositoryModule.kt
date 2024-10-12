package com.github.educationissimple.data.tasks.di.modules

import com.github.educationissimple.data.tasks.repositories.RoomTasksDataRepository
import com.github.educationissimple.data.tasks.repositories.TasksDataRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [TasksDataSourceModule::class])
interface TasksDataRepositoryModule {

    @Binds
    @Singleton
    fun bindTasksDataRepository(roomTasksDataRepository: RoomTasksDataRepository): TasksDataRepository

}