package com.github.educationissimple.audio.domain.usecases.player

import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import javax.inject.Inject

class InitPlayerUseCase @Inject constructor(private val audioListHandler: AudioListHandler) {

    suspend fun initPlayer(audioItems: List<Audio>) {
        audioListHandler.initAudioItems(audioItems)
    }

}