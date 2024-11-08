package com.github.educationissimple.audio.presentation.events

import com.github.educationissimple.audio.domain.entities.PlayerController

sealed class AudioEvent {

    data class PlayerEvent(val controller: PlayerController) : AudioEvent()

    data class AddAudioItemEvent(val uri: String, val categoryId: Long?) : AudioEvent()

    data class DeleteAudioItemEvent(val uri: String) : AudioEvent()

    data class CreateCategoryEvent(val name: String) : AudioEvent()

    data class DeleteCategoryEvent(val categoryId: Long) : AudioEvent()

    data class ChangeCategoryEvent(val categoryId: Long?) : AudioEvent()

}