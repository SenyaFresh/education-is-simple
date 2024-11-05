package com.github.educationissimple.glue.audio.di

import com.github.educationissimple.audio.domain.repositories.AudioRepository
import com.github.educationissimple.glue.audio.repositories.AdapterAudioRepository
import dagger.Binds
import dagger.Module

@Module
interface AudioRepositoryModule {

    @Binds
    fun bindAudioRepository(adapterAudioRepository: AdapterAudioRepository): AudioRepository

}