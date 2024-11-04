package com.github.educationissimple.audio.presentation.events

import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.entities.PlayerController

sealed class AudioEvent {

    data class PlayerEvent(val controller: PlayerController): AudioEvent()

    data class AddAudioItemEvent(val audio: Audio): AudioEvent()

    data class DeleteAudioItemEvent(val id: Long): AudioEvent()

}