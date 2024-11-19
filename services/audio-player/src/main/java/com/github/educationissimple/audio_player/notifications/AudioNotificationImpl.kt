package com.github.educationissimple.audio_player.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.os.Build
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
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

@UnstableApi
class AudioNotificationImpl @Inject constructor(
    private val context: Context,
    private val mediaSession: MediaSession
): AudioNotification {
    private val notificationManager = NotificationManagerCompat.from(context)
    private var playerNotificationManager: PlayerNotificationManager? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun startAudioNotification(serviceManager: AudioServiceManager) {
        if (playerNotificationManager == null) {
            buildNotification()
        }
        playerNotificationManager?.setPlayer(mediaSession.player)
        startForeGroundNotificationService(serviceManager.getService())
    }

    override fun stopAudioNotification() {
        playerNotificationManager?.setPlayer(null)
    }

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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startForeGroundNotificationService(service: Service) {
        val notification = Notification.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        service.startForeground(NOTIFICATION_ID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }
}