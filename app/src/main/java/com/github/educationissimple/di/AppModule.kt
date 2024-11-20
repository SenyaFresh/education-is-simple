package com.github.educationissimple.di

import android.app.Application
import android.content.Context
import com.github.educationissimple.audio.di.AudioDataRepositoryModule
import com.github.educationissimple.data.news.di.NewsDataRepositoryModule
import com.github.educationissimple.data.tasks.di.modules.TasksDataRepositoryModule
import com.github.educationissimple.glue.audio.di.AudioListHandlerModule
import com.github.educationissimple.glue.audio.di.AudioListPlayerHandlerModule
import com.github.educationissimple.glue.audio.di.AudioRepositoryModule
import com.github.educationissimple.glue.audio.di.AudioServiceManagerModule
import com.github.educationissimple.glue.news.di.NewsRepositoryModule
import com.github.educationissimple.glue.tasks.di.RemindersSchedulerModule
import com.github.educationissimple.glue.tasks.di.TasksRepositoryModule
import dagger.Module
import dagger.Provides

/**
 * Dagger module that provides dependencies for the app.
 */
@Module(
    includes = [
        TasksDataRepositoryModule::class,
        TasksRepositoryModule::class,
        RemindersSchedulerModule::class,
        AudioDataRepositoryModule::class,
        AudioRepositoryModule::class,
        AudioListPlayerHandlerModule::class,
        AudioListHandlerModule::class,
        AudioServiceManagerModule::class,
        NewsDataRepositoryModule::class,
        NewsRepositoryModule::class
    ]
)
class AppModule {

    /**
     * Provides the application context.
     *
     * @param context The application context.
     * @return The application context.
     **/
    @Provides
    fun provideApplication(context: Context): Application {
        return context.applicationContext as Application
    }

}