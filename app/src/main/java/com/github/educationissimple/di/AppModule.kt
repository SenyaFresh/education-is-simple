package com.github.educationissimple.di

import com.github.educationissimple.data.tasks.di.modules.TasksDataRepositoryModule
import com.github.educationissimple.glue.tasks.di.TasksRepositoryModule
import dagger.Module

@Module(includes = [TasksDataRepositoryModule::class, TasksRepositoryModule::class])
class AppModule