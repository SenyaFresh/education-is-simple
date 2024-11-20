package com.github.educationissimple.audio.domain.entities

import android.graphics.Bitmap

/**
 * Represents an audio item with relevant metadata.
 *
 * This data class holds the details of an audio item, including its URI, category ID,
 * title, subtitle, duration, and an optional image bitmap associated with the audio.
 *
 * @param uri The URI of the audio file.
 * @param categoryId The ID of the category this audio belongs to, or null if not applicable.
 * @param imageBitmap An optional bitmap representing the image associated with the audio (e.g., album art).
 * @param title The title of the audio.
 * @param subtitle A subtitle or additional description of the audio.
 * @param duration The duration of the audio in milliseconds.
 */
data class Audio(
    val uri: String,
    val categoryId: Long?,
    val imageBitmap: Bitmap?,
    val title: String,
    val subtitle: String,
    val duration: Long
)
