package com.github.educationissimple.audio.domain.usecases.player

import com.github.educationissimple.audio.domain.handlers.PlayerHandler
import javax.inject.Inject

class ChangeSelectedAudioUseCase @Inject constructor(private val playerHandler: PlayerHandler) {

    suspend fun changeSelectedAudio(id: Long) {
        playerHandler.selectMedia(id)
    }

    suspend fun close() {
        playerHandler.close()
    }

    suspend fun next() {
        playerHandler.next()
    }

    suspend fun previous() {
        playerHandler.previous()
    }

}