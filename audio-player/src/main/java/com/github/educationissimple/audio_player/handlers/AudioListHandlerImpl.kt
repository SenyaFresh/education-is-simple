package com.github.educationissimple.audio_player.handlers

import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.github.educationissimple.audio_player.entities.Audio
import com.github.educationissimple.audio_player.entities.AudioListState
import com.github.educationissimple.common.Core
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

class AudioListHandlerImpl @Inject constructor(private val player: ExoPlayer) : AudioListHandler, Player.Listener {

    private val state = MutableStateFlow<ResultContainer<AudioListState>>(ResultContainer.Loading)
    private var progressJob: Job? = null

    init {
        player.addListener(this)
    }

    override suspend fun addAudio(audio: Audio) {
        player.addMediaItem(audio.toMediaItem())
        updateState()
    }

    override suspend fun removeAudio(index: Int) {
        player.removeMediaItem(index)
        updateState()
    }

    override suspend fun getAudioListState(): Flow<ResultContainer<AudioListState>> {
        return state
    }

    override suspend fun selectMedia(index: Int) {
        if (index != player.currentMediaItemIndex) {
            player.seekToDefaultPosition(index)
            player.playWhenReady = true
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
        updateState()
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
        val playbackState = when {
            player.isPlaying -> AudioListState.State.AUDIO_PLAYING
            player.playbackState == Player.STATE_BUFFERING -> AudioListState.State.BUFFERING
            player.playbackState == Player.STATE_READY -> AudioListState.State.READY
            else -> AudioListState.State.PLAYING
        }
        val audioListState = AudioListState(
            state = playbackState,
            currentAudioId = player.currentMediaItem?.mediaId?.toLongOrNull(),
            positionMs = player.currentPosition,
            durationMs = player.duration.takeIf { it != C.TIME_UNSET } ?: 0
        )
        state.value = ResultContainer.Done(audioListState)
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

fun Audio.toMediaItem(): MediaItem {
    return MediaItem.Builder()
        .setMediaId(id.toString())
        .setUri(uri)
        .build()
}
