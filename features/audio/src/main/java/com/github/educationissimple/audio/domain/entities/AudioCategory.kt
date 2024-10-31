package com.github.educationissimple.audio.domain.entities

typealias AudioCategoryId = Long

data class AudioCategory(
    val id: AudioCategoryId,
    val name: String
) {
    companion object {
        const val NO_CATEGORY_ID = -1L
    }
}