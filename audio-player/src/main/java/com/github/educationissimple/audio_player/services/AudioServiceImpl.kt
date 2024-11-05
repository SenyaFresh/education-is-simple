package com.github.educationissimple.audio_player.services

import android.app.Service
import android.content.Intent
import android.os.Build
import androidx.media3.common.Player
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.github.educationissimple.audio_player.di.DaggerPlayerComponent
import com.github.educationissimple.audio_player.di.ExternalDiContainer
import com.github.educationissimple.audio_player.di.ModuleDiContainer
import com.github.educationissimple.audio_player.di.PlayerDepsProvider
import com.github.educationissimple.audio_player.notifications.AudioNotification

class AudioServiceImpl : MediaSessionService(), AudioServiceManager {

    private lateinit var mediaSession: MediaSession
    private lateinit var notification: AudioNotification

    override fun getService(): Service {
        return this
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val moduleDiContainer = ModuleDiContainer()
        val externalDiContainer = ExternalDiContainer()
        DaggerPlayerComponent.builder()
            .deps(PlayerDepsProvider.deps)
            .build()
            .also {
                it.inject(moduleDiContainer)
                it.inject(externalDiContainer)
            }
        mediaSession = moduleDiContainer.mediaSession
        notification = moduleDiContainer.notification

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