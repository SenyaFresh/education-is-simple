package com.github.educationissimple.audio_player.entities

data class AudioPlayerListState(
    val state: State,
    val currentAudioUri: String?,
    val positionMs: Long,
    val durationMs: Long
) {
    enum class State {
        READY,
        AUDIO_PLAYING,
        BUFFERING,
        PLAYING
    }
}