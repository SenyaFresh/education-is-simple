package com.github.educationissimple.notifications.di

import android.content.Context
import com.github.educationissimple.common.di.Service
import com.github.educationissimple.notifications.schedulers.ReminderScheduler
import dagger.BindsInstance
import dagger.Component

/**
 * Dagger component for audio-player module.
 */
@Service
@Component(modules = [ReminderModule::class])
interface ReminderComponent {

    fun alarmScheduler(): ReminderScheduler

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun deps(deps: ReminderDeps): Builder

        fun build(): ReminderComponent
    }
}

interface ReminderDeps {
    val context: Context
}