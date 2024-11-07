package com.github.educationissimple.audio.domain.usecases.player

import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import javax.inject.Inject

class AddAudioToPlayerUseCase @Inject constructor(private val audioListHandler: AudioListHandler) {

    suspend fun addAudio(uri: String) {
        audioListHandler.addAudio(uri)
    }

}