package com.github.educationissimple.audio_player.handlers

import com.github.educationissimple.audio_player.entities.Audio
import com.github.educationissimple.audio_player.entities.AudioPlayerListState
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.flow.Flow

interface AudioListPlayerHandler {

    suspend fun addAudio(audio: Audio)

    suspend fun removeAudio(index: Int)

    suspend fun getAudioListState(): Flow<ResultContainer<AudioPlayerListState>>

    suspend fun selectMedia(index: Int)

    suspend fun setPosition(positionMs: Long)

    suspend fun playPause()

    suspend fun close()

    suspend fun next()

    suspend fun previous()

}