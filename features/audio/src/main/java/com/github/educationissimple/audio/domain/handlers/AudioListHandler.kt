package com.github.educationissimple.audio.domain.handlers

import com.github.educationissimple.audio.domain.entities.AudioListState
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.flow.Flow

interface AudioListHandler {

    suspend fun getAudioListState(): Flow<ResultContainer<AudioListState>>

    suspend fun selectMedia(id: Long)

    suspend fun setPosition(position: Float)

    suspend fun playPause()

    suspend fun close()

    suspend fun next()

    suspend fun previous()

}