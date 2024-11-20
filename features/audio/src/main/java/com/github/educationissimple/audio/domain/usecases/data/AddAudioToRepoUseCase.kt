package com.github.educationissimple.audio.domain.usecases.data

import com.github.educationissimple.audio.domain.repositories.AudioRepository
import javax.inject.Inject

/**
 * Use case for adding an audio item to the repository.
 */
class AddAudioToRepoUseCase @Inject constructor(private val audioRepository: AudioRepository) {

    /**
     * Adds a new audio item.
     *
     * @param uri The URI of the audio item.
     * @param categoryId The ID of the category the audio item should belong to, or null if no category.
     */
    suspend fun addAudioItem(uri: String, categoryId: Long?) {
        audioRepository.addAudioItem(uri, categoryId)
    }

}