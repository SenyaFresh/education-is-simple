package com.github.educationissimple.audio.domain.entities

typealias AudioCategoryId = Long

/**
 * Represents an audio category, typically used for grouping audio items.
 *
 * @param id The unique identifier of the audio category.
 * @param name The name of the audio category.
 */
data class AudioCategory(
    val id: AudioCategoryId,
    val name: String
) {
    companion object {
        const val NO_CATEGORY_ID = -1L
    }
}