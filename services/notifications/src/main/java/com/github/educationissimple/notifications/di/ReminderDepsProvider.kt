package com.github.educationissimple.notifications.di

import kotlin.properties.Delegates.notNull

/**
 * [ReminderComponent] dependencies provider.
 */
interface ReminderDepsProvider {

    /**
     * [ReminderComponent] dependencies.
     */
    val deps: ReminderDeps

    /**
     * Singleton instance of [ReminderDepsProvider] provided by [ReminderDepsStore].
     */
    companion object : ReminderDepsProvider by ReminderDepsStore

}

/**
 * [ReminderComponent] dependencies store.
 */
object ReminderDepsStore : ReminderDepsProvider {
    override var deps: ReminderDeps by notNull()
}