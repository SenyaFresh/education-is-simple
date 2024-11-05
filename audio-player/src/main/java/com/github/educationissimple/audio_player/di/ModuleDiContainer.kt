package com.github.educationissimple.audio_player.di

import androidx.media3.session.MediaSession
import com.github.educationissimple.audio_player.notifications.AudioNotification
import javax.inject.Inject

class ModuleDiContainer {

    @Inject
    lateinit var mediaSession: MediaSession

    @Inject
    lateinit var notification: AudioNotification

}