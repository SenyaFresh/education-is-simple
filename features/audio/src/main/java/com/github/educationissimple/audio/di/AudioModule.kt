package com.github.educationissimple.audio.di

import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import com.github.educationissimple.audio.domain.repositories.AudioRepository
import com.github.educationissimple.common.di.Feature
import dagger.Module
import dagger.Provides

@Module
class AudioModule {

    @Provides
    @Feature
    fun provideAudioRepository(deps: AudioDeps): AudioRepository = deps.audioRepository

    @Provides
    @Feature
    fun provideAudioHandler(deps: AudioDeps): AudioListHandler = deps.audioHandler

}