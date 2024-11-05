package com.github.educationissimple.audio.domain.entities

sealed class PlayerController {

    data class SelectMedia(val uri: String): PlayerController()

    data object PlayPause: PlayerController()

    data class SetPosition(val positionMs: Long): PlayerController()

    data object Next: PlayerController()

    data object Previous: PlayerController()

    data object Close: PlayerController()

}