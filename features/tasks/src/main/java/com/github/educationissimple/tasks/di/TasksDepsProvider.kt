package com.github.educationissimple.tasks.di

import kotlin.properties.Delegates.notNull

/**
 * [TasksComponent] dependencies provider.
 */
interface TasksDepsProvider {

    /**
     * [TasksComponent] dependencies.
     */
    val deps: TasksDeps

    /**
     * Singleton instance of [TasksDepsProvider] provided by [TasksDepsStore].
     */
    companion object : TasksDepsProvider by TasksDepsStore

}

/**
 * [TasksComponent] dependencies store.
 */
object TasksDepsStore : TasksDepsProvider {
    override var deps: TasksDeps by notNull()
}