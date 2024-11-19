package com.github.educationissimple.audio_player.handlers

import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.github.educationissimple.audio_player.entities.AudioItem
import com.github.educationissimple.audio_player.entities.AudioPlayerListState
import com.github.educationissimple.common.Core
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Implementation of [AudioListPlayerHandler] that manages an audio player using ExoPlayer.
 * Provides functionality to control playback, manage the audio list, and update player state.
 *
 * @property player The ExoPlayer instance used to handle audio playback.
 * @constructor Injects an [ExoPlayer] instance for handling audio playback.
 */
class RealAudioListPlayerHandler @Inject constructor(
    private val player: ExoPlayer
) : AudioListPlayerHandler, Player.Listener {

    /**
     * State flow that emits the current state of the audio player.
     */
    private val state =
        MutableStateFlow<ResultContainer<AudioPlayerListState>>(ResultContainer.Loading)

    /**
     * Background job to track playback progress.
     */
    private var progressJob: Job? = null

    /**
     * Indicates if the player has been closed.
     */
    private var closed = false

    init {
        player.addListener(this)
        player.playWhenReady = false
    }

    /**
     * Initializes the list of audio items in the player.
     *
     * @param audioItemItems List of [AudioItem] objects to be added to the player.
     */
    override suspend fun initAudioItems(audioItemItems: List<AudioItem>) {
        player.setMediaItems(audioItemItems.map { it.toMediaItem() })
    }

    /**
     * Adds a new audio item to the player.
     *
     * @param audioItem The [AudioItem] to be added.
     */
    override suspend fun addAudio(audioItem: AudioItem) {
        player.addMediaItem(audioItem.toMediaItem())
        updateState()
    }

    /**
     * Removes an audio item from the player by index.
     *
     * @param index The index of the audio item to be removed.
     */
    override suspend fun removeAudio(index: Int) {
        if (index == player.currentMediaItemIndex) {
            player.removeMediaItem(index)
            close()
        } else {
            player.removeMediaItem(index)
            updateState()
        }
    }

    /**
     * Returns a flow of the current audio player state.
     *
     * @return A [Flow] emitting [ResultContainer] with [AudioPlayerListState].
     */
    override suspend fun getAudioListState(): Flow<ResultContainer<AudioPlayerListState>> {
        return state
    }

    /**
     * Selects an audio item by index and starts playback if needed.
     *
     * @param index The index of the audio item to select.
     */
    override suspend fun selectMedia(index: Int) {
        if (closed) closed = false
        if (player.playbackState == Player.STATE_IDLE) {
            player.prepare()
            player.playWhenReady = true
        }
        if (index != player.currentMediaItemIndex) {
            player.seekToDefaultPosition(index)
            startProgressing()
        } else {
            playPause()
        }
        updateState()
    }

    /**
     * Sets the playback position for the current audio item.
     *
     * @param positionMs The desired playback position in milliseconds.
     */
    override suspend fun setPosition(positionMs: Long) {
        player.seekTo(positionMs)
        updateState()
    }

    /**
     * Toggles play/pause for the current audio item.
     */
    override suspend fun playPause() {
        if (player.isPlaying) {
            player.pause()
            stopProgressing()
        } else {
            player.play()
            startProgressing()
        }
        updateState()
    }

    /**
     * Stops playback, resets the player, and closes resources.
     */
    override suspend fun close() {
        player.apply {
            seekToDefaultPosition(0)
            playWhenReady = false
            stop()
        }
        stopProgressing()
        state.value = ResultContainer.Done(
            AudioPlayerListState(
                state = AudioPlayerListState.State.IDLE,
                currentAudioUri = null,
                positionMs = 0,
                durationMs = 0
            )
        )
        closed = true
    }

    /**
     * Skips to the next audio item in the list.
     */
    override suspend fun next() {
        player.seekToNext()
        updateState()
    }

    /**
     * Returns to the previous audio item in the list.
     */
    override suspend fun previous() {
        player.seekToPrevious()
        updateState()
    }

    /**
     * Starts a background job to periodically update the playback progress.
     */
    private fun startProgressing() {
        stopProgressing()
        progressJob = Core.globalScope.launch {
            while (isActive) {
                updateState()
                delay(500)
            }
        }
    }

    /**
     * Stops the progress-tracking background job.
     */
    private fun stopProgressing() {
        progressJob?.cancel()
        progressJob = null
    }

    /**
     * Updates the state of the player and emits it to the state flow.
     */
    private fun updateState() {
        if (closed) return
        val playbackState = when {
            player.isPlaying -> AudioPlayerListState.State.AUDIO_PLAYING
            player.playbackState == Player.STATE_BUFFERING -> AudioPlayerListState.State.BUFFERING
            player.playbackState == Player.STATE_READY -> AudioPlayerListState.State.READY
            else -> AudioPlayerListState.State.IDLE
        }
        val audioPlayerListState = AudioPlayerListState(
            state = playbackState,
            currentAudioUri = player.currentMediaItem?.localConfiguration?.uri?.toString(),
            positionMs = player.currentPosition,
            durationMs = player.duration.takeIf { it != C.TIME_UNSET } ?: 0
        )
        state.value = ResultContainer.Done(audioPlayerListState)
    }

    // Player.Listener callbacks for automatic state updates.

    override fun onPlaybackStateChanged(playbackState: Int) {
        updateState()
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        updateState()
        if (isPlaying) startProgressing() else stopProgressing()
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        updateState()
    }
}

/**
 * Extension function to convert an [AudioItem] to a [MediaItem].
 *
 * @return A [MediaItem] representation of the [AudioItem].
 */
fun AudioItem.toMediaItem(): MediaItem {
    return MediaItem.Builder()
        .setUri(uri)
        .build()
}
