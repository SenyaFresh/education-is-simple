package com.github.educationissimple.audio_player.handlers

import com.github.educationissimple.audio_player.entities.Audio
import com.github.educationissimple.audio_player.entities.AudioListState
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.flow.Flow

interface AudioListHandler {

    suspend fun addAudio(audio: Audio)

    suspend fun removeAudio(index: Int)

    suspend fun getAudioListState(): Flow<ResultContainer<AudioListState>>

    suspend fun selectMedia(index: Int)

    suspend fun setPosition(positionMs: Long)

    suspend fun playPause()

    suspend fun close()

    suspend fun next()

    suspend fun previous()

}