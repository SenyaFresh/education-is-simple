package com.github.educationissimple.audio_player.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.ui.PlayerNotificationManager
import com.github.educationissimple.audio_player.R
import com.github.educationissimple.audio_player.notifications.AudioNotification.Companion.NOTIFICATION_CHANNEL_ID
import com.github.educationissimple.audio_player.notifications.AudioNotification.Companion.NOTIFICATION_CHANNEL_NAME
import com.github.educationissimple.audio_player.notifications.AudioNotification.Companion.NOTIFICATION_ID
import com.github.educationissimple.audio_player.services.AudioServiceManager
import javax.inject.Inject

/**
 * Implementation of [AudioNotification] for managing audio playback notifications.
 * This class integrates with [PlayerNotificationManager] to provide a customizable
 * notification for controlling audio playback and integrates with a foreground service
 * for persistent playback.
 *
 * @param context The [Context] used to access application resources and system services.
 * @param mediaSession The [MediaSession] used for handling media controls and metadata.
 */
@UnstableApi
class AudioNotificationImpl @Inject constructor(
    private val context: Context,
    private val mediaSession: MediaSession
): AudioNotification {
    private val notificationManager = NotificationManagerCompat.from(context)
    private var playerNotificationManager: PlayerNotificationManager? = null

    init {
            createNotificationChannel()
    }

    /**
     * Starts the audio playback notification and binds it to the provided service.
     *
     * @param serviceManager The [AudioServiceManager] managing the audio playback service.
     */
    override fun startAudioNotification(serviceManager: AudioServiceManager) {
        if (playerNotificationManager == null) {
            buildNotification()
        }
        playerNotificationManager?.setPlayer(mediaSession.player)
        startForeGroundNotificationService(serviceManager.getService())
    }

    /**
     * Stops the audio playback notification by unbinding it from the player.
     */
    override fun stopAudioNotification() {
        playerNotificationManager?.setPlayer(null)
    }

    /**
     * Builds and configures the [PlayerNotificationManager] used for displaying the audio playback notification.
     */
    @OptIn(UnstableApi::class)
    private fun buildNotification() {
        playerNotificationManager = PlayerNotificationManager.Builder(
            context,
            NOTIFICATION_ID,
            NOTIFICATION_CHANNEL_ID
        )
            .setMediaDescriptionAdapter(
                AudioNotificationAdapter(
                    context = context,
                    pendingIntent = mediaSession.sessionActivity
                )
            )
            .setSmallIconResourceId(R.drawable.ic_audio_playlist)
            .build()
            .apply {
                setMediaSessionToken(mediaSession.platformToken)
                setUseFastForwardActionInCompactView(true)
                setUseRewindActionInCompactView(true)
                setUseNextActionInCompactView(true)
                setPriority(NotificationCompat.PRIORITY_LOW)
            }
    }

    /**
     * Starts the foreground service with a notification.
     *
     * @param service The [Service] instance to be bound to the foreground notification.
     */
    private fun startForeGroundNotificationService(service: Service) {
        val notification = Notification.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        service.startForeground(NOTIFICATION_ID, notification)
    }

    /**
     * Creates a notification channel for audio playback notifications.
     */
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }
}