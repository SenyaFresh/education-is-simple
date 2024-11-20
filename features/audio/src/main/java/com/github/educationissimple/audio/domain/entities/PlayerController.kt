package com.github.educationissimple.audio.domain.entities

/**
 * A sealed class representing the different actions that can be performed by the audio player controller.
 *
 * This class encapsulates various commands for controlling the audio playback, including selecting
 * media, toggling play/pause, seeking to a specific position, and navigating between audio tracks.
 */
sealed class PlayerController {

    /**
     * Represents the action to select a specific media (audio) by its URI.
     *
     * @param uri The URI of the audio to be selected.
     */
    data class SelectMedia(val uri: String): PlayerController()

    /**
     * Represents the action to toggle the play/pause state of the audio player.
     */
    data object PlayPause: PlayerController()

    /**
     * Represents the action to seek to a specific position within the current audio.
     *
     * @param positionMs The position in milliseconds to seek to.
     */
    data class SetPosition(val positionMs: Long): PlayerController()

    /**
     * Represents the action to move to the next audio track in the playlist.
     */
    data object Next: PlayerController()

    /**
     * Represents the action to move to the previous audio track in the playlist.
     */
    data object Previous: PlayerController()

    /**
     * Represents the action to close the audio player.
     */
    data object Close: PlayerController()

}