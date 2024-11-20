package com.github.educationissimple.glue.audio.di

import com.github.educationissimple.audio.domain.repositories.AudioRepository
import com.github.educationissimple.glue.audio.repositories.AdapterAudioRepository
import dagger.Binds
import dagger.Module

/**
 * Dagger module that provides audio repository dependency for the app.
 */
@Module
interface AudioRepositoryModule {

    /**
     * Binds the [AdapterAudioRepository] to the [AudioRepository] interface.
     */
    @Binds
    fun bindAudioRepository(adapterAudioRepository: AdapterAudioRepository): AudioRepository

}