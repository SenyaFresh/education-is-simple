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

class RealAudioListPlayerHandler @Inject constructor(
    private val player: ExoPlayer
) : AudioListPlayerHandler, Player.Listener {

    private val state =
        MutableStateFlow<ResultContainer<AudioPlayerListState>>(ResultContainer.Loading)
    private var progressJob: Job? = null
    private var initialized = false
    private var closed = false

    init {
        player.addListener(this)
        player.playWhenReady = false
    }

    override suspend fun initAudioItems(audioItemItems: List<AudioItem>) {
        player.setMediaItems(audioItemItems.map { it.toMediaItem() })
        initialized = true
    }

    override suspend fun addAudio(audioItem: AudioItem) {
        player.addMediaItem(audioItem.toMediaItem())
        updateState()
    }

    override suspend fun removeAudio(index: Int) {
        if (index == player.currentMediaItemIndex) {
            player.removeMediaItem(index)
            close()
        } else {
            player.removeMediaItem(index)
            updateState()
        }
    }

    override suspend fun getAudioListState(): Flow<ResultContainer<AudioPlayerListState>> {
        return state
    }

    override suspend fun selectMedia(index: Int) {
        if (closed) {
            closed = false
        }
        if (initialized && player.playbackState == Player.STATE_IDLE) {
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

    override suspend fun setPosition(positionMs: Long) {
        player.seekTo(positionMs)
        updateState()
    }

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

    override suspend fun close() {
        player.run {
            stop()
            playWhenReady = false
            seekToDefaultPosition(0)
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

    override suspend fun next() {
        player.seekToNext()
        updateState()
    }

    override suspend fun previous() {
        player.seekToPrevious()
        updateState()
    }

    private fun startProgressing() {
        stopProgressing()
        progressJob = Core.globalScope.launch {
            while (isActive) {
                updateState()
                delay(500)
            }
        }
    }

    private fun stopProgressing() {
        progressJob?.cancel()
        progressJob = null
    }

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

fun AudioItem.toMediaItem(): MediaItem {
    return MediaItem.Builder()
        .setUri(uri)
        .build()
}
