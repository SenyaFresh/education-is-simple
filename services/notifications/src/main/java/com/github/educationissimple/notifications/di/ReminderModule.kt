package com.github.educationissimple.notifications.di

import android.content.Context
import com.github.educationissimple.common.di.Service
import com.github.educationissimple.notifications.schedulers.NotificationReminderScheduler
import com.github.educationissimple.notifications.schedulers.ReminderScheduler
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [AlarmSchedulerModule::class])
class ReminderModule {

    @Provides
    @Service
    fun provideContext(deps: ReminderDeps): Context = deps.context

}

@Module
abstract class AlarmSchedulerModule {

    @Binds
    @Service
    abstract fun provideAlarmScheduler(notificationAlarmScheduler: NotificationReminderScheduler): ReminderScheduler

}