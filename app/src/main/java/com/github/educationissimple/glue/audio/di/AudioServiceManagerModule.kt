package com.github.educationissimple.glue.audio.di

import com.github.educationissimple.audio_player.di.PlayerComponentHolder
import com.github.educationissimple.audio_player.services.AudioServiceManager
import dagger.Module
import dagger.Provides

@Module
class AudioServiceManagerModule {

    @Provides
    fun bindAudioServiceManager(): AudioServiceManager = PlayerComponentHolder.getInstance().audioServiceManager()

}