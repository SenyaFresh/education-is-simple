package com.github.educationissimple.audio.domain.usecases.player

import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import javax.inject.Inject

/**
 * Use case for controlling audio playback.
 */
class ControlAudioUseCase @Inject constructor(private val audioListHandler: AudioListHandler) {

    /**
     * Toggles the playback state of the audio.
     */
    suspend fun playPause() {
        audioListHandler.playPause()
    }

    /**
     * Sets the playback position to a specific value.
     *
     * @param positionMs The position in milliseconds to set.
     */
    suspend fun setPosition(positionMs: Long) {
        audioListHandler.setPosition(positionMs)
    }

}