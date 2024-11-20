package com.github.educationissimple.glue.audio.di

import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import com.github.educationissimple.audio_player.di.PlayerComponentHolder
import com.github.educationissimple.audio_player.handlers.AudioListPlayerHandler
import com.github.educationissimple.glue.audio.handlers.AdapterAudioListHandler
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Dagger module that provides audio dependencies for the app.
 */
@Module
interface AudioListHandlerModule {

    /**
     * Binds the [AdapterAudioListHandler] to the [AudioListHandler] interface.
     */
    @Binds
    fun bindAudioListHandler(adapterAudioListHandler: AdapterAudioListHandler): AudioListHandler

}

/**
 * Dagger module that provides audio dependencies from player module for the app.
 */
@Module
class AudioListPlayerHandlerModule {

    /**
     * Provides an instance of [AudioListPlayerHandler].
     */
    @Provides
    fun provideAudioListPlayerHandler(): AudioListPlayerHandler = PlayerComponentHolder.getInstance().playerListHandler()

}
