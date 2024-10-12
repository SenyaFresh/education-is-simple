package com.github.educationissimple.tasks.di

import android.content.Context
import com.github.educationissimple.common.di.Feature
import dagger.Component

@[Feature Component(dependencies = [TasksDeps::class])]
internal interface TasksComponent {

    fun inject(it: TasksDiContainer)

    @Component.Builder
    interface Builder {
        fun deps(deps: TasksDeps): Builder

        fun build(): TasksComponent
    }
}

interface TasksDeps {
    val context: Context
}