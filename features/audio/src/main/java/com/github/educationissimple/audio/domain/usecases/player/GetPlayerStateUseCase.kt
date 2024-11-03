package com.github.educationissimple.audio.domain.usecases.player

import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import javax.inject.Inject

class GetPlayerStateUseCase @Inject constructor(private val audioListHandler: AudioListHandler) {

    suspend fun getPlayerState() = audioListHandler.getAudioListState()

}