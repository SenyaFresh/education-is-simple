package com.github.educationissimple.audio.di

import com.github.educationissimple.audio.sources.AudioDataSource
import com.github.educationissimple.audio.sources.RoomAudioDataSource
import com.github.educationissimple.common.di.AppScope
import dagger.Binds
import dagger.Module

@Module
interface AudioDataSourceModule {

    @Binds
    @AppScope
    fun bindAudioDataSource(roomAudioDataSource: RoomAudioDataSource): AudioDataSource

}