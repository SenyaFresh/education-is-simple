package com.github.educationissimple.audio_player.handlers

import com.github.educationissimple.audio_player.entities.AudioItem
import com.github.educationissimple.audio_player.entities.AudioPlayerListState
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.flow.Flow

/**
 * Interface that defines the contract for managing and controlling an audio player
 * with a list of audio items. Provides methods for initializing, updating, and controlling
 * playback of audio items in a list.
 */
interface AudioListPlayerHandler {

    /**
     * Initializes the list of audio items for playback.
     *
     * @param audioItemItems A list of [AudioItem] objects representing the audio files to load.
     */
    suspend fun initAudioItems(audioItemItems: List<AudioItem>)

    /**
     * Adds a new audio item to the list.
     *
     * @param audioItem The [AudioItem] to be added to the list.
     */
    suspend fun addAudio(audioItem: AudioItem)

    /**
     * Removes an audio item from the list at the specified index.
     *
     * @param index The index of the audio item to be removed.
     */
    suspend fun removeAudio(index: Int)

    /**
     * Retrieves the current state of the audio player as a [Flow] of [ResultContainer].
     *
     * @return A [Flow] emitting the current [AudioPlayerListState] wrapped in a [ResultContainer].
     */
    suspend fun getAudioListState(): Flow<ResultContainer<AudioPlayerListState>>

    /**
     * Selects a specific media item from the list by its index.
     *
     * @param index The index of the media item to select.
     */
    suspend fun selectMedia(index: Int)

    /**
     * Sets the playback position for the currently selected audio item.
     *
     * @param positionMs The playback position in milliseconds.
     */
    suspend fun setPosition(positionMs: Long)

    /**
     * Toggles between playing and pausing the currently selected audio item.
     */
    suspend fun playPause()

    /**
     * Closes the audio player and releases all resources.
     */
    suspend fun close()

    /**
     * Skips to the next audio item in the list.
     */
    suspend fun next()

    /**
     * Returns to the previous audio item in the list.
     */
    suspend fun previous()
}
