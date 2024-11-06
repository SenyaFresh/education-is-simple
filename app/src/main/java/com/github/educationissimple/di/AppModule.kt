package com.github.educationissimple.di

import android.app.Application
import android.content.Context
import com.github.educationissimple.audio.di.AudioDataRepositoryModule
import com.github.educationissimple.data.tasks.di.modules.TasksDataRepositoryModule
import com.github.educationissimple.glue.audio.di.AudioListHandlerModule
import com.github.educationissimple.glue.audio.di.AudioListPlayerHandlerModule
import com.github.educationissimple.glue.audio.di.AudioRepositoryModule
import com.github.educationissimple.glue.tasks.di.TasksRepositoryModule
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        TasksDataRepositoryModule::class,
        TasksRepositoryModule::class,
        AudioDataRepositoryModule::class,
        AudioRepositoryModule::class,
        AudioListPlayerHandlerModule::class,
        AudioListHandlerModule::class
    ]
)
class AppModule {

    @Provides
    fun provideApplication(context: Context): Application {
        return context.applicationContext as Application
    }

}