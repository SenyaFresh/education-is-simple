package com.github.educationissimple.data.tasks.di.modules

import com.github.educationissimple.common.di.AppScope
import com.github.educationissimple.data.tasks.sources.SharedTaskPreferencesDataSource
import com.github.educationissimple.data.tasks.sources.TaskPreferencesDataSource
import dagger.Binds
import dagger.Module


@Module
interface PreferencesDataSourceModule {

    @Binds
    @AppScope
    fun bindPreferencesDataSource(sharedPreferencesDataSource: SharedTaskPreferencesDataSource): TaskPreferencesDataSource

}