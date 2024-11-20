package com.github.educationissimple.data.tasks.di.modules

import com.github.educationissimple.common.di.AppScope
import com.github.educationissimple.data.tasks.sources.SharedTaskPreferencesDataSource
import com.github.educationissimple.data.tasks.sources.TaskPreferencesDataSource
import dagger.Binds
import dagger.Module

/**
 * A Dagger module for providing dependencies related to the task preferences.
 * This module binds the implementation of [TaskPreferencesDataSource] to [SharedTaskPreferencesDataSource].
 */
@Module
interface PreferencesDataSourceModule {

    /**
     * Binds the [SharedTaskPreferencesDataSource] implementation to [TaskPreferencesDataSource].
     *
     * @param sharedPreferencesDataSource An instance of [SharedTaskPreferencesDataSource].
     * @return A [TaskPreferencesDataSource] instance.
     */
    @Binds
    @AppScope
    fun bindPreferencesDataSource(sharedPreferencesDataSource: SharedTaskPreferencesDataSource): TaskPreferencesDataSource

}