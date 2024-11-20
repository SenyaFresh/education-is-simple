package com.github.educationissimple.audio.domain.entities

/**
 * Represents the state of an audio player, providing information about its current status,
 * audio being played, and playback timing.
 *
 * @property state The current state of the audio player. See [State] for possible values.
 * @property currentAudioUri The URI of the currently loaded or playing audio file, or `null` if no audio is loaded.
 * @property positionMs The current playback position in milliseconds.
 * @property durationMs The total duration of the currently loaded audio in milliseconds.
 */
data class AudioListState(
    val state: State,
    val currentAudioUri: String?,
    val positionMs: Long,
    val durationMs: Long
) {
    /**
     * Enum representing the various states the audio player can be in.
     */
    enum class State {
        /**
         * The audio player is idle and not ready to play audio.
         */
        IDLE,
        /**
         * The audio player is ready to play audio but playback has not started.
         */
        READY,
        /**
         * The audio player is currently playing audio.
         */
        AUDIO_PLAYING,
        /**
         * The audio player is buffering data and temporarily unable to play audio.
         */
        BUFFERING,
    }
}