package com.github.educationissimple.notifications.di

import kotlin.properties.Delegates.notNull

interface ReminderDepsProvider {

    val deps: ReminderDeps

    companion object : ReminderDepsProvider by ReminderDepsStore

}

object ReminderDepsStore : ReminderDepsProvider {
    override var deps: ReminderDeps by notNull()
}