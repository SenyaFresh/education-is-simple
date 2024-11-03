package com.github.educationissimple.audio_player.entities

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