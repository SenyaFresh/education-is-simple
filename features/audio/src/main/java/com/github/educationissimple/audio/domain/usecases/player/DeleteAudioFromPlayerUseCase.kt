package com.github.educationissimple.audio.domain.usecases.player

import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import javax.inject.Inject

class DeleteAudioFromPlayerUseCase @Inject constructor(private val audioListHandler: AudioListHandler) {

    suspend fun deleteAudio(index: Int) {
        audioListHandler.removeAudio(index)
    }

}