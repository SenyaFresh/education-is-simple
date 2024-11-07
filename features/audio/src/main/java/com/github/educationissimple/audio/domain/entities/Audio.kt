package com.github.educationissimple.audio.domain.entities

import android.graphics.Bitmap

data class Audio(
    val uri: String,
    val categoryId: Long?,
    val imageBitmap: Bitmap?,
    val title: String,
    val subtitle: String,
    val duration: Long
)
