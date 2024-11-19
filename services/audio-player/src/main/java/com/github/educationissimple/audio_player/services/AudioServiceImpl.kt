package com.github.educationissimple.audio_player.services

import android.app.Service
import android.content.Intent
import androidx.media3.common.Player.STATE_IDLE
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.github.educationissimple.audio_player.di.PlayerComponentHolder
import com.github.educationissimple.audio_player.notifications.AudioNotification
import javax.inject.Inject
import kotlin.reflect.KClass

class AudioServiceImpl @Inject constructor() : MediaSessionService(), AudioServiceManager {

    private lateinit var mediaSession: MediaSession
    private lateinit var notification: AudioNotification

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaSession = PlayerComponentHolder.getInstance().mediaSession()
        notification = PlayerComponentHolder.getInstance().notification()

        startAudioNotification()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startAudioNotification() {
        notification.startAudioNotification(this)
    }

    private fun stopAudioNotification() {
        notification.stopAudioNotification()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession {
        return mediaSession
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAudioNotification()
        mediaSession.player.apply {
            if (playbackState != STATE_IDLE) {
                seekToDefaultPosition(0)
                playWhenReady = false
                stop()
            }
        }
    }

    override fun getServiceClass(): KClass<out Service> {
        return this::class
    }

    override fun getService(): Service {
        return this
    }

}