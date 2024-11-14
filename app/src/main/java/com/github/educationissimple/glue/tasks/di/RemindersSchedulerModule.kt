package com.github.educationissimple.glue.tasks.di

import com.github.educationissimple.notifications.di.ReminderComponentHolder
import com.github.educationissimple.notifications.schedulers.ReminderScheduler
import dagger.Module
import dagger.Provides

@Module
class RemindersSchedulerModule {

    @Provides
    fun provideRemindersScheduler(): ReminderScheduler = ReminderComponentHolder.getInstance().alarmScheduler()

}