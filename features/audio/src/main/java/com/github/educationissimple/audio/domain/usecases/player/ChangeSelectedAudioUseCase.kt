package com.github.educationissimple.audio.domain.usecases.player

import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import javax.inject.Inject

/**
 * Use case for changing the selected audio.
 */
class ChangeSelectedAudioUseCase @Inject constructor(private val audioListHandler: AudioListHandler) {

    /**
     * Changes the selected audio.
     *
     * @param index The index of the audio to be selected.
     */
    suspend fun changeSelectedAudio(index: Int) {
        audioListHandler.selectMedia(index)
    }

    /**
     * Closes the audio list.
     */
    suspend fun close() {
        audioListHandler.close()
    }

    /**
     * Moves to the next audio.
     */
    suspend fun next() {
        audioListHandler.next()
    }

    /**
     * Moves to the previous audio.
     */
    suspend fun previous() {
        audioListHandler.previous()
    }

}