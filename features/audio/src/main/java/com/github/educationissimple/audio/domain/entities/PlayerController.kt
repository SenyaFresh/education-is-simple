package com.github.educationissimple.audio.domain.entities

sealed class PlayerController {

    data class SelectMedia(val id: Long): PlayerController()

    data object PlayPause: PlayerController()

    data class SetPosition(val position: Float): PlayerController()

    data object Next: PlayerController()

    data object Previous: PlayerController()

    data object Close: PlayerController()

}