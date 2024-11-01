package com.github.educationissimple.audio.domain.usecases.player

import com.github.educationissimple.audio.domain.handlers.PlayerHandler
import javax.inject.Inject

class ControlAudioUseCase @Inject constructor(private val playerHandler: PlayerHandler) {

    suspend fun playPause() {
        playerHandler.playPause()
    }

    suspend fun setPosition(position: Float) {
        playerHandler.setPosition(position)
    }

}