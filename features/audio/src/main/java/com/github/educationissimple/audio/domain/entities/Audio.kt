package com.github.educationissimple.audio.domain.entities

data class Audio(
    val uri: String,
    val categoryId: Long?,
    val imageUri: String,
    val title: String,
    val subtitle: String,
    val duration: Long
)
