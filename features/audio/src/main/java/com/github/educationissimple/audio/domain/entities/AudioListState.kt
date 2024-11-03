package com.github.educationissimple.audio.domain.entities

data class AudioListState(
    val state: State,
    val progress: Float,
    val duration: Long
) {
    enum class State {
        READY,
        AUDIO_PLAYING,
        BUFFERING,
        PROGRESSING,
        PLAYING
    }
}