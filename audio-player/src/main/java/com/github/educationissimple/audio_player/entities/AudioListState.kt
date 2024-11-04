package com.github.educationissimple.audio_player.entities

data class AudioListState(
    val state: State,
    val currentAudioId: Long?,
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