package com.github.educationissimple.data.tasks.di.modules

import com.github.educationissimple.common.di.AppScope
import com.github.educationissimple.data.tasks.sources.PreferencesDataSource
import com.github.educationissimple.data.tasks.sources.SharedPreferencesDataSource
import dagger.Binds
import dagger.Module


@Module
interface PreferencesDataSourceModule {

    @Binds
    @AppScope
    fun bindPreferencesDataSource(sharedPreferencesDataSource: SharedPreferencesDataSource): PreferencesDataSource

}