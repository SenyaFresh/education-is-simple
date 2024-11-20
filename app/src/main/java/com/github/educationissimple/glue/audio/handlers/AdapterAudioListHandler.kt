package com.github.educationissimple.glue.audio.handlers

import android.app.Application
import android.net.Uri
import com.github.educationissimple.R
import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.entities.AudioListState
import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import com.github.educationissimple.audio_player.handlers.AudioListPlayerHandler
import com.github.educationissimple.common.Core
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.common.UserFriendlyException
import com.github.educationissimple.glue.audio.mappers.toAudio
import com.github.educationissimple.glue.audio.mappers.toAudioDataEntity
import com.github.educationissimple.glue.audio.mappers.toAudioItem
import com.github.educationissimple.glue.audio.mappers.toAudioListState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Adapter to interact with an underlying audio player handler, represented by the
 * [AudioListPlayerHandler] class. This adapter is responsible for managing a list of audio items
 * and performing various actions such as initialization, playback control, and state management.
 *
 * @param audioListPlayerHandler The handler that performs audio player operations.
 * @param application The application context used to access resources and perform application-level actions.
 */
class AdapterAudioListHandler @Inject constructor(
    private val audioListPlayerHandler: AudioListPlayerHandler,
    private val application: Application
) : AudioListHandler {

    /**
     * Initializes the audio list with a list of audio items.
     */
    override suspend fun initAudioItems(audioItems: List<Audio>) {
        audioListPlayerHandler.initAudioItems(audioItems.map { it.toAudioItem() })
    }

    /**
     * Adds a new audio item to the list.
     */
    override suspend fun addAudio(uri: String) {
        val audio = Uri.parse(uri).toAudioDataEntity(application)?.toAudio()?.toAudioItem()
                ?: throw UserFriendlyException(Core.resources.getString(R.string.add_audio_error))
        audioListPlayerHandler.addAudio(audio)
    }

    /**
     * Removes an audio item from the list at the specified index.
     *
     * @param index The index of the audio item to be removed.
     */
    override suspend fun removeAudio(index: Int) {
        audioListPlayerHandler.removeAudio(index)
    }

    /**
     * Retrieves the current state of the audio player as a [Flow] of [ResultContainer].
     *
     * @return A [Flow] emitting the current [AudioListState] wrapped in a [ResultContainer].
     */
    override suspend fun getAudioListState(): Flow<ResultContainer<AudioListState>> {
        return audioListPlayerHandler.getAudioListState()
            .map { container -> container.map { it.toAudioListState() } }
    }

    /**
     * Selects a specific media item from the list by its index.
     *
     * @param index The index of the media item to select.
     */
    override suspend fun selectMedia(index: Int) {
        audioListPlayerHandler.selectMedia(index)
    }

    /**
     * Sets the playback position for the currently selected audio item.
     *
     * @param positionMs The playback position in milliseconds.
     */
    override suspend fun setPosition(positionMs: Long) {
        audioListPlayerHandler.setPosition(positionMs)
    }

    /**
     * Toggles between playing and pausing the currently selected audio item.
     */
    override suspend fun playPause() {
        audioListPlayerHandler.playPause()
    }

    /**
     * Closes the audio player and releases all resources.
     */
    override suspend fun close() {
        audioListPlayerHandler.close()
    }

    /**
     * Skips to the next audio item in the list.
     */
    override suspend fun next() {
        audioListPlayerHandler.next()
    }

    /**
     * Returns to the previous audio item in the list.
     */
    override suspend fun previous() {
        audioListPlayerHandler.previous()
    }

}