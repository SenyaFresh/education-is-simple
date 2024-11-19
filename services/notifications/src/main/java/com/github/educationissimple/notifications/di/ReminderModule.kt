package com.github.educationissimple.notifications.di

import android.content.Context
import com.github.educationissimple.common.di.Service
import com.github.educationissimple.notifications.schedulers.NotificationReminderScheduler
import com.github.educationissimple.notifications.schedulers.ReminderScheduler
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Main module for notifications module.
 */
@Module(includes = [AlarmSchedulerModule::class])
class ReminderModule {

    /**
     * Provides [Context] for notifications module.
     * @param deps The [ReminderDeps] for [ReminderComponent].
     */
    @Provides
    @Service
    fun provideContext(deps: ReminderDeps): Context = deps.context

}

/**
 * Alarm scheduler module for notifications module.
 */
@Module
abstract class AlarmSchedulerModule {

    /**
     * Provides [ReminderScheduler] for notifications module.
     * @param notificationAlarmScheduler The [NotificationReminderScheduler] for [ReminderScheduler].
     */
    @Binds
    @Service
    abstract fun provideAlarmScheduler(notificationAlarmScheduler: NotificationReminderScheduler): ReminderScheduler

}