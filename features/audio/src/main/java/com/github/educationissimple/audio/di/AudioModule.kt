package com.github.educationissimple.audio.di

import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import com.github.educationissimple.audio.domain.repositories.AudioRepository
import com.github.educationissimple.common.di.Feature
import dagger.Module
import dagger.Provides

/**
 * Dagger module that provides dependencies related to audio functionality.
 *
 */
@Module
class AudioModule {

    /**
     * Provides an instance of [AudioRepository] for audio-related data operations.
     *
     * @param deps The [AudioDeps] interface, which contains the required audio dependencies.
     * @return An instance of [AudioRepository].
     */
    @Provides
    @Feature
    fun provideAudioRepository(deps: AudioDeps): AudioRepository = deps.audioRepository

    /**
     * Provides an instance of [AudioListHandler] for managing audio items.
     *
     * @param deps The [AudioDeps] interface, which contains the required audio dependencies.
     * @return An instance of [AudioListHandler].
     */
    @Provides
    @Feature
    fun provideAudioHandler(deps: AudioDeps): AudioListHandler = deps.audioHandler

}