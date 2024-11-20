package com.github.educationissimple.audio.domain.usecases.player

import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import javax.inject.Inject

/**
 * Use case for initializing the audio list.
 */
class InitPlayerUseCase @Inject constructor(private val audioListHandler: AudioListHandler) {

    /**
    * Initializes the audio list with a list of audio items.
    *
    * @param audioItems The list of audio items to be initialized.
    */
    suspend fun initPlayer(audioItems: List<Audio>) {
        audioListHandler.initAudioItems(audioItems)
    }

}