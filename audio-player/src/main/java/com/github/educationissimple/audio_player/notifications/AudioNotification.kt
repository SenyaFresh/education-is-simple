package com.github.educationissimple.audio_player.notifications

import com.github.educationissimple.audio_player.services.AudioService

interface AudioNotification {

    fun startAudioNotification(service: AudioService)

}