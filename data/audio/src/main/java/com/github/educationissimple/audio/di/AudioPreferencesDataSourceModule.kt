package com.github.educationissimple.audio.di

import com.github.educationissimple.audio.sources.AudioPreferencesDataSource
import com.github.educationissimple.audio.sources.room.SharedAudioPreferencesDataSource
import com.github.educationissimple.common.di.AppScope
import dagger.Binds
import dagger.Module

@Module
interface AudioPreferencesDataSourceModule {

    @Binds
    @AppScope
    fun bindPreferencesDataSource(sharedPreferencesDataSource: SharedAudioPreferencesDataSource): AudioPreferencesDataSource

}