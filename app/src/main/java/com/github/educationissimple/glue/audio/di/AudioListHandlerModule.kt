package com.github.educationissimple.glue.audio.di

import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import com.github.educationissimple.audio_player.di.ExternalDiContainer
import com.github.educationissimple.audio_player.handlers.AudioListPlayerHandler
import com.github.educationissimple.glue.audio.handlers.AdapterAudioListHandler
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface AudioListHandlerModule {

    @Binds
    fun bindAudioListHandler(adapterAudioListHandler: AdapterAudioListHandler): AudioListHandler

}

@Module
class AudioListPlayerHandlerModule {

    @Provides
    fun provideAudioListPlayerHandler(): AudioListPlayerHandler = ExternalDiContainer.playerListHandler

}
