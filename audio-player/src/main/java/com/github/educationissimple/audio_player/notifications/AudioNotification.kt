package com.github.educationissimple.audio_player.notifications

import com.github.educationissimple.audio_player.services.AudioServiceManager

interface AudioNotification {

    fun startAudioNotification(serviceManager: AudioServiceManager)

    fun stopAudioNotification()

    companion object {
        internal const val NOTIFICATION_ID = 111
        internal const val NOTIFICATION_CHANNEL_NAME = "app.AUDIO_CHANNEL_NAME"
        internal const val NOTIFICATION_CHANNEL_ID = "app.AUDIO_CHANNEL_ID"
    }
}