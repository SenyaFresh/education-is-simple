package com.github.educationissimple.audio_player.di

import com.github.educationissimple.audio_player.handlers.AudioListHandler
import com.github.educationissimple.audio_player.handlers.AudioListHandlerImpl
import com.github.educationissimple.common.di.AppScope
import dagger.Binds
import dagger.Module

@Module
interface AudioListHandlerModule {

    @Binds
    @AppScope
    fun bindAudioListHandler(audioListHandlerImpl: AudioListHandlerImpl): AudioListHandler


}