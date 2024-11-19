package com.github.educationissimple.audio_player.notifications

import com.github.educationissimple.audio_player.services.AudioServiceManager

/**
 * Interface for managing audio playback notifications. Provides methods to start and stop
 * a notification, ensuring audio playback control is visible and accessible to the user.
 */
interface AudioNotification {

    /**
     * Starts the audio playback notification. This method should be called when audio playback begins,
     * enabling the user to control playback through the notification.
     *
     * @param serviceManager An instance of [AudioServiceManager] that manages the audio service lifecycle.
     */
    fun startAudioNotification(serviceManager: AudioServiceManager)

    /**
     * Stops the audio playback notification. This method should be called when audio playback is
     * stopped, preventing the user from interacting with the notification.
     */
    fun stopAudioNotification()

    companion object {
        /**
         * The unique identifier for the audio playback notification channel.
         */
        internal const val NOTIFICATION_ID = 100

        /**
         * The name of the audio playback notification channel.
         */
        internal const val NOTIFICATION_CHANNEL_NAME = "app.AUDIO_CHANNEL_NAME"

        /**
         * The unique identifier for the audio playback notification channel.
         */
        internal const val NOTIFICATION_CHANNEL_ID = "app.AUDIO_CHANNEL_ID"
    }
}