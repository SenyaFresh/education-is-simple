package com.github.educationissimple.audio.domain.usecases.player

import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import javax.inject.Inject

/**
 * Use case for adding an audio item to the player.
 */
class AddAudioToPlayerUseCase @Inject constructor(private val audioListHandler: AudioListHandler) {

    /**
     * Adds a new audio item to the list.
     *
     * @param uri The URI of the audio to be added.
     */
    suspend fun addAudio(uri: String) {
        audioListHandler.addAudio(uri)
    }

}