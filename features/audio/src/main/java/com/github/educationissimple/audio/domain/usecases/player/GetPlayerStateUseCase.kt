package com.github.educationissimple.audio.domain.usecases.player

import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import javax.inject.Inject

/**
 * Use case for getting the current state of the audio list.
 */
class GetPlayerStateUseCase @Inject constructor(private val audioListHandler: AudioListHandler) {

    /**
     * Retrieves the current state of the audio list.
     */
    suspend fun getPlayerState() = audioListHandler.getAudioListState()

}