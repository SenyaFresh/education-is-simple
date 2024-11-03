package com.github.educationissimple.audio.domain.usecases.player

import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import javax.inject.Inject

class ControlAudioUseCase @Inject constructor(private val audioListHandler: AudioListHandler) {

    suspend fun playPause() {
        audioListHandler.playPause()
    }

    suspend fun setPosition(position: Float) {
        audioListHandler.setPosition(position)
    }

}