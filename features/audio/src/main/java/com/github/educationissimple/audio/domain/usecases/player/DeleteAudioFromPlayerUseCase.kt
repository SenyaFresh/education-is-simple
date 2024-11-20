package com.github.educationissimple.audio.domain.usecases.player

import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import javax.inject.Inject

/**
 * Use case for deleting an audio item from the player.
 */
class DeleteAudioFromPlayerUseCase @Inject constructor(private val audioListHandler: AudioListHandler) {

    /**
     * Deletes an audio item.
     *
     * @param index The index of the audio to be deleted.
     */
    suspend fun deleteAudio(index: Int) {
        audioListHandler.removeAudio(index)
    }

}