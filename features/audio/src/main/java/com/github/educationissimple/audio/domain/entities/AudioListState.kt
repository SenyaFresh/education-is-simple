package com.github.educationissimple.audio.domain.entities

data class AudioListState(
    val state: State,
    val currentAudioUri: String?,
    val positionMs: Long,
    val durationMs: Long
) {
    enum class State {
        IDLE,
        READY,
        AUDIO_PLAYING,
        BUFFERING,
    }
}