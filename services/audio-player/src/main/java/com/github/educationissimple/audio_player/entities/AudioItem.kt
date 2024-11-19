package com.github.educationissimple.audio_player.entities

import android.graphics.Bitmap

/**
 * Represents audio item.
 * @param uri The uri to audio file
 * @param categoryId The id of audio category
 * @param imageBitmap The bitmap of audio cover
 * @param title The audio name
 * @param subtitle The audio subtitle or author
 * @param duration The audio duration in milliseconds
 */
data class AudioItem(
    val uri: String,
    val categoryId: Long?,
    val imageBitmap: Bitmap?,
    val title: String,
    val subtitle: String,
    val duration: Long
)
