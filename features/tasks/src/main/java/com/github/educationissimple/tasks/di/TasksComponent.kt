package com.github.educationissimple.tasks.di

import com.github.educationissimple.common.di.Feature
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import dagger.BindsInstance
import dagger.Component

/**
 * Dagger component responsible for providing dependencies for the tasks feature.
 */
@Feature
@Component(modules = [TasksModule::class])
internal interface TasksComponent {

    fun inject(it: TasksDiContainer)

    /**
     * Builder interface for creating instances of [TasksComponent].
     */
    @Component.Builder
    interface Builder {
        /**
         * Binds the external dependencies for tasks into the component.
         *
         * @param deps The [TasksDeps] providing the required dependencies.
         * @return The builder instance for further configuration.
         **/
        @BindsInstance
        fun deps(deps: TasksDeps): Builder

        fun build(): TasksComponent
    }
}

/**
 * Interface defining the external dependencies required for tasks functionality.
 *
 * The dependencies include [TasksRepository] for data management.
 */
interface TasksDeps {
    val tasksRepository: TasksRepository
}