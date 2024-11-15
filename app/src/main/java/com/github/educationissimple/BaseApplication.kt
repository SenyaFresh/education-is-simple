package com.github.educationissimple

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.github.educationissimple.audio.di.AudioDepsStore
import com.github.educationissimple.audio_player.di.PlayerDepsStore
import com.github.educationissimple.common.Core
import com.github.educationissimple.di.AppComponent
import com.github.educationissimple.di.DaggerAppComponent
import com.github.educationissimple.news.di.NewsDepsStore
import com.github.educationissimple.notifications.di.ReminderDepsStore
import com.github.educationissimple.tasks.di.TasksDepsStore

class BaseApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .context(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        Core.init(appComponent.coreProvider)
        AudioDepsStore.deps = appComponent
        TasksDepsStore.deps = appComponent
        PlayerDepsStore.deps = appComponent
        ReminderDepsStore.deps = appComponent
        NewsDepsStore.deps = appComponent

        WorkManager.initialize(
            this,
            Configuration.Builder()
                .setWorkerFactory(appComponent.remindersSyncWorkerFactory())
                .build()
        )
    }

}