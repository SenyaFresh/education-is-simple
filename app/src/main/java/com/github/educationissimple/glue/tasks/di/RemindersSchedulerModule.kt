package com.github.educationissimple.glue.tasks.di

import com.github.educationissimple.notifications.di.ReminderComponentHolder
import com.github.educationissimple.notifications.schedulers.ReminderScheduler
import dagger.Module
import dagger.Provides

/**
 * Module for providing dependencies related to the reminders scheduler.
 */
@Module
class RemindersSchedulerModule {

    /**
     * Provides an instance of the [ReminderScheduler] interface.
     */
    @Provides
    fun provideRemindersScheduler(): ReminderScheduler = ReminderComponentHolder.getInstance().alarmScheduler()

}