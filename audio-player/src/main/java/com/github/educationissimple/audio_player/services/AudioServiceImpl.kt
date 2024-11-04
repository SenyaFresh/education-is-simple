package com.github.educationissimple.audio_player.services

import android.app.Service
import android.content.Intent
import android.os.Build
import androidx.media3.common.Player
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.github.educationissimple.audio_player.notifications.AudioNotificationImpl
import javax.inject.Inject

class AudioServiceImpl : MediaSessionService(), AudioServiceManager {

    @Inject
    lateinit var mediaSession: MediaSession

    @Inject
    lateinit var notification: AudioNotificationImpl

    override fun getService(): Service {
        return this
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.startAudioNotification(this)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession {
        return mediaSession
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession.apply {
            release()
            if (player.playbackState != Player.STATE_IDLE) {
                player.seekTo(0)
                player.playWhenReady = false
                player.stop()
            }
        }
    }

}