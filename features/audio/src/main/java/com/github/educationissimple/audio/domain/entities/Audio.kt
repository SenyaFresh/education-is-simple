package com.github.educationissimple.audio.domain.entities

data class Audio(
    val id: Long,
    val uri: String,
    val categoryId: Long?,
    val imageRes: Int,
    val title: String,
    val subtitle: String,
    val duration: Long
)
