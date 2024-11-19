package com.github.educationissimple.audio.di

import com.github.educationissimple.audio.repositories.AudioDataRepository
import com.github.educationissimple.audio.repositories.RoomAudioDataRepository
import com.github.educationissimple.common.di.AppScope
import dagger.Binds
import dagger.Module

/**
 * Dagger module for providing dependencies related to audio data repositories.
 */
@Module(includes = [AudioDataSourceModule::class, AudioPreferencesDataSourceModule::class])
interface AudioDataRepositoryModule {

    /**
     * Binds the [RoomAudioDataRepository] implementation to the [AudioDataRepository] interface.
     */
    @Binds
    @AppScope
    fun bindAudioDataRepository(roomAudioDataRepository: RoomAudioDataRepository): AudioDataRepository

}