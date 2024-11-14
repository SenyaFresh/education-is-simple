package com.github.educationissimple.di

import android.content.Context
import com.github.educationissimple.CoreModule
import com.github.educationissimple.MainActivity
import com.github.educationissimple.audio.di.AudioDeps
import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import com.github.educationissimple.audio.domain.repositories.AudioRepository
import com.github.educationissimple.audio_player.di.PlayerDeps
import com.github.educationissimple.common.CoreProvider
import com.github.educationissimple.common.di.AppScope
import com.github.educationissimple.notifications.di.ReminderDeps
import com.github.educationissimple.sync.RemindersSyncWorker
import com.github.educationissimple.tasks.di.TasksDeps
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import dagger.BindsInstance
import dagger.Component

@[AppScope Component(modules = [AppModule::class, CoreModule::class])]
interface AppComponent: TasksDeps, AudioDeps, PlayerDeps, ReminderDeps {

    fun inject(mainActivity: MainActivity)

    fun remindersSyncWorkerFactory(): RemindersSyncWorker.Factory

    override val context: Context

    override val audioHandler: AudioListHandler
    override val audioRepository: AudioRepository

    override val tasksRepository: TasksRepository

    val coreProvider: CoreProvider

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context) : Builder

        fun build() : AppComponent
    }

}