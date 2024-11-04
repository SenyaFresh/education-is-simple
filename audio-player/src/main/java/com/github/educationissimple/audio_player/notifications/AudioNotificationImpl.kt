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
import com.github.educationissimple.audio_player.services.AudioServiceManager
import javax.inject.Inject

class AudioNotificationImpl @Inject constructor(
    private val context: Context,
    private val mediaSession: MediaSession
): AudioNotification {
    private val notificationManager = NotificationManagerCompat.from(context)

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun startAudioNotification(serviceManager: AudioServiceManager) {
        buildNotification()
        startForeGroundNotificationService(serviceManager.getService())
    }

    @OptIn(UnstableApi::class)
    private fun buildNotification() {
        PlayerNotificationManager.Builder(
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
            .also {
                it.setMediaSessionToken(mediaSession.platformToken)
                it.setUseFastForwardActionInCompactView(true)
                it.setUseRewindActionInCompactView(true)
                it.setUseNextActionInCompactView(true)
                it.setPriority(NotificationCompat.PRIORITY_LOW)
                it.setPlayer(mediaSession.player)
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

    companion object {
        private const val NOTIFICATION_ID = 111
        private const val NOTIFICATION_CHANNEL_NAME = "app.AUDIO_CHANNEL_NAME"
        private const val NOTIFICATION_CHANNEL_ID = "app.AUDIO_CHANNEL_ID"
    }
}