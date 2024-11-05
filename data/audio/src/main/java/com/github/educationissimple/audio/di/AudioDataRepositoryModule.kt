package com.github.educationissimple.audio.di

import com.github.educationissimple.audio.repositories.AudioDataRepository
import com.github.educationissimple.audio.repositories.RoomAudioDataRepository
import com.github.educationissimple.common.di.AppScope
import dagger.Binds
import dagger.Module

@Module(includes = [AudioDataSourceModule::class])
interface AudioDataRepositoryModule {

    @Binds
    @AppScope
    fun bindAudioDataRepository(roomAudioDataRepository: RoomAudioDataRepository): AudioDataRepository

}