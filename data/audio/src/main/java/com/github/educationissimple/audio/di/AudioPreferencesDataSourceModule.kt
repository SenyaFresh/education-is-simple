package com.github.educationissimple.audio.di

import com.github.educationissimple.audio.sources.AudioPreferencesDataSource
import com.github.educationissimple.audio.sources.SharedAudioPreferencesDataSource
import com.github.educationissimple.common.di.AppScope
import dagger.Binds
import dagger.Module

/**
 * A Dagger module for providing dependencies related to audio preferences data sources.
 */
@Module
interface AudioPreferencesDataSourceModule {

    /**
     * Binds the [SharedAudioPreferencesDataSource] implementation to the [AudioPreferencesDataSource] interface.
     */
    @Binds
    @AppScope
    fun bindPreferencesDataSource(sharedPreferencesDataSource: SharedAudioPreferencesDataSource): AudioPreferencesDataSource

}