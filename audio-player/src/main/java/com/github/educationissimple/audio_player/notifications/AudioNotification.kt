package com.github.educationissimple.audio_player.notifications

import com.github.educationissimple.audio_player.services.AudioServiceManager

interface AudioNotification {

    fun startAudioNotification(serviceManager: AudioServiceManager)

}