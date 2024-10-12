package com.github.educationissimple.tasks.di

import kotlin.properties.Delegates.notNull

interface TasksDepsProvider {

    val deps: TasksDeps

    companion object : TasksDepsProvider by TasksDepsStore

}

object TasksDepsStore : TasksDepsProvider {
    override var deps: TasksDeps by notNull()
}