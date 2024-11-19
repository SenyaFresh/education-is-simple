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

/**
 * Implementation of [MediaSessionService] and [AudioServiceManager] for managing audio playback
 * as a foreground service. This service handles the lifecycle of the media session and
 * audio notifications.
 *
 * This service uses [MediaSession] for media controls and [AudioNotification] for
 * managing playback notifications.
 */
class AudioServiceImpl @Inject constructor() : MediaSessionService(), AudioServiceManager {

    private lateinit var mediaSession: MediaSession
    private lateinit var notification: AudioNotification

    /**
     * Initializes the [MediaSession] and [AudioNotification].
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaSession = PlayerComponentHolder.getInstance().mediaSession()
        notification = PlayerComponentHolder.getInstance().notification()

        startAudioNotification()
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * Starts the audio notification and binds it to the service.
     * Delegates to the [AudioNotification] implementation.
     */
    private fun startAudioNotification() {
        notification.startAudioNotification(this)
    }

    /**
     * Stops the audio notification by unbinding it from the service.
     * Delegates to the [AudioNotification] implementation.
     */
    private fun stopAudioNotification() {
        notification.stopAudioNotification()
    }

    /**
     * Called by the framework when a [MediaSession] is requested by a media controller.
     * Returns the current [MediaSession] for controlling playback.
     */
    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession {
        return mediaSession
    }

    /**
     * Cleans up the media session, stops the notification, and resets the player state.
     */
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

    /**
     * Returns the class of the service for use in notification binding and callbacks.
     *
     * @return The [KClass] of this [Service].
     */
    override fun getServiceClass(): KClass<out Service> {
        return this::class
    }

    /**
     * Returns the current instance of the service for use in foreground notification binding.
     *
     * @return The current [Service] instance.
     */
    override fun getService(): Service {
        return this
    }

}