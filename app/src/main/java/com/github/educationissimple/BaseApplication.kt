package com.github.educationissimple

import android.app.Application
import com.github.educationissimple.common.Core
import com.github.educationissimple.di.AppComponent
import com.github.educationissimple.di.DaggerAppComponent
import com.github.educationissimple.tasks.di.TasksDepsStore

class BaseApplication: Application() {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .context(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        Core.init(appComponent.coreProvider)
        TasksDepsStore.deps = appComponent
    }

}