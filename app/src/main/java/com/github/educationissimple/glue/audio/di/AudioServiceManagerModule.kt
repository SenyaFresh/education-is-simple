package com.github.educationissimple.glue.audio.di

import com.github.educationissimple.audio_player.di.PlayerComponentHolder
import com.github.educationissimple.audio_player.services.AudioServiceManager
import dagger.Module
import dagger.Provides

/**
 * Dagger module that provides [AudioServiceManager] dependencies for the app.
 */
@Module
class AudioServiceManagerModule {

    /**
     * Provides an instance of [AudioServiceManager].
     */
    @Provides
    fun bindAudioServiceManager(): AudioServiceManager = PlayerComponentHolder.getInstance().audioServiceManager()

}