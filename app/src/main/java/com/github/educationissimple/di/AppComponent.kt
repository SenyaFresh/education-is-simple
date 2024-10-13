package com.github.educationissimple.di

import android.content.Context
import com.github.educationissimple.CoreModule
import com.github.educationissimple.common.CoreProvider
import com.github.educationissimple.common.di.AppScope
import com.github.educationissimple.tasks.di.TasksDeps
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import dagger.BindsInstance
import dagger.Component

@[AppScope Component(modules = [AppModule::class, CoreModule::class])]
interface AppComponent: TasksDeps {

    override val tasksRepository: TasksRepository
    val coreProvider: CoreProvider

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context) : Builder

        fun build() : AppComponent
    }

}