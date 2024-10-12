package com.github.educationissimple.tasks.di

import android.content.Context
import com.github.educationissimple.common.di.Feature
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import dagger.BindsInstance
import dagger.Component

@Feature
@Component(modules = [TasksModule::class])
internal interface TasksComponent {

    fun inject(it: TasksDiContainer)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun deps(deps: TasksDeps): Builder

        fun build(): TasksComponent
    }
}

interface TasksDeps {
    val tasksRepository: TasksRepository
}