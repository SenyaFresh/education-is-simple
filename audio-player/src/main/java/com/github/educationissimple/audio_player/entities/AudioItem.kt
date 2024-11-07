package com.github.educationissimple.audio_player.entities

import android.graphics.Bitmap

data class AudioItem(
    val uri: String,
    val categoryId: Long?,
    val imageBitmap: Bitmap,
    val title: String,
    val subtitle: String,
    val duration: Long
)
