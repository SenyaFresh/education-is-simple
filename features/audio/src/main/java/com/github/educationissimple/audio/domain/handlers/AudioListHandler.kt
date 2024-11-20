package com.github.educationissimple.audio.domain.handlers

import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.entities.AudioListState
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.flow.Flow

/**
 * An interface that defines operations for managing and controlling a list of audio items.
 *
 * This interface provides methods to initialize, modify, and interact with an audio list, including
 * actions like adding/removing audio, changing the playback position, and controlling playback state.
 */
interface AudioListHandler {

    /**
     * Initializes the audio list with a list of audio items.
     *
     * @param audioItems The list of audio items to be initialized.
     */
    suspend fun initAudioItems(audioItems: List<Audio>)

    /**
     * Adds a new audio item to the list.
     *
     * @param uri The URI of the audio to be added.
     */
    suspend fun addAudio(uri: String)

    /**
     * Removes an audio item from the list at the specified index.
     *
     * @param index The index of the audio item to be removed.
     */
    suspend fun removeAudio(index: Int)

    /**
     * Retrieves the current state of the audio list.
     *
     * @return A [Flow] emitting the [ResultContainer] containing the current [AudioListState].
     */
    suspend fun getAudioListState(): Flow<ResultContainer<AudioListState>>

    /**
     * Selects a media (audio item) from the list.
     *
     * @param index The index of the audio item to be selected.
     */
    suspend fun selectMedia(index: Int)

    /**
     * Sets the playback position to a specific value.
     *
     * @param positionMs The position in milliseconds to set.
     */
    suspend fun setPosition(positionMs: Long)

    /**
     * Toggles the playback state of the audio list.
     */
    suspend fun playPause()

    /**
     * Closes the audio list.
     */
    suspend fun close()

    /**
     * Moves to the next audio item in the list.
     */
    suspend fun next()

    /**
     * Moves to the previous audio item in the list.
     */
    suspend fun previous()

}