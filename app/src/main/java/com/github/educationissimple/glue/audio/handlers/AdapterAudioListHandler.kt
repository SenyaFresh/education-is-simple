package com.github.educationissimple.glue.audio.handlers

import com.github.educationissimple.audio.domain.entities.AudioListState
import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import com.github.educationissimple.audio_player.handlers.AudioListPlayerHandler
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.glue.audio.mappers.toAudioListState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterAudioListHandler @Inject constructor(
    private val audioListPlayerHandler: AudioListPlayerHandler
) : AudioListHandler {

    override suspend fun getAudioListState(): Flow<ResultContainer<AudioListState>> {
        return audioListPlayerHandler.getAudioListState()
            .map { container -> container.map { it.toAudioListState() } }
    }

    override suspend fun selectMedia(index: Int) {
        audioListPlayerHandler.selectMedia(index)
    }

    override suspend fun setPosition(positionMs: Long) {
        audioListPlayerHandler.setPosition(positionMs)
    }

    override suspend fun playPause() {
        audioListPlayerHandler.playPause()
    }

    override suspend fun close() {
        audioListPlayerHandler.close()
    }

    override suspend fun next() {
        audioListPlayerHandler.next()
    }

    override suspend fun previous() {
        audioListPlayerHandler.previous()
    }

}