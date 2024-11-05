package com.github.educationissimple.audio.domain.handlers

import com.github.educationissimple.audio.domain.entities.AudioListState
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.flow.Flow

interface AudioListHandler {

    suspend fun getAudioListState(): Flow<ResultContainer<AudioListState>>

    suspend fun selectMedia(index: Int)

    suspend fun setPosition(positionMs: Long)

    suspend fun playPause()

    suspend fun close()

    suspend fun next()

    suspend fun previous()

}