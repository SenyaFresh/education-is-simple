package com.github.educationissimple.audio_player.entities

data class AudioItem(
    val uri: String,
    val categoryId: Long?,
    val imageUri: String,
    val title: String,
    val subtitle: String,
    val duration: Long
)
