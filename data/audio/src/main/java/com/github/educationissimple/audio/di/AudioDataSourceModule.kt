package com.github.educationissimple.audio.di

import com.github.educationissimple.audio.sources.AudioDataSource
import com.github.educationissimple.audio.sources.RoomAudioDataSource
import com.github.educationissimple.common.di.AppScope
import dagger.Binds
import dagger.Module

/**
 * A Dagger module for providing dependencies related to audio data sources.
 */
@Module
interface AudioDataSourceModule {

    /**
     * Binds the [RoomAudioDataSource] implementation to the [AudioDataSource] interface.
     */
    @Binds
    @AppScope
    fun bindAudioDataSource(roomAudioDataSource: RoomAudioDataSource): AudioDataSource

}