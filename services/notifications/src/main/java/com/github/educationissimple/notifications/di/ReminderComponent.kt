package com.github.educationissimple.notifications.di

import android.content.Context
import com.github.educationissimple.common.di.Service
import com.github.educationissimple.notifications.schedulers.ReminderScheduler
import dagger.BindsInstance
import dagger.Component

/**
 * A Dagger component for managing reminders-related dependencies in the notifications module.
 * This component provides instance of [ReminderScheduler].
 */
@Service
@Component(modules = [ReminderModule::class])
interface ReminderComponent {

    /**
     * Provides an instance of [ReminderScheduler].
     *
     * @return The [ReminderScheduler] implementation.
     */
    fun alarmScheduler(): ReminderScheduler

    /**
     * Builder interface for creating instances of [ReminderComponent].
     *
     * Requires [ReminderDeps] provided by [deps] function for providing external dependencies.
     */
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun deps(deps: ReminderDeps): Builder

        fun build(): ReminderComponent
    }
}

/**
 * Interface representing the external dependencies required by the [ReminderComponent].
 * These dependencies are provided at runtime.
 */
interface ReminderDeps {
    val context: Context
}