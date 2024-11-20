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
import com.github.educationissimple.news.di.NewsDeps
import com.github.educationissimple.news.domain.repositories.NewsRepository
import com.github.educationissimple.notifications.di.ReminderDeps
import com.github.educationissimple.sync.RemindersSyncWorker
import com.github.educationissimple.tasks.di.TasksDeps
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import dagger.BindsInstance
import dagger.Component

/**
 * The AppComponent is the main Dagger component for dependency injection in the app.
 *
 * The component is responsible for injecting dependencies into the application’s various classes,
 * including activities, services, and workers.
 *
 * It also binds various modules that provide the actual implementations of the dependencies required by the app.
 *
 * @AppScope indicates that the component has a singleton lifecycle within the application's lifetime.
 */
@[AppScope Component(modules = [AppModule::class, CoreModule::class])]
interface AppComponent: TasksDeps, AudioDeps, PlayerDeps, ReminderDeps, NewsDeps {

    fun inject(mainActivity: MainActivity)

    fun remindersSyncWorkerFactory(): RemindersSyncWorker.Factory

    override val newsRepository: NewsRepository

    override val context: Context

    override val audioHandler: AudioListHandler
    override val audioRepository: AudioRepository

    override val tasksRepository: TasksRepository

    val coreProvider: CoreProvider

    /**
     * Builder for creating an instance of [AppComponent].
     * Requires context dependency before building.
     */
    @Component.Builder
    interface Builder {
        /**
         * Binds an instance of [Context] to the component.
         * This is required to ensure that the component has access to the application's context.
         *
         * @param context The application [Context].
         * @return The builder instance.
         */
        @BindsInstance
        fun context(context: Context) : Builder

        fun build() : AppComponent
    }

}