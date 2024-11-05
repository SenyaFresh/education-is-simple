package com.github.educationissimple.audio.domain.usecases.player

import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import javax.inject.Inject

class ChangeSelectedAudioUseCase @Inject constructor(private val audioListHandler: AudioListHandler) {

    suspend fun changeSelectedAudio(index: Int) {
        audioListHandler.selectMedia(index)
    }

    suspend fun close() {
        audioListHandler.close()
    }

    suspend fun next() {
        audioListHandler.next()
    }

    suspend fun previous() {
        audioListHandler.previous()
    }

}