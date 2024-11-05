package com.github.educationissimple.audio_player.entities

data class Audio(
    val uri: String,
    val categoryId: Long?,
    val imageRes: Int,
    val title: String,
    val subtitle: String,
    val duration: Long
)
